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
package org.openwms.core.configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import org.openwms.core.AbstractEntity;
import org.openwms.core.exception.WrongClassTypeException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * A PreferenceRepositoryImpl implements custom generic find methods of {@link PreferenceRepositoryCustom}.
 * <p>
 * All methods have to be invoked within an active transaction context.
 * </p>
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.2
 * @since 0.1
 */
@Transactional(propagation = Propagation.MANDATORY)
@Repository
class PreferenceRepositoryImpl implements PreferenceRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends AbstractPreference> List<T> findByType(Class<T> clazz) {
        return (List<T>) em.createNamedQuery(getQueryName(clazz) + AbstractEntity.FIND_ALL).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends AbstractPreference> List<T> findByType(Class<T> clazz, String owner) {
        return (List<T>) em.createNamedQuery(getQueryName(clazz) + AbstractPreference.FIND_BY_OWNER)
                .setParameter("owner", owner).getResultList();
    }

    private <T extends AbstractPreference> String getQueryName(Class<T> clazz) {
        for (int i = 0; i < Preferences.TYPES.length; i++) {
            if (Preferences.TYPES[i].equals(clazz)) {
                return clazz.getSimpleName();
            }
        }
        throw new WrongClassTypeException("Type " + clazz + " not a valid Preferences type");
    }
}