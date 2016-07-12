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
package org.openwms.common.transport;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openwms.common.location.LocationType;

/**
 * A TransportUnitTypeTest.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
public class TransportUnitTypeTest {

    @org.junit.Rule
    public ExpectedException thrown = ExpectedException.none();

    public final
    @Test
    void testCreationWithNull() {
        thrown.expect(IllegalArgumentException.class);
        TransportUnitType.create(null);
    }

    public final
    @Test
    void testCreationWithEmpty() {
        thrown.expect(IllegalArgumentException.class);
        TransportUnitType.create("");
    }

    public final
    @Test
    void testDefaultValues() {
        TransportUnitType tut = TransportUnitType.create("tut");
        assertThat(tut.getDescription()).isEqualTo(TransportUnitType.DEF_TYPE_DESCRIPTION);
        assertThat(tut.getLength()).isEqualTo(TransportUnitType.DEF_LENGTH);
        assertThat(tut.getHeight()).isEqualTo(TransportUnitType.DEF_HEIGHT);
        assertThat(tut.getWidth()).isEqualTo(TransportUnitType.DEF_WIDTH);
        assertThat(tut.getTransportUnits()).hasSize(0);
        assertThat(tut.getTypePlacingRules()).hasSize(0);
        assertThat(tut.getTypeStackingRules()).hasSize(0);
    }

    public final
    @Test
    void testPlacingRuleHandling() {
        LocationType locationType = new LocationType("conveyor");
        TransportUnitType tut = TransportUnitType.create("tut");
        TypePlacingRule typePlacingRule = new TypePlacingRule(tut, locationType, 1);

        tut.addTypePlacingRule(typePlacingRule);
        assertThat(tut.getTypePlacingRules()).hasSize(1).contains(typePlacingRule);

        tut.removeTypePlacingRule(typePlacingRule);
        assertThat(tut.getTypePlacingRules()).hasSize(0);
    }
}
