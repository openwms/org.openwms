/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.core.service.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openwms.core.annotation.FireAfterTransaction;
import org.openwms.core.domain.system.usermanagement.Grant;
import org.openwms.core.domain.system.usermanagement.SecurityObject;
import org.openwms.core.integration.RoleDao;
import org.openwms.core.integration.SecurityObjectDao;
import org.openwms.core.service.SecurityService;
import org.openwms.core.util.event.UserChangedEvent;
import org.openwms.core.util.validation.AssertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A SecurityServiceImpl.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Transactional
@Service(SecurityServiceImpl.COMPONENT_NAME)
public class SecurityServiceImpl implements SecurityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);
    @Autowired
    @Qualifier("securityObjectDao")
    private SecurityObjectDao dao;
    @Autowired(required = true)
    @Qualifier("roleDao")
    private RoleDao roleDao;
    /** Springs component name. */
    public static final String COMPONENT_NAME = "securityService";

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SecurityObject> findAll() {
        List<SecurityObject> result = dao.findAll();
        return result == null ? Collections.<SecurityObject> emptyList() : result;
    }

    /**
     * {@inheritDoc}
     * 
     * Marked as <code>readOnly</code> transactional method. Only a trace message is written. This method is solely responsible to activate
     * the security filter chain.
     */
    @Override
    @Transactional(readOnly = true)
    public void login() {
        LOGGER.debug("Login successful!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @FireAfterTransaction(events = { UserChangedEvent.class })
    public List<Grant> mergeGrants(String moduleName, List<Grant> grants) {
        AssertUtils.notNull(moduleName, "Modulename must not be null");
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Merging grants of module:" + moduleName);
        }
        List<Grant> persisted = dao.findAllOfModule(moduleName + "%");
        List<Grant> result = new ArrayList<Grant>(persisted);
        Grant merged = null;
        for (Grant grant : grants) {
            if (!persisted.contains(grant)) {
                merged = (Grant) dao.merge(grant);
                result.add(merged);
                // if (persisted.contains(merged)) {
                // persisted.remove(merged);
                // }
            } else {
                persisted.remove(grant);
            }
        }
        result.removeAll(persisted);
        if (!persisted.isEmpty()) {
            roleDao.removeFromRoles(persisted);
            dao.delete(persisted);
        }
        return result;
    }
}