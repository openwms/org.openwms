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
package org.openwms.core.integration.jpa;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.openwms.core.domain.preferences.ApplicationPreference;
import org.openwms.core.domain.preferences.ModulePreference;
import org.openwms.core.domain.system.AbstractPreference;
import org.openwms.core.domain.system.usermanagement.RolePreference;
import org.openwms.core.domain.system.usermanagement.UserPreference;
import org.openwms.core.integration.PreferenceWriter;
import org.openwms.core.util.exception.WrongClassTypeException;
import org.springframework.stereotype.Repository;

/**
 * A PreferencesDaoImpl.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Repository("preferencesJpaDao")
public class PreferencesDaoImpl implements PreferenceWriter<Long> {

    @PersistenceContext
    private EntityManager em;

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.integration.PreferenceDao#findByKey(org.openwms.core.domain.system.PreferenceKey)
     */
    @Override
    public AbstractPreference findByKey(Long id) {
        return em.find(AbstractPreference.class, id);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.integration.PreferenceDao#findByType(java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends AbstractPreference> List<T> findByType(Class<T> clazz) {
        if (ApplicationPreference.class.equals(clazz)) {
            return (List<T>) em.createNamedQuery(ApplicationPreference.NQ_FIND_ALL);
        }
        if (ModulePreference.class.equals(clazz)) {
            return (List<T>) em.createNamedQuery(ModulePreference.NQ_FIND_ALL);
        }
        if (RolePreference.class.equals(clazz)) {
            return (List<T>) em.createNamedQuery(RolePreference.NQ_FIND_ALL);
        }
        if (UserPreference.class.equals(clazz)) {
            return (List<T>) em.createNamedQuery(UserPreference.NQ_FIND_ALL);
        }
        throw new WrongClassTypeException("Type " + clazz + " not a valid Preferences type");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.integration.PreferenceDao#findAll()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AbstractPreference> findAll() {
        List<AbstractPreference> prefs = em.createNamedQuery(AbstractPreference.NQ_FIND_ALL).getResultList();
        if (null == prefs) {
            return Collections.<AbstractPreference> emptyList();
        }
        return prefs;
    }

    /**
     * {@inheritDoc}
     * 
     * Call {@link EntityManager#persist(Object)} for transient instances and
     * {@link EntityManager#merge(Object)} for detached and managed ones.
     * 
     * @see org.openwms.core.integration.PreferenceWriter#save(org.openwms.core.domain.system.AbstractPreference)
     */
    @Override
    public <T extends AbstractPreference> T save(T entity) {
        if (entity.isNew()) {
            em.persist(entity);
        }
        return em.merge(entity);
    }

    /**
     * {@inheritDoc}
     * 
     * If <code>entity</code> is not already managed, call
     * {@link EntityManager#merge(Object)} before to attach it to the
     * persistence context.
     * 
     * @see org.openwms.core.integration.PreferenceWriter#remove(org.openwms.core.domain.system.AbstractPreference)
     */
    @Override
    public void remove(AbstractPreference entity) {
        if (em.contains(entity)) {
            em.remove(entity);
        } else {
            em.remove(em.merge(entity));
        }
    }

}
