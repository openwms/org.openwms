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

import java.net.URL;
import java.util.List;

import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;

/**
 * A CustomPersistenceUnitManager.
 * <p>
 * This class customizes the default {@link DefaultPersistenceUnitManager}
 * implementation and adds support for multiple jar files with Entity classes.
 * </p>
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @deprecated as OSGi is used since 0.1
 */
@Deprecated
public class CustomPersistenceUnitManager extends DefaultPersistenceUnitManager {

    /**
     * Add all jar files to the
     * {@link javax.persistence.spi.PersistenceUnitInfo}.
     * 
     * @param pui
     *            {@link javax.persistence.spi.PersistenceUnitInfo}
     * @see org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager#postProcessPersistenceUnitInfo(org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo)
     */
    @Override
    protected void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui) {
        super.postProcessPersistenceUnitInfo(pui);
        pui.addJarFileUrl(pui.getPersistenceUnitRootUrl());
        MutablePersistenceUnitInfo oldPui = getPersistenceUnitInfo(pui.getPersistenceUnitName());
        if (oldPui != null) {
            List<URL> urls = oldPui.getJarFileUrls();
            for (URL url : urls) {
                pui.addJarFileUrl(url);
            }
        }
    }
}
