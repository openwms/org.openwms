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
package org.openwms.core.integration.file;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;
import org.openwms.core.Constants;
import org.openwms.core.domain.preferences.Preferences;
import org.openwms.core.domain.system.AbstractPreference;
import org.openwms.core.domain.system.PreferenceKey;
import org.openwms.core.integration.PreferenceDao;
import org.openwms.core.integration.exception.DataException;
import org.openwms.core.integration.exception.NoUniqueResultException;
import org.openwms.core.integration.exception.ResourceNotFoundException;
import org.openwms.core.util.event.PropertiesChangedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.stereotype.Repository;

/**
 * A PreferencesDaoImpl.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Repository("preferencesFileDao")
public class PreferencesDaoImpl implements PreferenceDao<PreferenceKey>, ApplicationListener<PropertiesChangedEvent> {

    public static final String INITIAL_PREFERENCES_FILE = "classpath:org/openwms/core/integration/file/initial-preferences.xml";

    @Autowired
    private Unmarshaller unmarshaller;
    @Autowired
    private ApplicationContext ctx;
    @Autowired
    @Value(Constants.APPLICATION_INITIAL_PROPERTIES)
    private String fileName;
    private Resource fileResource;
    private Preferences preferences;
    private Map<PreferenceKey, AbstractPreference> prefs = new HashMap<PreferenceKey, AbstractPreference>();

    /**
     * Create a new PreferencesDaoImpl.
     * 
     * @param fileName
     *            The name of the properties file
     */
    private PreferencesDaoImpl() {}

    /**
     * @see org.openwms.core.integration.PreferenceDao#findByKey(PreferenceKey)
     */
    @Override
    public AbstractPreference findByKey(PreferenceKey id) {
        return prefs.get(id);
    }

    /**
     * @see org.openwms.core.integration.PreferenceDao#findByType(org.openwms.core.domain.system.AbstractPreference)
     */
    @Override
    public <T extends AbstractPreference> List<T> findByType(Class<T> clazz) {
        return preferences.getOfType(clazz);
    }

    /**
     * @see org.openwms.core.integration.PreferenceDao#findAll()
     */
    @Override
    public List<AbstractPreference> findAll() {
        return preferences.getAll();
    }

    /**
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    @Override
    public void onApplicationEvent(PropertiesChangedEvent event) {
        reloadResources();
    }

    /**
     * On bean initialization load all preferences into a Map.
     */
    @PostConstruct
    public void loadResources() {
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
                throw new DataException("Exception while unmarshalling from " + fileName, xme);
            } catch (IOException ioe) {
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
                throw new ResourceNotFoundException("Resources with name " + fileName + ":" + INITIAL_PREFERENCES_FILE
                        + " could not be resolved");
            }
        }
    }
}
