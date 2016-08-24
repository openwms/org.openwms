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
import org.openwms.common.units.Weight;

/**
 * A TransportUnitTest.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 0.1
 */
public class TransportUnitTest {

    @org.junit.Rule
    public ExpectedException thrown = ExpectedException.none();

    public final
    @Test
    void testCreationWithEmptyString() {
        thrown.expect(IllegalArgumentException.class);
        ObjectFactory.createTransportUnit("");
    }

    public final
    @Test
    void testDefaultValues() {
        TransportUnit tu = ObjectFactory.createTransportUnit("4711");
        assertThat(tu.getBarcode()).isEqualTo(ObjectFactory.createBarcode("4711"));
        assertThat(tu.getWeight()).isEqualTo(new Weight("0"));
        assertThat(tu.getState()).isEqualTo(TransportUnitState.AVAILABLE);
        assertThat(tu.getErrors()).hasSize(0);
        assertThat(tu.getChildren()).hasSize(0);
    }

    public final
    @Test
    void
    testAddErrorWithNull() {
        TransportUnit tu = ObjectFactory.createTransportUnit("4711");
        thrown.expect(IllegalArgumentException.class);
        tu.addError(null);
    }

    public final
    @Test
    void
    testUnitErrorHandling() {
        TransportUnit tu = ObjectFactory.createTransportUnit("4711");
        assertThat(tu.getErrors()).hasSize(0);
        tu.addError(UnitError.newBuilder().errorNo("4711").errorText("Damaged").build());
        assertThat(tu.getErrors()).hasSize(1).containsValues(UnitError.newBuilder().errorNo("4711").errorText("Damaged").build());
    }

    public final
    @Test
    void
    testAddChildWithNull() {
        TransportUnit tu = ObjectFactory.createTransportUnit("4711");
        thrown.expect(IllegalArgumentException.class);
        tu.addChild(null);
    }

    public final
    @Test
    void
    testRemoveChildWithNull() {
        TransportUnit tu = ObjectFactory.createTransportUnit("4711");
        thrown.expect(IllegalArgumentException.class);
        tu.removeChild(null);
    }

    public final
    @Test
    void testChildrenHandling() {
        TransportUnit tu1 = ObjectFactory.createTransportUnit("4711");
        TransportUnit tu2 = ObjectFactory.createTransportUnit("4712");
        TransportUnit tu3 = ObjectFactory.createTransportUnit("4713");

        tu1.addChild(tu2);
        assertThat(tu1.getChildren()).hasSize(1).contains(tu2);
        assertThat(tu2.getParent()).isEqualTo(tu1);

        tu1.addChild(tu3);
        assertThat(tu1.getChildren()).hasSize(2).contains(tu3);

        tu2.addChild(tu3);
        assertThat(tu1.getChildren()).hasSize(1);
        assertThat(tu2.getChildren()).hasSize(1).contains(tu3);
        assertThat(tu3.getParent()).isEqualTo(tu2);

        tu2.removeChild(tu3);
        assertThat(tu2.getChildren()).hasSize(0);
        assertThat(tu3.getParent()).isNull();

        thrown.expect(IllegalArgumentException.class);
        tu1.removeChild(tu3);
    }

    public final
    @Test
    void testEqualityLight() {
        TransportUnit tu1 = ObjectFactory.createTransportUnit("4711");
        TransportUnit tu2 = ObjectFactory.createTransportUnit("4711");
        TransportUnit tu3 = ObjectFactory.createTransportUnit("4712");

        assertThat(tu1).isEqualTo(tu2);
        assertThat(tu2).isEqualTo(tu1);

        assertThat(tu1).isNotEqualTo(tu3);
    }
}