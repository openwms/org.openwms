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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * A LocationTypeTest.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
public class LocationTypeTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public final
    @Test
    void testCreationWithNull() {
        thrown.expect(IllegalArgumentException.class);
        new LocationType(null);
    }

    public final
    @Test
    void testCreationWithEmpty() {
        thrown.expect(IllegalArgumentException.class);
        new LocationType("");
    }

    public final
    @Test
    void testDefaultValues() {
        LocationType lt = new LocationType("conveyor");
        assertThat(lt.getDescription()).isEqualTo(LocationType.DEF_TYPE_DESCRIPTION);
        assertThat(lt.getHeight()).isEqualTo(LocationType.DEF_HEIGHT);
        assertThat(lt.getLength()).isEqualTo(LocationType.DEF_LENGTH);
        assertThat(lt.getWidth()).isEqualTo(LocationType.DEF_WIDTH);
        assertThat(lt.getType()).isEqualTo("conveyor");
    }

    public final
    @Test
    void testEqualityLight() {
        LocationType conveyor = new LocationType("conveyor");
        LocationType conveyor2 = new LocationType("conveyor");
        LocationType workplace = new LocationType("workplace");

        assertThat(workplace).isNotEqualTo(conveyor);
        assertThat(conveyor).isEqualTo(conveyor);
        assertThat(conveyor).isEqualTo(conveyor2);
        assertThat(conveyor2).isEqualTo(conveyor);
    }

    public final
    @Test
    void testProperReturnOfToString() {
        LocationType conveyor = new LocationType("conveyor");
        assertThat(conveyor.toString()).isEqualTo("conveyor");
    }
}
