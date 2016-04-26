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
package org.openwms.core;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.openwms.core.annotation.FireAfterTransaction;
import org.openwms.core.domain.system.AbstractPreference;
import org.openwms.core.domain.system.PreferenceKey;
import org.openwms.core.integration.PreferenceDao;
import org.openwms.core.integration.PreferenceWriter;
import org.openwms.core.util.event.ConfigurationChangedEvent;
import org.openwms.core.util.event.MergePropertiesEvent;
import org.openwms.core.util.validation.AssertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A ConfigurationServiceImpl is a transactional Spring powered service
 * implementation to manage preferences. This implementation can be autowired
 * with the name {@value #COMPONENT_NAME}.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Transactional
@Service(ConfigurationServiceImpl.COMPONENT_NAME)
public class ConfigurationServiceImpl implements ConfigurationService, ApplicationListener<MergePropertiesEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationServiceImpl.class);
    @Autowired
    @Qualifier("preferencesFileDao")
    private PreferenceDao<PreferenceKey> fileDao;
    @Autowired
    @Qualifier("preferencesJpaDao")
    private PreferenceWriter<Long> dao;

    /** Springs service name. */
    public static final String COMPONENT_NAME = "configurationService";

    /**
     * {@inheritDoc}
     * 
     * When an event arrives all <i>new</i> preferences received from the file
     * provider are persisted. Already persisted preferences are ignored.
     */
    @Override
    public void onApplicationEvent(MergePropertiesEvent event) {
        mergeApplicationProperties();
    }

    /**
     * {@inheritDoc}
     * 
     * No match returns an empty List ({@link Collections#emptyList()}).
     */
    @Override
    public Collection<AbstractPreference> findAll() {
        Collection<AbstractPreference> result = dao.findAll();
        return result == null ? Collections.<AbstractPreference> emptyList() : result;
    }

    /**
     * {@inheritDoc}
     * 
     * If owner is set to <code>null</code> or is empty, all preferences of this
     * type are returned. No match returns an empty List (
     * {@link Collections#emptyList()}).
     */
    @Override
    public <T extends AbstractPreference> Collection<T> findByType(Class<T> clazz, String owner) {
        Collection<T> result;
        result = (owner == null || owner.isEmpty()) ? dao.findByType(clazz) : dao.findByType(clazz, owner);
        return result == null ? Collections.<T> emptyList() : result;
    }

    /**
     * {@inheritDoc}
     * 
     * Not allowed to call this implementation with a <code>null</code>
     * argument.
     * 
     * @throws IllegalArgumentException
     *             when <code>preference</code> is <code>null</code>
     */
    @Override
    @FireAfterTransaction(events = { ConfigurationChangedEvent.class })
    public <T extends AbstractPreference> T save(T preference) {
        AssertUtils.notNull(preference, "Not allowed to call save with a NULL argument");
        List<? extends AbstractPreference> preferences = dao.findByType(preference.getClass());
        if (preferences.contains(preference)) {
            if (preference.isNew()) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Fake. Do not save or persist an entity that is transient and duplicated");
                    // FIXME [scherrer] : Don't do nothing. clone and merge!
                }
                return preference;
            }
            return dao.save(preference);
        }
        dao.persist(preference);
        return dao.save(preference);
    }

    /**
     * {@inheritDoc}
     * 
     * Not allowed to call this implementation with a <code>null</code>
     * argument.
     * 
     * @throws IllegalArgumentException
     *             when <code>preference</code> is <code>null</code>
     */
    @Override
    @FireAfterTransaction(events = { ConfigurationChangedEvent.class })
    public AbstractPreference merge(AbstractPreference preference) {
        AssertUtils.notNull(preference, "Not allowed to call merge with a NULL argument");
        List<? extends AbstractPreference> preferences = dao.findByType(preference.getClass());
        if (preferences.contains(preference)) {
            return preference;
        }
        return save(preference);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IllegalArgumentException
     *             when <code>preference</code> is <code>null</code>
     */
    @Override
    @FireAfterTransaction(events = { ConfigurationChangedEvent.class })
    public void remove(AbstractPreference preference) {
        AssertUtils.notNull(preference, "Not allowed to call remove with a NULL argument");
        dao.remove(preference);
    }

    private void mergeApplicationProperties() {
        List<AbstractPreference> fromFile = fileDao.findAll();
        List<AbstractPreference> persistedPrefs = dao.findAll();
        for (AbstractPreference pref : fromFile) {
            if (!persistedPrefs.contains(pref)) {
                dao.save(pref);
            }
        }
    }
}