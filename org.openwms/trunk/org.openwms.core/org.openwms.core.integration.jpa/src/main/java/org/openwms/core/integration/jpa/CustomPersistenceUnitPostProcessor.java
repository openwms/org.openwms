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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

/**
 * A CustomPersistenceUnitPostProcessor.
 * <p>
 * An extension of the default Spring implementation.
 * </p>
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @see org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor
 * @since 0.1
 * @deprecated as OSGi is used since 0.1
 */
@Deprecated
public class CustomPersistenceUnitPostProcessor implements PersistenceUnitPostProcessor {

    /**
     * Default location of the persistence jar file: "classpath*:OpenWMS*".
     */
    public static final String DEFAULT_PERSISTENCE_JAR_FILES = "classpath*:OpenWMS*";

    // Location of persistence jar file(s)
    private String[] persistenceJarFiles = new String[] { DEFAULT_PERSISTENCE_JAR_FILES };
    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    private List<Resource> jarFiles = new ArrayList<Resource>();

    private void resolveJarFiles() {
        try {
            for (int i = 0; i < persistenceJarFiles.length; i++) {
                Resource[] resources = this.resourcePatternResolver.getResources(persistenceJarFiles[i]);
                for (Resource resource : resources) {
                    jarFiles.add(resource);
                }
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException("Cannot load persistence jar file from ", ex);
        }
    }

    /**
     * Specify multiple locations of <code>persistence.xml</code> files to load.
     * These locations can be specified as Spring resource locations and/or as a
     * location patterns.
     * <p>
     * Default is "classpath*:META-INF/persistence.xml".
     * </p>
     * 
     * @param pJarFiles
     *            An array of Spring resources identifying the location of the
     *            <code>persistence.xml</code> files to read
     */
    public void setPersistenceJarFiles(String[] pJarFiles) {
        this.persistenceJarFiles = pJarFiles;
    }

    /**
     * @param pui
     *            {@link javax.persistence.spi.PersistenceUnitInfo}
     * @see org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor#postProcessPersistenceUnitInfo(org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo)
     */
    @Override
    public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui) {
        if (jarFiles.size() == 0) {
            resolveJarFiles();
        }
        try {
            for (Resource resource : jarFiles) {
                pui.addJarFileUrl(resource.getURL());
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot parse persistence unit from " + pui.getPersistenceUnitName(), e);
        }
    }

}
