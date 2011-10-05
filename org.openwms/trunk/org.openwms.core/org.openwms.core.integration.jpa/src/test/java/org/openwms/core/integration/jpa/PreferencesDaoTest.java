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

import static junit.framework.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.openwms.core.domain.system.AbstractPreference;
import org.openwms.core.domain.system.ApplicationPreference;
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
@ContextConfiguration("classpath:/org/openwms/core/integration/jpa/Test-context.xml")
public class PreferencesDaoTest extends AbstractJpaSpringContextTests {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    @Qualifier("preferencesJpaDao")
    protected PreferenceWriter<Long> dao;

    @Before
    public void onSetup() {
        em.persist(new ApplicationPreference("APP1"));
        em.flush();
        em.clear();
    }

    @Test
    public void testEquality() {
        List<AbstractPreference> prefs = dao.findAll();
        AbstractPreference abPref = new ApplicationPreference("APP1");
        assertTrue(prefs.get(0).equals(abPref));
        assertTrue(abPref.equals(prefs.get(0)));
        assertTrue(prefs.contains(abPref));
    }
}
