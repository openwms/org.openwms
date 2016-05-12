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
package org.openwms.core.uaa;

import java.util.ArrayList;
import java.util.List;

import org.ameba.annotation.TxService;
import org.ameba.i18n.Translator;
import org.openwms.core.annotation.FireAfterTransaction;
import org.openwms.core.event.UserChangedEvent;
import org.openwms.core.exception.ExceptionCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * A SecurityServiceImpl is a transactional Spring Service implementation.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.2
 * @since 0.1
 */
@TxService
public class SecurityServiceImpl implements SecurityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);
    @Autowired
    private SecurityObjectRepository securityObjectDao;
    @Autowired
    private RoleRepository roleDao;
    @Autowired
    private Translator translator;

    /**
     * {@inheritDoc}
     * <p>
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
     * <p>
     * Triggers <tt>UserChangedEvent</tt> after completion.
     */
    @Override
    @FireAfterTransaction(events = {UserChangedEvent.class})
    public List<Grant> mergeGrants(String moduleName, List<Grant> grants) {
        Assert.notNull(moduleName, translator.translate(ExceptionCodes.MODULENAME_NOT_NULL));
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Merging grants of module:" + moduleName);
        }
        List<Grant> persisted = securityObjectDao.findAllOfModule(moduleName + "%");
        List<Grant> result = new ArrayList<>(persisted);
        Grant merged;
        for (Grant grant : grants) {
            if (!persisted.contains(grant)) {
                merged = (Grant) securityObjectDao.merge(grant);
                result.add(merged);
            } else {
                persisted.remove(grant);
            }
        }
        result.removeAll(persisted);
        if (!persisted.isEmpty()) {
            roleDao.removeFromRoles(persisted);
            securityObjectDao.delete(persisted);
        }
        return result;
    }
}