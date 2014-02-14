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
package org.openwms.core.integration.jpa;

import org.openwms.core.domain.system.I18n;
import org.openwms.core.integration.I18nRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * An I18nRepositoryImpl is responsible to find and retrieve i18n translations from the persistent storage. It can be injected by name
 * {@value #COMPONENT_NAME}.
 * <p>
 * All methods have to be invoked within an active transaction context.
 * </p>
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Transactional(propagation = Propagation.MANDATORY)
@Repository(I18nRepositoryImpl.COMPONENT_NAME)
public class I18nRepositoryImpl extends AbstractGenericJpaDao<I18n, Long> implements I18nRepository {

    /** Springs component name. */
    public static final String COMPONENT_NAME = "i18nRepo";

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getFindAllQuery() {
        return I18n.NQ_FIND_ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getFindByUniqueIdQuery() {
        return I18n.NQ_FIND_BY_UNIQUE_QUERY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<I18n> getPersistentClass() {
        return I18n.class;
    }
}