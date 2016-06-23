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
package org.openwms.common;

import org.junit.Assert;
import org.junit.Test;
import org.openwms.core.test.AbstractJpaSpringContextTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * A LocationGroupDaoTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@ContextConfiguration("classpath:common-jpa-test-context.xml")
public class LocationGroupDaoTest extends AbstractJpaSpringContextTests {

    @Autowired
    private LocationGroupDao dao;

    /**
     * Try to persist two locationGroups with the same business key.
     */
    @Test
    public final void testDuplicateLocationGroups() {
        LocationGroup locationGroup = new LocationGroup("FIRST_LG");
        LocationGroup locationGroup2 = new LocationGroup("FIRST_LG");
        dao.save(locationGroup);
        entityManager.flush();
        try {
            dao.save(locationGroup2);
            entityManager.flush();
            Assert.fail("Persisting two LocationGroups with same id must be permitted");
        } catch (Exception e) {
            LOGGER.debug("OK:Duplicate id for LocationGroup must be prevented by unique constraint.");
        }
    }

    /**
     * Check locationGroup relationships between parent and childs.
     */
    @Test
    public final void testAddLocationGroup() {
        LocationGroup parent = new LocationGroup("TEST_GROUP_1");
        LocationGroup child = new LocationGroup("TEST_GROUP_2");
        dao.save(parent);

        try {
            parent.addLocationGroup(null);
            Assert.fail("Not allowed to add null as LocationGroup on parent");
        } catch (IllegalArgumentException iae) {
            LOGGER.debug("OK: Exception when trying to add null as parent LocationGroup");
        }
        int noChildren = parent.getLocationGroups().size();
        parent.addLocationGroup(child);
        entityManager.flush();
        parent = dao.save(parent);
        Assert.assertTrue("The number of child LocationGroups must be increased by one", noChildren + 1 == parent
                .getLocationGroups().size());
        Assert.assertTrue("Parent LocationGroup is not the right one", child.getParent() == parent);
        Assert.assertFalse("Child LocationGroup must also be persisted", child.isNew());
    }

    /**
     * Test the removal of a locationGroup.
     */
    @Test
    public final void testRemoveLocationGroups() {
        LocationGroup parent = new LocationGroup("TEST_GROUP_1");
        LocationGroup child = new LocationGroup("TEST_GROUP_2");
        parent.addLocationGroup(child);
        dao.save(parent);
        entityManager.flush();
        try {
            parent.removeLocationGroup(null);
            Assert.fail("Not allowed to remove null as LocationGroup on parent");
        } catch (IllegalArgumentException iae) {
            LOGGER.debug("OK: Exception when trying to remove null as parent LocationGroup");
        }
        int noChildren = parent.getLocationGroups().size();
        Assert.assertEquals("The parent must be set on the child", child.getParent(), parent);
        parent.removeLocationGroup(child);
        Assert.assertNull("The parent has to be removed as LocationGroup from the child", child.getParent());
        Assert.assertTrue("The number of child LocationGroups must be decreased by one", noChildren - 1 == parent
                .getLocationGroups().size());
    }
}