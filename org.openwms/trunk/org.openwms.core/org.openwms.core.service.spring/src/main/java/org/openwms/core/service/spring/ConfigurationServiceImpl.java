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

import org.openwms.core.domain.preferences.ApplicationPreference;
import org.openwms.core.domain.preferences.ModulePreference;
import org.openwms.core.domain.system.AbstractPreference;
import org.openwms.core.domain.system.PreferenceKey;
import org.openwms.core.domain.values.Unit;
import org.openwms.core.integration.PreferenceDao;
import org.openwms.core.integration.PreferenceWriter;
import org.openwms.core.service.ConfigurationService;
import org.openwms.core.util.event.MergePropertiesEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A ConfigurationServiceImpl is a transactional Spring powered service
 * implementation.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.service.ConfigurationService
 */
@Transactional
@Service("configurationService")
public class ConfigurationServiceImpl extends EntityServiceImpl<AbstractPreference, Long> implements
        ConfigurationService, ApplicationListener<MergePropertiesEvent> {

    @Autowired
    @Qualifier("preferencesJpaDao")
    private PreferenceWriter<Long> dao;
    @Autowired
    @Qualifier("preferencesFileDao")
    private PreferenceDao<PreferenceKey> fileDao;

    /**
     * {@inheritDoc}
     * 
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    @Override
    public void onApplicationEvent(MergePropertiesEvent event) {
        mergeApplicationProperties();
    }

    private void mergeApplicationProperties() {
        List<AbstractPreference> prefs = fileDao.findAll();
        List<AbstractPreference> persistedPrefs = dao.findAll();
        for (AbstractPreference pref : prefs) {
            if (!persistedPrefs.contains(pref)) {
                dao.save(pref);
            }
        }
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
     */
    @Override
    public List<ApplicationPreference> findApplicationProperties() {
        return dao.findByType(ApplicationPreference.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ModulePreference> findModuleProperties() {
        return dao.findByType(ModulePreference.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends Unit> getAllUnits() {
        return null;
    }
}
