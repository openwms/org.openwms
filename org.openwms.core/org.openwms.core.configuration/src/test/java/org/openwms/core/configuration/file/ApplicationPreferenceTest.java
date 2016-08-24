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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

/**
 * A ApplicationPreferenceTest. Test unmarshalling a valid XML document of preferences.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.2
 * @since 0.1
 */
public class ApplicationPreferenceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationPreferenceTest.class);
    private static final String APP_PREF1 = "APP_PREF1";
    private static final String APP_PREF2 = "APP_PREF1";
    private static final String APP_PREF3 = "APP_PREF2";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Test creation.
     */
    @Test
    public final void testCreation() {
        ApplicationPreference applicationPreference = new ApplicationPreference(APP_PREF1);
        assertThat(applicationPreference.getKey())
                .isEqualTo(APP_PREF1);
    }

    /**
     * Negative creation test.
     */
    @Test
    public final void testCreationNegative() {

        assertThatThrownBy(
                () -> new ApplicationPreference(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Not allowed to create an ApplicationPreference with an empty key");

        assertThatThrownBy(
                () -> new ApplicationPreference(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Not allowed to create an ApplicationPreference with an empty key");
    }

    /**
     * Test that a proper PreferenceKey is returned.
     */
    @Test
    public final void testKeyGeneration() {
        ApplicationPreference ap1 = new ApplicationPreference(APP_PREF1);
        ApplicationPreference ap2 = new ApplicationPreference(APP_PREF2);
        ApplicationPreference ap3 = new ApplicationPreference(APP_PREF3);

        assertThat(ap1.getPrefKey()).isEqualTo(ap2.getPrefKey());
        assertThat(ap1.getPrefKey()).isNotEqualTo(ap3.getPrefKey());
    }

    /**
     * Test that PreferenceKeys can be used for keys.
     */
    @Test
    public final void testKeyUniqueness() {
        ApplicationPreference ap1 = new ApplicationPreference(APP_PREF1);
        ApplicationPreference ap2 = new ApplicationPreference(APP_PREF2);
        ApplicationPreference ap3 = new ApplicationPreference(APP_PREF3);
        Map<PreferenceKey, ApplicationPreference> map = new HashMap<>();
        map.put(ap1.getPrefKey(), ap1);
        map.put(ap2.getPrefKey(), ap2);

        assertThat(map).hasSize(1);

        map.put(ap3.getPrefKey(), ap3);
        assertThat(map).hasSize(2);
    }

    /**
     * Just test to validate the given XML file against the schema declaration. If the XML file is not compliant with the schema, the test
     * will fail.
     *
     * @throws Exception any error
     */
    @Test
    public void testReadPreferences() throws Exception {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(ResourceUtils.getFile("classpath:preferences.xsd"));
        // Schema schema = schemaFactory.newSchema(new
        // URL("http://www.openwms.org/schema/preferences.xsd"));
        JAXBContext ctx = JAXBContext.newInstance("org.openwms.core.configuration.file");
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
                "classpath:org/openwms/core/configuration/file/preferences.xml")));
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
        ApplicationPreference ap1 = new ApplicationPreference(APP_PREF1);
        ApplicationPreference ap2 = new ApplicationPreference(APP_PREF2);
        ApplicationPreference ap3 = new ApplicationPreference(APP_PREF3);
        ApplicationPreference ap4 = new ApplicationPreference();

        // Just the key is considered
        assertThat(ap1).isEqualTo(ap1);
        assertThat(ap1).isEqualTo(ap2);
        assertThat(ap1).isNotEqualTo(ap3);
        assertThat(ap4).isNotEqualTo(ap3);

        // Test behavior in hashed collections
        Set<ApplicationPreference> applicationPreferences = new HashSet<>();
        applicationPreferences.add(ap1);
        applicationPreferences.add(ap2);
        assertThat(applicationPreferences).hasSize(1);
        applicationPreferences.add(ap3);
        assertThat(applicationPreferences).hasSize(2);
    }
}