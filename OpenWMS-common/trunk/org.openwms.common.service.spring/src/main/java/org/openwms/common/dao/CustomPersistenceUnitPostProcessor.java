/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;
import org.springframework.util.ResourceUtils;

/**
 * 
 * A CustomPersistenceUnitPostProcessor.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 877 $
 */
@Deprecated
public class CustomPersistenceUnitPostProcessor implements PersistenceUnitPostProcessor {

	/**
	 * Default location of the persistence jar file: "classpath*:OpenWMS*".
	 */
	public final static String DEFAULT_PERSISTENCE_JAR_FILES = "classpath*:OpenWMS*";

	/** Location of persistence jar file(s) */
	private String[] persistenceJarFiles = new String[] { DEFAULT_PERSISTENCE_JAR_FILES };

	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

	private List<Resource> jarFiles = new ArrayList<Resource>();

	private void resolveJarFiles() {
		try {
			for (int i = 0; i < persistenceJarFiles.length; i++) {
				System.out.println("--" + ResourceUtils.getURL(persistenceJarFiles[i]));
				Resource[] resources = this.resourcePatternResolver.getResources(persistenceJarFiles[i]);
				for (Resource resource : resources) {
					jarFiles.add(resource);
				}
			}
		}
		catch (IOException ex) {
			throw new IllegalArgumentException("Cannot load persistence jar file from ", ex);
		}
	}

	/**
	 * Specify multiple locations of <code>persistence.xml</code> files to load. These can be specified as Spring
	 * resource locations and/or location patterns.
	 * <p>
	 * Default is "classpath*:META-INF/persistence.xml".
	 * 
	 * @param persistenceXmlLocations
	 *            an array of Spring resource Strings identifying the location of the <code>persistence.xml</code> files
	 *            to read
	 */
	public void setPersistenceJarFiles(String[] persistenceJarFiles) {
		this.persistenceJarFiles = persistenceJarFiles;
	}

	public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui) {
		if (jarFiles.size() == 0) {
			resolveJarFiles();
		}
		try {
			for (Resource resource : jarFiles) {
				pui.addJarFileUrl(resource.getURL());
			}
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Cannot parse persistence unit from " + pui.getPersistenceUnitName(), e);
		}
	}

}
