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
 * A MessageTest.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.1
 * @since 0.1
 */
public class MessageTest {

    public
    @Test
    final void testConstruction() {
        Message m = new Message(4711, "Test message");
        assertThat(m.getMessageText()).isEqualTo("Test message");
        assertThat(m.getMessageNo()).isEqualTo(4711);
        assertThat(m).isEqualTo((new Message(4711, "Test message")));
    }

    public
    @Test
    final void testConstructionWithBuilder() {
        Message m = Message.newBuilder().messageNo(4711).messageText("Test message").build();
        assertThat(m.getMessageText()).isEqualTo("Test message");
        assertThat(m.getMessageNo()).isEqualTo(4711);
        assertThat(m).isEqualTo((new Message(4711, "Test message")));
    }

    public
    @Test
    final void testEqualityLight() {
        Message m1 = Message.newBuilder().messageNo(4711).messageText("Test message").build();
        Message m2 = Message.newBuilder().messageNo(4711).messageText("Test message").build();
        Message m3 = Message.newBuilder().messageNo(9999).messageText("Test message").build();
        Message m4 = Message.newBuilder().messageNo(4711).messageText("Error message").build();

        assertThat(m1).isEqualTo(m2);
        assertThat(m2).isEqualTo(m1);

        assertThat(m1).isNotEqualTo(m3);
        assertThat(m1).isNotEqualTo(m4);

        assertThat(m3).isNotEqualTo(m4);
        assertThat(m4).isNotEqualTo(m3);
    }

    public
    @Test
    final void testProperOutcomeOfToString() {
        Message m1 = Message.newBuilder().messageNo(4711).messageText("Test message").build();
        assertThat(m1.toString()).isEqualTo("4711" + Message.SEPARATOR + "Test message");
    }
}
