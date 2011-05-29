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
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.granite.context.GraniteContext;
import org.granite.messaging.service.security.AbstractSecurityContext;
import org.granite.messaging.service.security.AbstractSecurityService;
import org.granite.messaging.service.security.SecurityServiceException;
import org.granite.messaging.webapp.HttpGraniteContext;
import org.granite.spring.security.AbstractSpringSecurity3Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * A CustomSecurityService.
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision$
 * @since 0.1
 */
public class CustomSecurityService extends AbstractSecurityService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String FILTER_APPLIED = "__spring_security_scpf_applied";

    private AuthenticationManager authenticationManager = null;
    private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    private AbstractSpringSecurity3Interceptor securityInterceptor = null;
    private String authenticationManagerBeanName = null;
    private Method getRequest = null;
    private Method getResponse = null;

    public CustomSecurityService() {
        try {
            getRequest = HttpRequestResponseHolder.class.getDeclaredMethod("getRequest");
            getRequest.setAccessible(true);
            getResponse = HttpRequestResponseHolder.class.getDeclaredMethod("getResponse");
            getResponse.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException("Could not get methods from HttpRequestResponseHolder", e);
        }
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setSecurityContextRepository(SecurityContextRepository securityContextRepository) {
        this.securityContextRepository = securityContextRepository;
    }

    public void setSecurityInterceptor(AbstractSpringSecurity3Interceptor securityInterceptor) {
        this.securityInterceptor = securityInterceptor;
    }

    @Override
    public void configure(Map<String, String> params) {
        if (params.containsKey("authentication-manager-bean-name"))
            authenticationManagerBeanName = params.get("authentication-manager-bean-name");
    }

    @Override
    public void login(Object credentials) {
        List<String> decodedCredentials = Arrays.asList(decodeBase64Credentials(credentials));

        HttpGraniteContext graniteContext = (HttpGraniteContext) GraniteContext.getCurrentInstance();
        HttpServletRequest httpRequest = graniteContext.getRequest();

        String user = decodedCredentials.get(0);
        String password = decodedCredentials.get(1);
        Authentication auth = new UsernamePasswordAuthenticationToken(user, password);

        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(httpRequest.getSession()
                .getServletContext());
        if (ctx != null) {
            lookupAuthenticationManager(ctx, authenticationManagerBeanName);

            try {
                Authentication authentication = authenticationManager.authenticate(auth);

                HttpRequestResponseHolder holder = new HttpRequestResponseHolder(graniteContext.getRequest(),
                        graniteContext.getResponse());
                SecurityContext securityContext = securityContextRepository.loadContext(holder);
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
                try {
                    securityContextRepository.saveContext(securityContext,
                            (HttpServletRequest) getRequest.invoke(holder),
                            (HttpServletResponse) getResponse.invoke(holder));
                } catch (Exception e) {
                    logger.error("Could not save context after authentication", e);
                }
            } catch (BadCredentialsException e) {
                throw SecurityServiceException.newInvalidCredentialsException(e.getMessage());
            }
        }
    }

    public void lookupAuthenticationManager(ApplicationContext ctx, String authenticationManagerBeanName)
            throws SecurityServiceException {
        if (this.authenticationManager != null) return;

        Map<String, AuthenticationManager> authManagers = BeanFactoryUtils.beansOfTypeIncludingAncestors(ctx,
                AuthenticationManager.class);
        String beanName = authenticationManagerBeanName == null ? "_authenticationManager"
                : authenticationManagerBeanName;
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

    @Override
    public Object authorize(AbstractSecurityContext context) throws Exception {
        startAuthorization(context);

        HttpGraniteContext graniteContext = (HttpGraniteContext) GraniteContext.getCurrentInstance();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HttpRequestResponseHolder holder = null;

        if (graniteContext.getRequest().getAttribute(FILTER_APPLIED) == null) {
            holder = new HttpRequestResponseHolder(graniteContext.getRequest(), graniteContext.getResponse());
            SecurityContext contextBeforeChainExecution = securityContextRepository.loadContext(holder);
            SecurityContextHolder.setContext(contextBeforeChainExecution);
            if (isAuthenticated(authentication))
                contextBeforeChainExecution.setAuthentication(authentication);
            else
                authentication = contextBeforeChainExecution.getAuthentication();
        }

        if (context.getDestination().isSecured()) {
            if (!isAuthenticated(authentication) || authentication instanceof AnonymousAuthenticationToken) {
                logger.debug("Is not authenticated!");
                throw SecurityServiceException.newNotLoggedInException("User not logged in");
            }
            if (!userCanAccessService(context, authentication)) {
                logger.debug("Access denied for {0}", authentication.getName());
                throw SecurityServiceException.newAccessDeniedException("User not in required role");
            }
        }

        try {
            Object returnedObject = securityInterceptor != null ? securityInterceptor.invoke(context)
                    : endAuthorization(context);

            return returnedObject;
        } catch (AccessDeniedException e) {
            throw SecurityServiceException.newAccessDeniedException(e.getMessage());
        } catch (InvocationTargetException e) {
            handleAuthorizationExceptions(e);
            throw e;
        }
        finally {
            if (graniteContext.getRequest().getAttribute(FILTER_APPLIED) == null) {
                SecurityContext contextAfterChainExecution = SecurityContextHolder.getContext();
                SecurityContextHolder.clearContext();
                try {
                    securityContextRepository.saveContext(contextAfterChainExecution,
                            (HttpServletRequest) getRequest.invoke(holder),
                            (HttpServletResponse) getResponse.invoke(holder));
                } catch (Exception e) {
                    logger.error("Could not extract wrapped context from holder", e);
                }
            }
        }
    }

    @Override
    public void logout() {
        HttpGraniteContext context = (HttpGraniteContext) GraniteContext.getCurrentInstance();
        HttpSession session = context.getSession(false);
        if (session != null && securityContextRepository.containsContext(context.getRequest())) session.invalidate();

        SecurityContextHolder.clearContext();
    }

    protected boolean isUserInRole(Authentication authentication, String role) {
        for (GrantedAuthority ga : authentication.getAuthorities()) {
            if (ga.getAuthority().matches(role)) return true;
        }
        return false;
    }

    protected boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();
    }

    protected boolean userCanAccessService(AbstractSecurityContext context, Authentication authentication) {
        for (String role : context.getDestination().getRoles()) {
            if (isUserInRole(authentication, role)) {
                logger.debug("Allowed access to {0} in role {1}", authentication.getName(), role);
                return true;
            }
            logger.debug("Access denied for {0} not in role {1}", authentication.getName(), role);
        }
        return false;
    }

    protected void handleAuthorizationExceptions(InvocationTargetException e) {
        for (Throwable t = e; t != null; t = t.getCause()) {
            // Don't create a dependency to javax.ejb in SecurityService...
            if (t instanceof SecurityException || t instanceof AccessDeniedException
                    || "javax.ejb.EJBAccessException".equals(t.getClass().getName()))
                throw SecurityServiceException.newAccessDeniedException(t.getMessage());
        }
    }

}
