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
import java.util.List;

import org.openwms.core.domain.Module;
import org.openwms.core.integration.ModuleDao;
import org.openwms.core.service.ModuleService;
import org.openwms.core.service.exception.ServiceRuntimeException;
import org.openwms.core.util.validation.AssertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A ModuleServiceImpl is a Spring powered transactional service using a
 * repository to execute simple CRUD operations. This implementation can be
 * autowired with the name {@value #COMPONENT_NAME}.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.integration.ModuleDao
 */
@Transactional
@Service(ModuleServiceImpl.COMPONENT_NAME)
public class ModuleServiceImpl implements ModuleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModuleServiceImpl.class);
    @Autowired
    private ModuleDao dao;
    /** Springs component name. */
    public static final String COMPONENT_NAME = "moduleService";

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
     * It is expected that the list of {@link Module}s is already ordered by
     * their startup order. Each {@link Module}'s <code>startupOrder</code> is
     * synchronized with the persistence storage.
     * 
     * @throws IllegalArgumentException
     *             when modules is <code>null</code>
     */
    @Override
    public void saveStartupOrder(List<Module> modules) {
        AssertUtils.notNull(modules, "List of modules to save the startupOrder, is null");
        for (Module module : modules) {
            Module toSave = dao.findById(module.getId());
            toSave.setStartupOrder(module.getStartupOrder());
            dao.save(toSave);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * If the {@link Module} entity is a transient instance the method returns
     * with no further action.
     * 
     * @throws IllegalArgumentException
     *             when <code>module</code> is <code>null</code>
     * @throws ServiceRuntimeException
     *             when the {@link Module} to remove was not found
     */
    @Override
    public void remove(Module module) {
        AssertUtils.notNull(module, "Module to be removed must not be null");
        Module rem = null;
        if (module.isNew()) {
            rem = dao.findByUniqueId(module.getModuleName());
            if (rem == null) {
                LOGGER.info("Do not remove a transient Module");
                return;
            }
        } else {
            rem = dao.findById(module.getId());
            if (rem == null) {
                throw new ServiceRuntimeException("Module to be removed was not found, probably it was removed before");
            }
        }
        dao.remove(rem);
    }

    /**
     * {@inheritDoc}
     * 
     * Additionally the <code>startupOrder</code> is re-calculated for a new
     * {@link Module}.
     * 
     * @throws IllegalArgumentException
     *             when <code>module</code> is <code>null</code>
     */
    @Override
    public Module save(Module module) {
        AssertUtils.notNull(module, "Module to be saved must not be null");
        if (module.isNew()) {
            List<Module> all = dao.findAll();
            if (!all.isEmpty()) {
                Collections.sort(all, new Module.ModuleComparator());
                module.setStartupOrder(all.get(all.size() - 1).getStartupOrder() + 1);
            }
        }
        return dao.save(module);
    }
}