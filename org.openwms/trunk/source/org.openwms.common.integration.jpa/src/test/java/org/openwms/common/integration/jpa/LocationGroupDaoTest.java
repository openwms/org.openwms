/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.integration.jpa;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import org.junit.Test;
import org.openwms.common.domain.LocationGroup;
import org.openwms.common.integration.LocationGroupDao;
import org.openwms.common.test.AbstractJpaSpringContextTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * A LocationGroupDaoTest.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: $
 */
@ContextConfiguration("classpath:META-INF/spring/LocationTest-context.xml")
public class LocationGroupDaoTest extends AbstractJpaSpringContextTests {

    @Autowired(required = true)
    protected LocationGroupDao dao;

    @Test
    public final void testDuplicateLocationGroups() {
        LocationGroup locationGroup = new LocationGroup("FIRST_LG");
        LocationGroup locationGroup2 = new LocationGroup("FIRST_LG");
        dao.persist(locationGroup);
        entityManager.flush();
        try {
            dao.persist(locationGroup2);
            entityManager.flush();
            fail("Persisting two LocationGroups with same id must be permitted");
        }
        catch (Exception e) {
            logger.debug("OK:Duplicate id for LocationGroup must be prevented by unique constraint.");
        }
    }

    @Test
    public final void testAddLocationGroup() {
        LocationGroup parent = new LocationGroup("TEST_GROUP_1");
        LocationGroup child = new LocationGroup("TEST_GROUP_2");
        dao.persist(parent);

        try {
            parent.addLocationGroup(null);
            fail("Not allowed to add null as LocationGroup on parent");
        }
        catch (IllegalArgumentException iae) {
            logger.debug("OK: Exception when trying to add null as parent LocationGroup");
        }
        int noChildren = parent.getLocationGroups().size();
        parent.addLocationGroup(child);
        entityManager.flush();
        parent = dao.save(parent);
        assertTrue("The number of child LocationGroups must be increased by one", noChildren + 1 == parent
                .getLocationGroups().size());
        assertTrue("Parent LocationGroup is not the right one", child.getParent() == parent);
        assertFalse("Child LocationGroup must also be persisted", child.isNew());
    }

    @Test
    public final void testRemoveLocationGroups() {
        LocationGroup parent = new LocationGroup("TEST_GROUP_1");
        LocationGroup child = new LocationGroup("TEST_GROUP_2");
        parent.addLocationGroup(child);
        dao.persist(parent);
        entityManager.flush();
        try {
            parent.removeLocationGroup(null);
            fail("Not allowed to remove null as LocationGroup on parent");
        }
        catch (IllegalArgumentException iae) {
            logger.debug("OK: Exception when trying to remove null as parent LocationGroup");
        }
        int noChildren = parent.getLocationGroups().size();
        assertEquals("The parent must be set on the child", child.getParent(), parent);
        parent.removeLocationGroup(child);
        assertNull("The parent has to be removed as LocationGroup from the child", child.getParent());
        assertTrue("The number of child LocationGroups must be decreased by one", noChildren - 1 == parent
                .getLocationGroups().size());;
    }
}