/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao;

import java.util.List;

import org.junit.Test;
import org.openwms.AbstractJpaSpringContextTests;
import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationPK;

/**
 * A GenericJpaDAOTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class GenericJpaDAOTest extends AbstractJpaSpringContextTests {

    private GenericDAO<Location, Long> locationDao;

    public void setLocationDao(GenericDAO<Location, Long> locationDao) {
	this.locationDao = locationDao;
    }

    /**
     * Test method for {@link org.openwms.common.dao.core.AbstractGenericJpaDAO#findAll()}.
     */
    @Test
    public final void testFindAll() {
	List<Location> l = locationDao.findAll();
    }

    /**
     * Test method for
     * {@link org.openwms.common.dao.core.AbstractGenericJpaDAO#findByQuery(java.lang.String, java.util.Map)}.
     */
    @Test
    public final void testFindByQuery() {
	Location l = locationDao.findById(new Long(1));
	System.out.println("Hallo");
    }

    /**
     * Test method for {@link org.openwms.common.dao.core.AbstractGenericJpaDAO#findByUniqueId(java.lang.Object)}.
     */
    @Test
    public final void testFindByUniqueId() {
	Location l = locationDao.findByUniqueId(new LocationPK("AREA", "AISLE", "X", "Y", "Z"));
    }

    /**
     * Test method for {@link org.openwms.common.dao.core.AbstractGenericJpaDAO#save(java.io.Serializable)}.
     */
    @Test
    public final void testSave() {
	Location l = new Location(new LocationPK("NEW", "NEW", "NEW", "NEW", "NEW"));
	assertNull("PK should be NULL", l.getId());
	locationDao.persist(l);
	assertNotNull("PK should be set", l.getId());
	l.setDescription("Hello");
	Location l2 = locationDao.save(l);
	// TODO: Assert
    }

    /**
     * Test method for {@link org.openwms.common.dao.core.AbstractGenericJpaDAO#persist(java.io.Serializable)}.
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

}
