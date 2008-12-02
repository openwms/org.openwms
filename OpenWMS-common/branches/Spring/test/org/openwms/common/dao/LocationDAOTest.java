/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao;

import java.io.Serializable;

import org.junit.Test;
import org.openwms.AbstractJpaSpringContextTests;

/**
 * A LocationDAOTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class LocationDAOTest extends AbstractJpaSpringContextTests {

    private GenericDAO<Serializable, Serializable> locationDAO;

    public void setLocationDAO(GenericDAO<Serializable, Serializable> locationDAO) {
	this.locationDAO = locationDAO;
    }

    /**
     * Test method for {@link org.openwms.common.dao.core.AbstractGenericJpaDAO#findById(java.io.Serializable)}.
     */
    @Test
    public final void testFindById() {
	fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.openwms.common.dao.core.AbstractGenericJpaDAO#findAll()}.
     */
    @Test
    public final void testFindAll() {
	fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link org.openwms.common.dao.core.AbstractGenericJpaDAO#findByQuery(java.lang.String, java.util.Map)}.
     */
    @Test
    public final void testFindByQuery() {
	fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.openwms.common.dao.core.AbstractGenericJpaDAO#findByUniqueId(java.lang.Object)}.
     */
    @Test
    public final void testFindByUniqueId() {
	fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.openwms.common.dao.core.AbstractGenericJpaDAO#save(java.lang.Object)}.
     */
    @Test
    public final void testSave() {
	fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.openwms.common.dao.core.AbstractGenericJpaDAO#remove(java.lang.Object)}.
     */
    @Test
    public final void testRemove() {
	fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.openwms.common.dao.core.AbstractGenericJpaDAO#persist(java.lang.Object)}.
     */
    @Test
    public final void testPersist() {
	fail("Not yet implemented");
    }

}
