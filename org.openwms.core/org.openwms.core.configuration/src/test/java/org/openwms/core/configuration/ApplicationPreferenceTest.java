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

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

/**
 * A ApplicationPreferenceTest. Test unmarshalling a valid XML document of preferences.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public class ApplicationPreferenceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationPreferenceTest.class);
    private static final String APP_PREF1 = "APP_PREF1";
    private static final String APP_PREF2 = "APP_PREF1";
    private static final String APP_PREF3 = "APP_PREF2";

    /**
     * Test creation.
     */
    @Test
    public final void testCreation() {
        ApplicationPreference applicationPreference = new ApplicationPreference(APP_PREF1);
        Assert.assertEquals(APP_PREF1, applicationPreference.getKey());
    }

    /**
     * Negative creation test.
     */
    @Test
    public final void testCreationNegative() {
        try {
            new ApplicationPreference(null);
            Assert.fail("IAE expected when creating ApplicationPreference(String) with key equals to null");
        } catch (IllegalArgumentException iae) {
        }
        try {
            new ApplicationPreference("");
            Assert.fail("IAE expected when creating ApplicationPreference(String) with empty key");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * Test that a proper PreferenceKey is returned.
     */
    @Test
    public final void testKeyGeneration() {
        ApplicationPreference applicationPreference1 = new ApplicationPreference(APP_PREF1);
        ApplicationPreference applicationPreference2 = new ApplicationPreference(APP_PREF2);
        ApplicationPreference applicationPreference3 = new ApplicationPreference(APP_PREF3);
        Assert.assertTrue(applicationPreference1.getPrefKey().equals(applicationPreference2.getPrefKey()));
        Assert.assertFalse(applicationPreference1.getPrefKey().equals(applicationPreference3.getPrefKey()));
    }

    /**
     * Test that PreferenceKeys can be used for keys.
     */
    @Test
    public final void testKeyUniqueness() {
        ApplicationPreference applicationPreference1 = new ApplicationPreference(APP_PREF1);
        ApplicationPreference applicationPreference2 = new ApplicationPreference(APP_PREF2);
        ApplicationPreference applicationPreference3 = new ApplicationPreference(APP_PREF3);

        Map<PreferenceKey, ApplicationPreference> map = new HashMap<PreferenceKey, ApplicationPreference>();
        map.put(applicationPreference1.getPrefKey(), applicationPreference1);
        map.put(applicationPreference2.getPrefKey(), applicationPreference2);
        Assert.assertEquals(1, map.size());
        map.put(applicationPreference3.getPrefKey(), applicationPreference3);
        Assert.assertEquals(2, map.size());
    }

    /**
     * Just test to validate the given XML file against the schema declaration. If the XML file is not compliant with the schema, the test
     * will fail.
     *
     * @throws Throwable any error
     */
    @Test
    public void testReadPreferences() throws Throwable {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(ResourceUtils.getFile("classpath:preferences.xsd"));
        // Schema schema = schemaFactory.newSchema(new
        // URL("http://www.openwms.org/schema/preferences.xsd"));
        JAXBContext ctx = JAXBContext.newInstance("org.openwms.core.domain.preferences");
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        unmarshaller.setSchema(schema);
        unmarshaller.setEventHandler(new ValidationEventHandler() {
            @Override
            public boolean handleEvent(ValidationEvent event) {
                RuntimeException ex = new RuntimeException(event.getMessage(), event.getLinkedException());
                LOGGER.error(ex.getMessage());
                throw ex;
            }
        });

        Preferences prefs = Preferences.class.cast(unmarshaller.unmarshal(ResourceUtils.getFile(
                "classpath:org/openwms/core/domain/system/preferences.xml")));
        for (AbstractPreference pref : prefs.getApplications()) {
            LOGGER.info(pref.toString());
        }
        for (AbstractPreference pref : prefs.getModules()) {
            LOGGER.info(pref.toString());
        }
        for (AbstractPreference pref : prefs.getUsers()) {
            LOGGER.info(pref.toString());
        }
    }

    /**
     * Test hashCode() and equals(obj).
     */
    @Test
    public final void testHashCodeEquals() {
        ApplicationPreference applicationPreference1 = new ApplicationPreference(APP_PREF1);
        ApplicationPreference applicationPreference2 = new ApplicationPreference(APP_PREF2);
        ApplicationPreference applicationPreference3 = new ApplicationPreference(APP_PREF3);
        ApplicationPreference applicationPreference4 = new ApplicationPreference();

        // Just the key is considered
        Assert.assertTrue(applicationPreference1.equals(applicationPreference1));
        Assert.assertTrue(applicationPreference1.equals(applicationPreference2));
        Assert.assertFalse(applicationPreference1.equals(applicationPreference3));
        Assert.assertFalse(applicationPreference4.equals(applicationPreference3));

        // Test behavior in hashed collections
        Set<ApplicationPreference> applicationPreferences = new HashSet<ApplicationPreference>();
        applicationPreferences.add(applicationPreference1);
        applicationPreferences.add(applicationPreference2);
        Assert.assertTrue(applicationPreferences.size() == 1);
        applicationPreferences.add(applicationPreference3);
        Assert.assertTrue(applicationPreferences.size() == 2);
    }
}