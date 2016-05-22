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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openwms.core.configuration.file.AbstractPreference;
import org.openwms.core.configuration.file.ApplicationPreference;
import org.openwms.core.configuration.file.MockApplicationPreference;
import org.openwms.core.configuration.file.ModulePreference;
import org.openwms.core.configuration.file.PreferenceDao;
import org.openwms.core.event.MergePropertiesEvent;
import org.openwms.core.test.AbstractMockitoTests;

/**
 * A ConfigurationServiceTest.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
public class ConfigurationServiceTest extends AbstractMockitoTests {

    private static final String PERSISTED_APP_PREF1 = "persPref1";
    private static final String PERSISTED_APP_PREF2 = "persPref2";
    private List<AbstractPreference> filePrefs = new ArrayList<AbstractPreference>();
    private List<AbstractPreference> persistedPrefs = new ArrayList<AbstractPreference>();

    @Mock(name = "preferencesJpaDao")
    private PreferenceRepository writer;
    @Mock(name = "preferencesFileDao")
    private PreferenceDao reader;
    @InjectMocks
    private ConfigurationServiceImpl srv = new ConfigurationServiceImpl();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Setting up some test data.
     */
    @Override
    public void doBefore() {
        // Prepare some preferences coming from a file
        filePrefs.add(new ApplicationPreference("filePref1"));
        filePrefs.add(new ApplicationPreference("filePref2"));
        // And a few from the database
        persistedPrefs.add(new ApplicationPreference(PERSISTED_APP_PREF1));
        persistedPrefs.add(new ApplicationPreference(PERSISTED_APP_PREF2));
    }

    /**
     * @see org.openwms.core.test.AbstractMockitoTests#doAfter()
     */
    @SuppressWarnings("unchecked")
    @Override
    public void doAfter() {
        filePrefs.clear();
        persistedPrefs.clear();
        reset(writer);
        reset(reader);
    }

    /**
     * Test method for {@link org.openwms.core.configuration.ConfigurationServiceImpl#onApplicationEvent(org.openwms.core.event.MergePropertiesEvent)}
     * .
     * <p>
     * Test whether new file preferences are saved to the database after an MergePropertiesEvent is thrown.
     */
    @Test
    public final void testOnApplicationEvent() {
        // That one must not be persisted
        filePrefs.add(new ApplicationPreference(PERSISTED_APP_PREF2));
        when(reader.findAll()).thenReturn(filePrefs);
        when(writer.findAll()).thenReturn(persistedPrefs);

        srv.onApplicationEvent(new MergePropertiesEvent(this));
        // new file preferences should be saved
        verify(writer).save(new ApplicationPreference("filePref1"));
        verify(writer).save(new ApplicationPreference("filePref2"));
        // save must not be called for an already existing preference.
        verify(writer, never()).save(new ApplicationPreference(PERSISTED_APP_PREF2));
    }

    /**
     * Test method for {@link org.openwms.core.configuration.ConfigurationServiceImpl#findAll()} .
     * <p>
     * Test that the service to the right repository and the returned list is the right one.
     */
    @Test
    public final void testFindAll() {
        when(writer.findAll()).thenReturn(persistedPrefs);
        assertEquals(persistedPrefs, srv.findAll());
        verify(writer, times(1)).findAll();
    }

    /**
     * Test method for {@link org.openwms.core.configuration.ConfigurationServiceImpl#findByType(java.lang.Class)} .
     */
    @Test
    public final void testFindByType() {
        filePrefs.add(new ModulePreference("CORE", PERSISTED_APP_PREF2));
        when(writer.findByType(ModulePreference.class, "CORE")).thenReturn(
                Collections.singletonList(new ModulePreference("CORE", PERSISTED_APP_PREF2)));

        Collection<ModulePreference> prefs = srv.findByType(ModulePreference.class, "CORE");
        verify(writer).findByType(ModulePreference.class, "CORE");
        Assert.assertTrue(prefs.size() == 1);
    }

    /**
     * Test method for {@link org.openwms.core.configuration.ConfigurationServiceImpl#save(org.openwms.core.system.AbstractPreference)} .
     * <p>
     * Save with <code>null</code> must throw an exception IAE.
     */
    @Test
    public final void testSaveNull() {
        thrown.expect(IllegalArgumentException.class);
        srv.save(null);
        fail("Expected to catch an IllegalArgumentException when calling save() with null");
    }

    /**
     * Test method for {@link org.openwms.core.configuration.ConfigurationServiceImpl#save(org.openwms.core.system.AbstractPreference)} .
     */
    @Test
    public final void testSaveNewEntity() {
        when(writer.findByType(ApplicationPreference.class)).thenReturn(
                Collections.singletonList(new ApplicationPreference("PERSISTED")));

        ApplicationPreference newEntity = new ApplicationPreference("TRANSIENT");
        srv.save(newEntity);
        verify(writer).save(newEntity);
    }

    /**
     * Test method for {@link org.openwms.core.configuration.ConfigurationServiceImpl#save(org.openwms.core.system.AbstractPreference)} .
     */
    @Test
    public final void testSaveDuplicatedEntity() {
        when(writer.findByType(ApplicationPreference.class)).thenReturn(
                Collections.singletonList(new ApplicationPreference("TRANSIENT")));

        ApplicationPreference newEntity = new ApplicationPreference("TRANSIENT");
        srv.save(newEntity);
        verify(writer).save(newEntity);
    }

    /**
     * Test method for {@link org.openwms.core.configuration.ConfigurationServiceImpl#save(org.openwms.core.system.AbstractPreference)} .
     */
    @Test
    public final void testSaveExistingEntity() {
        MockApplicationPreference mock = new MockApplicationPreference("TRANSIENT");
        when(writer.findByType(MockApplicationPreference.class)).thenReturn(
                Collections.singletonList(mock));
        when(writer.save(mock)).thenReturn(mock);

        assertEquals(mock, srv.save(mock));
        verify(writer).save(mock);
    }

    /**
     * Test method for {@link org.openwms.core.configuration.ConfigurationServiceImpl#remove(org.openwms.core.system.AbstractPreference)} .
     * <p>
     * Remove with <code>null</code> must throw an exception IAE.
     */
    @Test
    public final void testRemoveNull() {
        thrown.expect(IllegalArgumentException.class);
        srv.delete(null);
        fail("Expected to catch an IllegalArgumentException when calling remove() with null");
    }

    /**
     * Test method for {@link org.openwms.core.configuration.ConfigurationServiceImpl#remove(org.openwms.core.system.AbstractPreference)} .
     * <p>
     * Simply delegate to the writer.
     */
    @Test
    public final void testRemove() {
        srv.delete(new ApplicationPreference("TRANSIENT"));
        verify(writer).delete(new ApplicationPreference("TRANSIENT"));
    }
}