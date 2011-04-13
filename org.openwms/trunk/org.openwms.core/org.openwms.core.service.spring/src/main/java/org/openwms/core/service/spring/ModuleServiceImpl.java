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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.openwms.core.domain.Module;
import org.openwms.core.integration.ModuleDao;
import org.openwms.core.service.ModuleService;
import org.openwms.core.service.exception.ServiceRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A ModuleServiceImpl is a Spring powered transactional service using a
 * {@link ModuleDao} to perform simple CRUD operations.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 * @see org.openwms.core.integration.ModuleDao
 */
@Service
@Transactional
public class ModuleServiceImpl implements ModuleService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ModuleDao dao;

    /**
     * {@inheritDoc}
     * 
     * Marked as <code>readOnly</code> transactional method.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Module> findAll() {
        return dao.findAll();
    }

    /**
     * {@inheritDoc}
     * 
     * Marked as <code>readOnly</code> transactional method. Only a trace
     * message is written. This method is solely responsible to activate the
     * security filter chain.
     */
    @Override
    @Transactional(readOnly = true)
    public void login() {
        logger.debug("Login successful!");
    }

    /**
     * {@inheritDoc}
     * 
     * It is expected that the list of {@link Module}s is already pre-ordered in
     * their startupOrder. Each {@link Module}'s startupOrder is synchronized
     * with the persistence storage. No other data is updated.
     * 
     * @throws ServiceRuntimeException
     *             when modules is <code>null</code>
     */
    @Override
    public void saveStartupOrder(List<Module> modules) {
        if (modules == null) {
            throw new ServiceRuntimeException("List of modules to store the startupOrder for is null");
        }
        for (Module module : modules) {
            Module toSave = dao.findById(module.getId());
            toSave.setStartupOrder(module.getStartupOrder());
            dao.save(toSave);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * In this case a {@link Module} is been removed. But if the {@link Module}
     * entity is a transient instance the method returns with no further action.
     * 
     * @throws ServiceRuntimeException
     *             when the {@link Module} was not found or is <code>null</code>
     */
    @Override
    public void remove(Module module) {
        if (module == null) {
            throw new ServiceRuntimeException("Module to be removed is null");
        }
        Module rem = null;
        if (module.isNew()) {
            rem = dao.findByUniqueId(module.getModuleName());
            if (rem == null) {
                logger.info("Do not remove a transient Module");
                return;
            }
        } else {
            rem = dao.findById(module.getId());
            if (rem == null) {
                throw new ServiceRuntimeException("Module to be removed not found, probably it was removed before");
            }
        }
        dao.remove(rem);
    }

    /**
     * {@inheritDoc}
     * 
     * Additionally the startup order is re-calculated for a new {@link Module}.
     */
    @Override
    public Module save(Module module) {
        if (module == null) {
            throw new ServiceRuntimeException("Module to be saved is null");
        }
        if (module.isNew()) {
            List<Module> all = dao.findAll();
            if (!all.isEmpty()) {
                Collections.sort(all, new Comparator<Module>() {
                    @Override
                    public int compare(Module o1, Module o2) {
                        return o1.getStartupOrder() >= o2.getStartupOrder() ? 1 : -1;
                    }
                });
                for (Module module2 : all) {
                    System.out.println("L:" + module2.getModuleName() + module2.getStartupOrder());
                }
                module.setStartupOrder(all.get(all.size() - 1).getStartupOrder() + 1);
            }
        }
        return dao.save(module);
    }
}
