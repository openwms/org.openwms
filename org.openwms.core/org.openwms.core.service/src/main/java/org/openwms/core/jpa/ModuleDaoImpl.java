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
package org.openwms.core.jpa;

import org.openwms.core.ModuleDao;
import org.openwms.core.domain.Module;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * A ModuleDao is an extension of a {@link AbstractGenericJpaDao} about functionality regarding {@link Module}s. The stereotype annotation
 * {@link Repository} marks this class as a DAO and enables exception translation and component scanning. It can be injected by name
 * {@value #COMPONENT_NAME}.
 * <p>
 * All methods have to be invoked within an active transaction context.
 * </p>
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see AbstractGenericJpaDao
 * @see org.openwms.core.ModuleDao
 */
@Transactional(propagation = Propagation.MANDATORY)
@Repository(ModuleDaoImpl.COMPONENT_NAME)
public class ModuleDaoImpl extends AbstractGenericJpaDao<Module, Long> implements ModuleDao {

    /** Springs component name. */
    public static final String COMPONENT_NAME = "moduleDao";

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getFindAllQuery() {
        return Module.NQ_FIND_ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getFindByUniqueIdQuery() {
        return Module.NQ_FIND_BY_UNIQUE_QUERY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Module> getPersistentClass() {
        return Module.class;
    }
}