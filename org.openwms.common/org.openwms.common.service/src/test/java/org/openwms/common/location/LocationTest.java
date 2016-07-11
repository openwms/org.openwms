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
package org.openwms.common.location;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * A LocationDaoTest.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public class LocationTest {

    public
    @Test
    void testDefaultCreation() {
        Location l = new Location(new LocationPK.Builder().area("area").aisle("aisle").x("x").y("y").z("z").build());
        assertThat(l.getNoMaxTransportUnits()).isEqualTo(Location.MAX_TU);
    }

    /**
     * Test adding messages to the location. Test cascading persistence.
     @Test public final void testAddingErrorMessages() {
     LocationPK pk = new LocationPK("OWN", "OWN", "OWN", "OWN", "OWN");
     Location location = new Location(pk);
     locationRepository.save(location);

     location.addMessage(new Message(1, "Errormessage 1"));
     location.addMessage(new Message(2, "Errormessage 2"));

     locationRepository.save(location);

     Assert.assertEquals("Expected 2 persisted Messages added to the location", 2, location.getMessages().size());
     Query query = entityManager.createQuery("select count(m) from Message m");
     Long cnt = (Long) query.getSingleResult();
     Assert.assertEquals("Expected 2 persisted Messages in MESSAGE table", 2, cnt.intValue());
     }
     */
}
