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

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.ameba.annotation.TxService;
import org.openwms.core.annotation.FireAfterTransaction;
import org.openwms.core.configuration.file.AbstractPreference;
import org.openwms.core.configuration.file.PreferenceDao;
import org.openwms.core.event.ConfigurationChangedEvent;
import org.openwms.core.event.MergePropertiesEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

/**
 * A ConfigurationServiceImpl is a transactional Spring powered service implementation to manage preferences.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.2
 * @since 0.1
 */
@TxService
class ConfigurationServiceImpl implements ConfigurationService, ApplicationListener<MergePropertiesEvent> {

    @Autowired
    private PreferenceDao fileDao;
    @Autowired
    private PreferenceRepository preferenceRepository;

    /**
     * {@inheritDoc}
     * <p>
     * When an event arrives all <i>new</i> preferences received from the file provider are persisted. Already persisted preferences are
     * ignored.
     */
    @Override
    public void onApplicationEvent(MergePropertiesEvent event) {
        mergeApplicationProperties();
    }

    /**
     * {@inheritDoc}
     * <p>
     * No match returns an empty List ({@link Collections#emptyList()}).
     */
    @Override
    public Collection<AbstractPreference> findAll() {
        Collection<AbstractPreference> result = preferenceRepository.findAll();
        return result == null ? Collections.emptyList() : result;
    }

    /**
     * {@inheritDoc}
     * <p>
     * If owner is set to {@literal null} or is empty, all preferences of this type are returned. No match returns an empty List ( {@link
     * Collections#emptyList()}).
     */
    @Override
    public <T extends AbstractPreference> Collection<T> findByType(Class<T> clazz, String owner) {
        Collection<T> result;
        result = (owner == null || owner.isEmpty()) ? preferenceRepository.findByType(clazz) : preferenceRepository.findByType(clazz, owner);
        return result == null ? Collections.<T>emptyList() : result;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Not allowed to call this implementation with a {@literal null} argument.
     *
     * @throws IllegalArgumentException when {@code preference} is {@literal null}
     */
    @Override
    @FireAfterTransaction(events = {ConfigurationChangedEvent.class})
    public <T extends AbstractPreference> T save(@NotNull T preference) {
        //Assert.notNull(preference, "Not allowed to call save with a NULL argument");
        return preferenceRepository.save(preference);
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException when {@code preference} is {@literal null}
     */
    @Override
    @FireAfterTransaction(events = {ConfigurationChangedEvent.class})
    public void delete(@NotNull AbstractPreference preference) {
        //Assert.notNull(preference, "Not allowed to call remove with a NULL argument");
        preferenceRepository.delete(preference);
    }

    private void mergeApplicationProperties() {
        List<AbstractPreference> fromFile = fileDao.findAll();
        List<AbstractPreference> persistedPrefs = preferenceRepository.findAll();
        for (AbstractPreference pref : fromFile) {
            if (!persistedPrefs.contains(pref)) {
                preferenceRepository.save(pref);
            }
        }
    }
}