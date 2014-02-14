/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
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
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.core.integration.jpa;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openwms.core.domain.preferences.ApplicationPreference;
import org.openwms.core.domain.system.AbstractPreference;
import org.openwms.core.integration.PreferenceWriter;
import org.openwms.core.test.AbstractJpaSpringContextTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

/**
 * A PreferencesDaoTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@ContextConfiguration("classpath:core-jpa-test-context.xml")
public class PreferencesDaoTest extends AbstractJpaSpringContextTests {

    @Autowired
    @Qualifier("preferencesJpaDao")
    private PreferenceWriter<Long> dao;

    /**
     * Setup some test data.
     */
    @Before
    public final void onSetup() {
        entityManager.persist(new ApplicationPreference("APP33"));
        entityManager.flush();
        entityManager.clear();
    }

    /**
     * Test whether the returned instance is the same.
     */
    @Ignore
    @Test
    public final void testEquality() {
        List<AbstractPreference> prefs = dao.findAll();
        AbstractPreference abPref = new ApplicationPreference("APP33");
        assertTrue(prefs.get(0).equals(abPref));
        assertTrue(abPref.equals(prefs.get(0)));
        assertTrue(prefs.contains(abPref));
    }
}