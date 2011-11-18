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

import java.util.List;

import org.openwms.core.domain.system.AbstractPreference;
import org.openwms.core.domain.system.PreferenceKey;
import org.openwms.core.integration.PreferenceDao;
import org.openwms.core.integration.PreferenceWriter;
import org.openwms.core.service.ConfigurationService;
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

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationServiceImpl.class);
    @Autowired
    @Qualifier("preferencesJpaDao")
    private PreferenceWriter<Long> dao;
    @Autowired
    @Qualifier("preferencesFileDao")
    private PreferenceDao<PreferenceKey> fileDao;
    /**
     * Springs service name.
     */
    public static final String COMPONENT_NAME = "configurationService";

    /**
     * {@inheritDoc}
     * 
     * When an event arrives all <i>new</i> preferences received from the file
     * provider are persisted. Already persisted preferences are ignored.
     * 
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    @Override
    public void onApplicationEvent(MergePropertiesEvent event) {
        mergeApplicationProperties();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.service.spring.EntityServiceImpl#findAll()
     */
    @Override
    public List<AbstractPreference> findAll() {
        return dao.findAll();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.service.ConfigurationService#findByType(java.lang.Class)
     */
    @Override
    public <T extends AbstractPreference> List<T> findByType(Class<T> clazz) {
        return dao.findByType(clazz);
    }

    /**
     * {@inheritDoc}
     * 
     * It is not allowed to call the implementation with a <code>null</code>
     * argument.
     * 
     * @throws IllegalArgumentException
     *             when <code>preference</code> is <code>null</code>
     * @see org.openwms.core.service.spring.EntityServiceImpl#save(org.openwms.core.domain.AbstractEntity)
     */
    @Override
    public AbstractPreference save(AbstractPreference preference) {
        AssertUtils.notNull(preference, "Not allowed to call save with a NULL preference");
        List<? extends AbstractPreference> preferences = dao.findByType(preference.getClass());
        if (preferences.contains(preference)) {
            if (preference.isNew()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Fake. Do not save or persist an entity that is transient and duplicated");
                    // FIXME [scherrer] : Do not nothing. clone and merge!
                    return preference;
                }
            }
            return dao.save(preference);
        }
        dao.persist(preference);
        return dao.save(preference);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IllegalArgumentException
     *             when <code>preference</code> is <code>null</code>
     * @see org.openwms.core.service.ConfigurationService#remove(org.openwms.core.domain.system.AbstractPreference)
     */
    @Override
    public void remove(AbstractPreference preference) {
        AssertUtils.notNull(preference, "Not allowed to call remove with a NULL preference");
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