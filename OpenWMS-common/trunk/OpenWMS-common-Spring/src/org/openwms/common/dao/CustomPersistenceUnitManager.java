/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;

/**
 * 
 * A CustomPersistenceUnitManager.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 877 $
 */
public class CustomPersistenceUnitManager extends DefaultPersistenceUnitManager {
	List<URL> url = new ArrayList<URL>();

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
