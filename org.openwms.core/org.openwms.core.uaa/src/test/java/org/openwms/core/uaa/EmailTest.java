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
package org.openwms.core.uaa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * An EmailTest.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.1
 * @since 0.1
 */
public class EmailTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Test construction of Email entities.
     */
    public @Test final void testCreationNegative() {
        thrown.expect(IllegalArgumentException.class);
        new Email(null, null);
    }

    /**
     * Test construction of Email entities.
     */
    public @Test final void testCreationNegative2() {
        thrown.expect(IllegalArgumentException.class);
        new Email("Test", null);
    }

    /**
     * Test construction of Email entities.
     */
    public @Test final void testCreationNegative3() {
        thrown.expect(IllegalArgumentException.class);
        new Email(null, "force something, still no mail check");
    }

    /**
     * Test equality at least a few combinations.
     */
    public @Test final void testEquality() {
        Email e1 = new Email("user", "user@home.gov");
        e1.setFullname("user test");
        Email e2 = new Email("user", "user@home.gov");
        e2.setFullname("user test");
        Email d1 = new Email("user2", "user@home.gov");
        d1.setFullname("user different");
        Email d2 = new Email("user", "user@home.at");
        d2.setFullname("user different");
        assertThat(e1).isEqualTo(e2);
        assertThat(e2).isEqualTo(e1);
        assertThat(e1).isNotEqualTo(d1);
        assertThat(d1).isNotEqualTo(e1);
        assertThat(e1).isNotEqualTo(d2);
        assertThat(d2).isNotEqualTo(e1);
    }

    /**
     * Test put and retrieve from a hash collection.
     */
    public @Test final void testHashcode() {
        Email e1 = new Email("user", "user@home.gov");
        Email e2 = new Email("user", "user@home.gov");
        Email d1 = new Email("user2", "user@home.gov");
        Email d2 = new Email("user", "user@home.at");
        Set<Email> all = new HashSet<>();

        all.add(e1);
        assertThat(all).hasSize(1);
        assertThat(all.contains(e1)).isTrue();
        all.add(e2);
        assertThat(all).hasSize(1);
        assertThat(all.contains(e1)).isTrue();
        assertThat(all.contains(e2)).isTrue();
        Email ret = all.iterator().next();
        assertThat(ret == e1).isTrue();
        assertThat(ret == e2).isFalse();
        all.add(d1);
        assertThat(all).hasSize(2);
        assertThat(all.contains(d1)).isTrue();
        all.add(d2);
        assertThat(all).hasSize(3);
        assertThat(all.contains(d2)).isTrue();
    }
}