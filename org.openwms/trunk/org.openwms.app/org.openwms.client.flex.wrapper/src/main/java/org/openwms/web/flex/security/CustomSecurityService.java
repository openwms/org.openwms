/*
 * openwms.org, the Open Warehouse Management System.
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.web.flex.security;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.granite.context.GraniteContext;
import org.granite.logging.Logger;
import org.granite.messaging.service.security.AbstractSecurityContext;
import org.granite.messaging.service.security.AbstractSecurityService;
import org.granite.messaging.service.security.SecurityServiceException;
import org.granite.messaging.webapp.HttpGraniteContext;
import org.granite.spring.security.SpringSecurity3Service;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * A CustomSecurityService.
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision: 700 $
 * @since 0.1
 */
public class CustomSecurityService extends AbstractSecurityService {

    private static final Logger logger = Logger.getLogger(CustomSecurityService.class);

    private static final String SPRING_AUTHENTICATION_TOKEN = SpringSecurity3Service.class.getName()
            + ".SPRING_AUTHENTICATION_TOKEN";

    private AuthenticationManager authenticationManager = null;
    private String authenticationManagerBeanName = "authenticationManager";

    /**
     * Inject an
     * org.springframework.security.authentication.AuthenticationManager.
     * 
     * @param authenticationManager
     *            The manager
     */
    @Required
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.granite.messaging.service.security.SecurityService#configure(java.util.Map)
     */
    @Override
    public void configure(Map<String, String> params) {
        if (params.containsKey("authentication-manager-bean-name")) {
            authenticationManagerBeanName = params.get("authentication-manager-bean-name");
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.granite.messaging.service.security.SecurityService#login(java.lang.Object)
     */
    @Override
    public void login(Object credentials) {
        logger.debug("Trying to log in");
        List<String> decodedCredentials = Arrays.asList(decodeBase64Credentials(credentials));

        HttpGraniteContext context = (HttpGraniteContext) GraniteContext.getCurrentInstance();
        HttpServletRequest httpRequest = context.getRequest();

        String user = decodedCredentials.get(0);
        String password = decodedCredentials.get(1);
        Authentication auth = new UsernamePasswordAuthenticationToken(user, password);

        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(httpRequest.getSession()
                .getServletContext());
        if (ctx != null) {
            lookupAuthenticationManager(ctx, authenticationManagerBeanName);

            try {
                Authentication authentication = authenticationManager.authenticate(auth);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                httpRequest.getSession().setAttribute(SPRING_AUTHENTICATION_TOKEN, authentication);
            } catch (DisabledException de) {
                logger.error("DisabledException");
                throw SecurityServiceException.newAccessDeniedException(de.getMessage());
            } catch (LockedException le) {
                logger.error("LockedException");
                throw SecurityServiceException.newAccessDeniedException(le.getMessage());
            } catch (BadCredentialsException bce) {
                logger.error("BadCredentialsException");
                throw SecurityServiceException.newInvalidCredentialsException(bce.getMessage());
            }
        }
    }

    /**
     * Resolve the AuthenticationManager by name.
     * 
     * @param ctx
     *            Springs ApplicationContext
     * @param managerBeanName
     *            The beanName defined for the AuthenticationManager in the
     *            Spring configuration
     */
    private void lookupAuthenticationManager(ApplicationContext ctx, String managerBeanName) {

        Map<String, AuthenticationManager> authManagers = BeanFactoryUtils.beansOfTypeIncludingAncestors(ctx,
                AuthenticationManager.class);
        String beanName = managerBeanName == null ? "_authenticationManager" : managerBeanName;
        this.authenticationManager = authManagers.get(beanName);
        if (authenticationManager == null) {
            try {
                authenticationManager = ctx.getBean(beanName, AuthenticationManager.class);
            } catch (BeansException e) {
                throw SecurityServiceException.newInvalidCredentialsException("Authentication failed");
            }
        }
        return;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.granite.messaging.service.security.SecurityService#authorize(org.granite.messaging.service.security.AbstractSecurityContext)
     */
    @Override
    public Object authorize(AbstractSecurityContext context) throws Exception {
        startAuthorization(context);

        Authentication authentication = getAuthentication();
        if (context.getDestination().isSecured()) {
            if (!isAuthenticated(authentication)) {
                throw SecurityServiceException.newNotLoggedInException("User not logged in");
            }
            if (!userCanAccessService(context, authentication)) {
                throw SecurityServiceException.newAccessDeniedException("User not in required role");
            }
        }
        if (isAuthenticated(authentication)) {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
        }

        try {
            return endAuthorization(context);
        } catch (InvocationTargetException e) {
            handleAuthorizationExceptions(e);
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.granite.messaging.service.security.SecurityService#logout()
     */
    @Override
    public void logout() {
        HttpGraniteContext context = (HttpGraniteContext) GraniteContext.getCurrentInstance();
        context.getSession().invalidate();
    }

    /**
     * {@inheritDoc}
     */
    protected boolean isUserInRole(Authentication authentication, String role) {
        for (GrantedAuthority ga : authentication.getAuthorities()) {
            if (ga.getAuthority().matches(role)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    protected boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();
    }

    /**
     * {@inheritDoc}
     */
    protected boolean userCanAccessService(AbstractSecurityContext context, Authentication authentication) {
        for (String role : context.getDestination().getRoles()) {
            if (isUserInRole(authentication, role)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    protected Authentication getAuthentication() {
        HttpGraniteContext context = (HttpGraniteContext) GraniteContext.getCurrentInstance();
        HttpServletRequest httpRequest = context.getRequest();
        Authentication authentication = (Authentication) httpRequest.getSession().getAttribute(
                SPRING_AUTHENTICATION_TOKEN);

        return authentication;
    }

    /**
     * {@inheritDoc}
     */
    protected void handleAuthorizationExceptions(InvocationTargetException e) {
        for (Throwable t = e; t != null; t = t.getCause()) {
            // Don't create a dependency to javax.ejb in SecurityService...
            if (t instanceof SecurityException || t instanceof AccessDeniedException
                    || "javax.ejb.EJBAccessException".equals(t.getClass().getName())) {
                throw SecurityServiceException.newAccessDeniedException(t.getMessage());
            }
        }
    }

}
