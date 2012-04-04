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
package org.openwms.core.domain.preferences;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.Test;
import org.openwms.core.domain.system.AbstractPreference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A ApplicationPreferenceTest. Test unmarshalling a valid XML document of
 * preferences.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
public class ApplicationPreferenceTest {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationPreferenceTest.class);

    /**
     * Just test to validate the given XML file against the schema declaration.
     * If the XML file is not compliant with the schema, the test will fail.
     * 
     * @throws Throwable
     *             any error
     */
    @Test
    public void testReadPreferences() throws Throwable {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File("src/test/resources/preferences.xsd"));
        // Schema schema = schemaFactory.newSchema(new
        // URL("http://www.openwms.org/schema/preferences.xsd"));
        JAXBContext ctx = JAXBContext.newInstance("org.openwms.core.domain.preferences");
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        unmarshaller.setSchema(schema);
        unmarshaller.setEventHandler(new ValidationEventHandler() {
            @Override
            public boolean handleEvent(ValidationEvent event) {
                RuntimeException ex = new RuntimeException(event.getMessage(), event.getLinkedException());
                logger.error(ex.getMessage());
                throw ex;
            }
        });

        Preferences prefs = Preferences.class.cast(unmarshaller.unmarshal(new File(
                "src/test/resources/org/openwms/core/domain/system/preferences.xml")));
        for (AbstractPreference pref : prefs.getApplications()) {
            logger.info(pref.toString());
        }
        for (AbstractPreference pref : prefs.getModules()) {
            logger.info(pref.toString());
        }
        for (AbstractPreference pref : prefs.getUsers()) {
            logger.info(pref.toString());
        }
    }
}
