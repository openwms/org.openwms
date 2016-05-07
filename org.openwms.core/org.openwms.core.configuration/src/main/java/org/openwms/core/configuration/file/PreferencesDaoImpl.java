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
package org.openwms.core.configuration.file;

import javax.annotation.PostConstruct;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.openwms.core.Constants;
import org.openwms.core.configuration.AbstractPreference;
import org.openwms.core.configuration.PreferenceDao;
import org.openwms.core.configuration.PreferenceKey;
import org.openwms.core.configuration.Preferences;
import org.openwms.core.event.ReloadFilePreferencesEvent;
import org.openwms.core.exception.DataException;
import org.openwms.core.exception.NoUniqueResultException;
import org.openwms.core.exception.ResourceNotFoundException;
import org.openwms.core.logging.LoggingCategories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * A PreferencesDaoImpl reads a XML file of preferences and keeps them internally in a Map. An initial preferences file is expected to be at
 * {@value #INITIAL_PREFERENCES_FILE} but this can be overridden with a property <i>application.initial.properties</i> in the configuration
 * properties file. <p> On a {@link ReloadFilePreferencesEvent} the internal Map is cleared and reloaded. </p>
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @see org.openwms.core.event.ReloadFilePreferencesEvent
 * @since 0.1
 */
@Transactional(propagation = Propagation.MANDATORY)
@Repository(PreferencesDaoImpl.COMPONENT_NAME)
public class PreferencesDaoImpl implements PreferenceDao<PreferenceKey>,
        ApplicationListener<ReloadFilePreferencesEvent> {

    private static final Logger EXC_LOGGER = LoggerFactory.getLogger(LoggingCategories.INTEGRATION_EXCEPTION);
    @Autowired
    private ApplicationContext ctx;
    @Autowired
    @Qualifier("preferencesUnmarshaller")
    private Unmarshaller unmarshaller;
    @Autowired
    @Value(Constants.APPLICATION_INITIAL_PROPERTIES)
    private String fileName;
    private volatile Resource fileResource;
    private volatile Preferences preferences;
    private Map<PreferenceKey, AbstractPreference> prefs = new ConcurrentHashMap<PreferenceKey, AbstractPreference>();

    /** The URL to the initial preferences XML file. Default {@value} */
    public static final String INITIAL_PREFERENCES_FILE = "classpath:org/openwms/core/integration/file/initial-preferences.xml";
    /** Springs component name. */
    public static final String COMPONENT_NAME = "preferencesFileDao";

    /**
     * {@inheritDoc}
     *
     * @see org.openwms.core.configuration.PreferenceDao#findByKey(java.io.Serializable)
     */
    @Override
    public AbstractPreference findByKey(PreferenceKey id) {
        return prefs.get(id);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.openwms.core.configuration.PreferenceDao#findByType(Class)
     */
    @Override
    public <T extends AbstractPreference> List<T> findByType(Class<T> clazz) {
        return preferences.getOfType(clazz);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.openwms.core.configuration.PreferenceDao#findByType(java.lang.Class, java.lang.String)
     */
    @Override
    public <T extends AbstractPreference> List<T> findByType(Class<T> clazz, String owner) {
        // FIXME [scherrer] : delegate to preferences
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.openwms.core.configuration.PreferenceDao#findAll()
     */
    @Override
    public List<AbstractPreference> findAll() {
        return preferences.getAll();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    @Override
    public void onApplicationEvent(ReloadFilePreferencesEvent event) {
        reloadResources();
    }

    /**
     * On bean initialization load all preferences into a Map.
     */
    @PostConstruct
    private void loadResources() {
        if (preferences == null) {
            initResource();
            try {
                preferences = (Preferences) unmarshaller.unmarshal(new StreamSource(fileResource.getInputStream()));
                for (AbstractPreference pref : preferences.getAll()) {
                    if (prefs.containsKey(pref.getPrefKey())) {
                        throw new NoUniqueResultException("Preference with key " + pref.getPrefKey()
                                + " already loaded.");
                    }
                    prefs.put(pref.getPrefKey(), pref);
                }
            } catch (XmlMappingException xme) {
                EXC_LOGGER.error("Exception while unmarshalling from " + fileName, xme);
                throw new DataException("Exception while unmarshalling from " + fileName, xme);
            } catch (IOException ioe) {
                EXC_LOGGER.error("Exception while accessing the resource with name " + fileName, ioe);
                throw new ResourceNotFoundException("Exception while accessing the resource with name " + fileName, ioe);
            }
        }
    }

    private void reloadResources() {
        preferences = null;
        prefs.clear();
        loadResources();
    }

    private void initResource() {
        if (StringUtils.isNotEmpty(fileName)) {
            fileResource = ctx.getResource(fileName);
        }
        if (fileResource == null || !fileResource.exists()) {
            fileResource = ctx.getResource(INITIAL_PREFERENCES_FILE);
            if (!fileResource.exists()) {
                EXC_LOGGER.error("Resources with name " + fileName + ":" + INITIAL_PREFERENCES_FILE
                        + " could not be resolved");
                throw new ResourceNotFoundException("Resources with name " + fileName + ":" + INITIAL_PREFERENCES_FILE
                        + " could not be resolved");
            }
        }
    }
}