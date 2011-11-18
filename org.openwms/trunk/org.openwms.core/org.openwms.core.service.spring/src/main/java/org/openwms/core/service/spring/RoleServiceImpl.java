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
package org.openwms.core.service.spring;

import java.util.List;

import org.openwms.core.domain.system.usermanagement.Role;
import org.openwms.core.integration.RoleDao;
import org.openwms.core.service.RoleService;
import org.openwms.core.util.validation.AssertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A RoleServiceImpl is a Spring supported transactional implementation of a
 * general {@link RoleService}. Using Spring 2 annotation support autowires
 * collaborators, therefore XML configuration becomes obsolete. This class is
 * marked with Springs {@link Service} annotation to benefit from Springs
 * exception translation intercepter. Traditional CRUD operations are delegated
 * to a {@link RoleDao} instance.
 * <p>
 * This implementation can be autowired with the name {@value #COMPONENT_NAME}.
 * </p>
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.integration.system.usermanagement.RoleDao;
 */
@Transactional
@Service(RoleServiceImpl.COMPONENT_NAME)
public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    @Autowired
    private RoleDao dao;
    /**
     * Springs service name.
     */
    public static final String COMPONENT_NAME = "roleService";

    /**
     * {@inheritDoc}
     * 
     * @throws IllegalArgumentException
     *             when <code>roles</code> is <code>null</code>
     */
    @Override
    public void remove(List<Role> roles) {
        AssertUtils.notNull(roles, "Roles to be removed must not be null");
        Role role;
        for (Role r : roles) {
            role = r.isNew() ? dao.findByUniqueId(r.getName()) : dao.findById(r.getId());
            if (role != null) {
                dao.remove(role);
            } else {
                if (logger.isInfoEnabled()) {
                    logger.info("Role to remove could not be found. Role:" + role);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IllegalArgumentException
     *             when <code>role</code> is <code>null</code>
     */
    @Override
    public Role save(Role role) {
        AssertUtils.notNull(role, "Role to be removed must not be null");
        return dao.save(role);
    }

    /**
     * {@inheritDoc}
     * 
     * Marked as <code>readOnly</code> transactional method.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return dao.findAll();
    }

}
