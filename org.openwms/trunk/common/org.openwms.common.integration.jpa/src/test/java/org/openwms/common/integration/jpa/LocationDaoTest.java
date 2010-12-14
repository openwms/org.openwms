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
package org.openwms.common.integration.jpa;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.system.Message;
import org.openwms.common.integration.LocationDao;
import org.openwms.core.test.AbstractJpaSpringContextTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * A LocationDaoTest.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@ContextConfiguration("classpath:META-INF/spring/LocationDaoTest-context.xml")
public class LocationDaoTest extends AbstractJpaSpringContextTests {

    @Autowired(required = true)
    private LocationDao locationDao;
    private List<Location> locations;

    /**
     * Persist a location for each test execution.
     */
    @Before
    public void onSetUpInTransaction() {
        locationDao.persist(new Location(new LocationPK("area", "aisle", "x", "y", "z")));
        // TODO [scherrer] Do we need to flush and clear after persist?
    }

    /**
     * Try to find the persisted location using the findAll query.
     */
    @Test
    public final void testFindAll() {
        assertNotNull("Cannot query a list of all Locations", locationDao.findAll());
        logger.debug("OK:All Location found by query");
    }

    /**
     * Try to find the persisted location using the findById query.
     */
    @Test
    public final void testFindById() {
        locations = locationDao.findAll();
        assertNotNull("Cannot find Location by Id", locationDao.findById(locations.get(0).getId()));
        logger.debug("OK:Location found by id query");
    }

    /**
     * Try to find the persisted location using the findByUniqeId query, that
     * means fetching by the business key.
     */
    @Test
    public final void testFindByUniqueId() {
        Location known = locationDao.findByUniqueId(new LocationPK("area", "aisle", "x", "y", "z"));
        assertNotNull("Location not found by id", known);
        logger.debug("OK:Location found by unique id query");

        Location unknown = locationDao.findByUniqueId(new LocationPK("AREA", "AISLE", "X", "Y", "Z"));
        assertNull("Unknown Location found by key", unknown);
        logger.debug("OK:Unknown Location not found by unique id query");
    }

    /**
     * Test {@link Location#isNew()} and try to persist.
     */
    @Test
    public final void testPersist() {
        Location l = new Location(new LocationPK("NEW", "NEW", "NEW", "NEW", "NEW"));
        assertNull("PK should be NULL", l.getId());
        assertTrue("isNew should return true for a transient entity", l.isNew());
        locationDao.persist(l);
        assertNotNull("PK should be set", l.getId());
        assertFalse("isNew should return false for a persistend entity", l.isNew());
    }

    /**
     * Try to persist and remove.
     */
    @Test
    public final void testPersistAndRemove() {
        Location l = new Location(new LocationPK("NEW", "NEW", "NEW", "NEW", "NEW"));
        assertNull("PK should be NULL", l.getId());
        locationDao.persist(l);
        assertNotNull("PK should be set", l.getId());
        Location l2 = locationDao.findById(l.getId());
        assertNotNull("PK should be set", l2.getId());
        locationDao.remove(l);
        l2 = locationDao.findById(l2.getId());
        assertNull("PK should be NULL", l2);
    }

    /**
     * Test adding messages to the location. Test cascading persistence.
     */
    @Test
    public final void testAddingErrorMessages() {
        LocationPK pk = new LocationPK("UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN");
        Location location = new Location(pk);
        locationDao.persist(location);

        location.addMessage(new Message(1, "Errormessage 1"));
        location.addMessage(new Message(2, "Errormessage 2"));

        locationDao.save(location);

        assertEquals("Expected 2 persisted Messages added to the location", 2, location.getMessages().size());
        Query query = entityManager.createQuery("select count(m) from Message m");
        Long cnt = (Long) query.getSingleResult();
        assertEquals("Expected 2 persisted Messages in MESSAGE table", 2, cnt.intValue());
    }
}
