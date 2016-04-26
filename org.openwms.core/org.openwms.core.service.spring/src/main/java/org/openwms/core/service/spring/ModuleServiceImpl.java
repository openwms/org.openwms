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

import java.util.Collections;
import java.util.List;

import org.openwms.core.ExceptionCodes;
import org.openwms.core.ModuleService;
import org.openwms.core.domain.Module;
import org.openwms.core.integration.GenericDao;
import org.openwms.core.integration.ModuleDao;
import org.openwms.core.service.exception.EntityNotFoundException;
import org.openwms.core.service.exception.ServiceRuntimeException;
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
public class ModuleServiceImpl extends AbstractGenericEntityService<Module, Long, String> implements ModuleService {

    @Autowired
    private ModuleDao moduleDao;
    /** Springs component name. */
    public static final String COMPONENT_NAME = "moduleService";

    /**
     * {@inheritDoc}
     */
    @Override
    protected GenericDao<Module, Long> getRepository() {
        return moduleDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Module resolveByBK(Module entity) {
        Module result = null;
        try {
            result = moduleDao.findByUniqueId(entity.getModuleName());
        } catch (EntityNotFoundException enfe) {
            ;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * It is expected that the list of {@link Module}s is already ordered by
     * their startup order. Each {@link Module}'s <code>startupOrder</code> is
     * synchronized with the persistence storage.
     * 
     * @throws ServiceRuntimeException
     *             when <code>modules</code> is <code>null</code>
     */
    @Override
    public void saveStartupOrder(List<Module> modules) {
        checkForNull(modules, ExceptionCodes.MODULE_SAVE_STARTUP_ORDER_NOT_BE_NULL);
        for (Module module : modules) {
            Module toSave = findById(module.getId());
            toSave.setStartupOrder(module.getStartupOrder());
            save(toSave);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * Additionally the <code>startupOrder</code> is re-calculated for the new
     * {@link Module}.
     * 
     * @throws ServiceRuntimeException
     *             when <code>module</code> is <code>null</code>
     */
    @Override
    public Module save(Module module) {
        checkForNull(module, ExceptionCodes.MODULE_SAVE_NOT_BE_NULL);
        if (module.isNew()) {
            List<Module> all = findAll();
            if (!all.isEmpty()) {
                Collections.sort(all, new Module.ModuleComparator());
                module.setStartupOrder(all.get(all.size() - 1).getStartupOrder() + 1);
            }
        }
        return super.save(module);
    }
}