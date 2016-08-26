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
package org.openwms.core.configuration.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openwms.core.configuration.PropertyScope;
import org.openwms.core.configuration.file.AbstractPreference;
import org.openwms.core.configuration.file.ApplicationPreference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * A ConfigurationIT.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class ConfigurationWithInitialPreferencesIT {

    private ApplicationPreference saved = new ApplicationPreference.Builder()
            .withKey("defaultLanguage")
            .withDescription("description")
            .withMinimum(10)
            .withMaximum(100)
            .withFloatValue(22.1F)
            .withValue("en_US")
            .build();

    @Autowired
    private ConfigurationController testee;

    public
    @Test
    void testSave() throws Exception {
        Iterable<AbstractPreference> all = testee.findAll();
        assertThat(all)
                .isNotNull()
                .hasSize(1)
                .contains(saved);
        assertThat(all)
                .extracting("key", "type", "value", "minimum", "maximum", "floatValue")
                .contains(tuple("defaultLanguage", PropertyScope.APPLICATION, "en_US", 10, 100, 22.1F));
    }
}
