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

import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openwms.common.StateChangeException;

/**
 * A LocationGroupTest.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
public class LocationGroupTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public
    @Test
    void testConstructionWithNull() {
        thrown.expect(IllegalArgumentException.class);
        new LocationGroup(null);
    }

    public
    @Test
    void testConstructionWithEmpty() {
        thrown.expect(IllegalArgumentException.class);
        new LocationGroup("");
    }

    public
    @Test
    void testDefaultValues() {
        LocationGroup lg = new LocationGroup("Error zone");
        assertThat(lg.getName()).isEqualTo("Error zone");
        assertThat(lg.isLocationGroupCountingActive()).isTrue();
        assertThat(lg.getNoLocations()).isEqualTo(0);
        assertThat(lg.getGroupStateIn()).isEqualTo(LocationGroupState.AVAILABLE);
        assertThat(lg.isInfeedAllowed()).isTrue();
        assertThat(lg.isInfeedBlocked()).isFalse();
        assertThat(lg.getGroupStateOut()).isEqualTo(LocationGroupState.AVAILABLE);
        assertThat(lg.isOutfeedAllowed()).isTrue();
        assertThat(lg.isOutfeedBlocked()).isFalse();
        assertThat(lg.getMaxFillLevel()).isEqualTo(0);
        assertThat(lg.getLocationGroups()).hasSize(0);
        assertThat(lg.getLocations()).hasSize(0);
    }

    public
    @Test
    void testAddLocationGroupWithNull() {
        LocationGroup parent = new LocationGroup("Warehouse");
        thrown.expect(IllegalArgumentException.class);
        parent.addLocationGroup(null);
    }

    public
    @Test
    void testAddLocationGroupToParent() {
        LocationGroup parent = new LocationGroup("Warehouse");
        LocationGroup lg = new LocationGroup("Error zone");
        parent.addLocationGroup(lg);
        assertThat(parent.getLocationGroups()).hasSize(1);
        assertThat(lg.getParent()).isEqualTo(parent);
        assertThat(lg.getGroupStateIn()).isEqualTo(parent.getGroupStateIn());
        assertThat(lg.getGroupStateOut()).isEqualTo(parent.getGroupStateOut());
    }

    public
    @Test
    void testAddLocationGroupToChild() {
        LocationGroup parent = new LocationGroup("Warehouse");
        LocationGroup lg = new LocationGroup("Error zone");
        lg.setGroupStateIn(LocationGroupState.NOT_AVAILABLE);
        LocationGroup child = new LocationGroup("Picking");

        // test ...
        parent.addLocationGroup(child);
        assertThat(child.getParent()).isEqualTo(parent);
        assertThat(child.getGroupStateIn()).isEqualTo(LocationGroupState.AVAILABLE);

        lg.addLocationGroup(child);
        assertThat(child.getParent()).isEqualTo(lg);
        assertThat(child.getGroupStateIn()).isEqualTo(LocationGroupState.NOT_AVAILABLE);
    }

    public
    @Test
    void testAddLocationGroupWithStateChange() {
        LocationGroup parent = new LocationGroup("Warehouse");
        parent.setGroupStateIn(LocationGroupState.NOT_AVAILABLE);
        parent.setGroupStateOut(LocationGroupState.NOT_AVAILABLE, parent);
        LocationGroup lg = new LocationGroup("Error zone");
        parent.addLocationGroup(lg);
        assertThat(parent.getLocationGroups()).hasSize(1);
        assertThat(lg.getParent()).isEqualTo(parent);
        assertThat(lg.getGroupStateIn()).isEqualTo(parent.getGroupStateIn()).isEqualTo(LocationGroupState.NOT_AVAILABLE);
        assertThat(lg.getGroupStateOut()).isEqualTo(parent.getGroupStateOut()).isEqualTo(LocationGroupState.NOT_AVAILABLE);
    }

    public
    @Test
    void testStateChangeAllowed() {
        LocationGroup parent = new LocationGroup("Warehouse");
        LocationGroup lg = new LocationGroup("Error zone");
        parent.addLocationGroup(lg);

        parent.setGroupStateIn(LocationGroupState.NOT_AVAILABLE);
        assertThat(lg.getGroupStateIn()).isEqualTo(parent.getGroupStateIn()).isEqualTo(LocationGroupState.NOT_AVAILABLE);
        parent.setGroupStateOut(LocationGroupState.NOT_AVAILABLE, parent);
        assertThat(lg.getGroupStateOut()).isEqualTo(parent.getGroupStateOut()).isEqualTo(LocationGroupState.NOT_AVAILABLE);

        parent.setGroupStateIn(LocationGroupState.AVAILABLE);
        assertThat(lg.getGroupStateIn()).isEqualTo(parent.getGroupStateIn()).isEqualTo(LocationGroupState.AVAILABLE);
        parent.setGroupStateOut(LocationGroupState.AVAILABLE, parent);
        assertThat(lg.getGroupStateOut()).isEqualTo(parent.getGroupStateOut()).isEqualTo(LocationGroupState.AVAILABLE);
    }

    public
    @Test
    void testStateChangeNotAllowed() {
        LocationGroup parent = new LocationGroup("Warehouse");
        LocationGroup lg = new LocationGroup("Error zone");
        parent.addLocationGroup(lg);
        parent.setGroupStateIn(LocationGroupState.NOT_AVAILABLE);
        parent.setGroupStateOut(LocationGroupState.NOT_AVAILABLE, parent);

        thrown.expect(StateChangeException.class);
        lg.setGroupStateIn(LocationGroupState.AVAILABLE);
    }

    public
    @Test
    void testAddLocationWithNull() {
        LocationGroup lg = new LocationGroup("Error zone");
        thrown.expect(IllegalArgumentException.class);
        lg.addLocation(null);
    }

    public
    @Test
    void testAddLocation() {
        LocationGroup lg = new LocationGroup("Error zone");
        Location loc = Location.create(new LocationPK("area", "aisle", "x", "y", "z"));
        assertThat(loc.belongsNotToLocationGroup()).isTrue();
        lg.addLocation(loc);
        assertThat(loc.belongsToLocationGroup()).isTrue();
        assertThat(lg.getLocations()).hasSize(1);
        assertThat(lg.hasLocations()).isTrue();
    }

    public
    @Test
    void testRemoveLocationWithNull() {
        LocationGroup lg = new LocationGroup("Error zone");
        thrown.expect(IllegalArgumentException.class);
        lg.removeLocation(null);
    }

    public
    @Test
    void testRemoveLocation() {
        LocationGroup lg = new LocationGroup("Error zone");
        Location loc = Location.create(new LocationPK("area", "aisle", "x", "y", "z"));
        lg.addLocation(loc);
        assertThat(loc.belongsToLocationGroup()).isTrue();
        assertThat(lg.getLocations()).hasSize(1);
        assertThat(lg.hasLocations()).isTrue();

        // test...
        lg.removeLocation(loc);
        assertThat(loc.belongsNotToLocationGroup()).isTrue();
        assertThat(lg.getLocations()).hasSize(0);
        assertThat(lg.hasLocations()).isFalse();
    }

    public
    @Test
    void testEqualityLight() {
        LocationGroup lg1 = new LocationGroup("Error zone");
        LocationGroup lg2 = new LocationGroup("Error zone");
        LocationGroup lg3 = new LocationGroup("Picking");
        assertThat(lg1).isEqualTo(lg2);
        assertThat(lg2).isEqualTo(lg1);
        assertThat(lg1).isNotEqualTo(lg3);
    }

    public
    @Test
    void testHash() {
        Set<LocationGroup> groups = new HashSet();
        groups.add(new LocationGroup("Error zone"));
        assertThat(groups).hasSize(1);
        groups.add(new LocationGroup("Error zone"));
        assertThat(groups).hasSize(1);
        groups.add(new LocationGroup("Picking"));
        assertThat(groups).hasSize(2);
    }
}
