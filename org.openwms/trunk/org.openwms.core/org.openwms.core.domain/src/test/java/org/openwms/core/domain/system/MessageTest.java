/*
 * openwms.org, the Open Warehouse Management System.
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.core.domain.system;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;

import org.junit.Test;
import org.openwms.core.test.AbstractJpaSpringContextTests;

/**
 * A MessageTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 */
public class MessageTest extends AbstractJpaSpringContextTests {

    @Test
    public final void testConstruction() {
        Message m = new Message(4711, "Test message");
        assertNotNull("Creation date must be set", m.getCreated());
        assertEquals("Expected the message", "Test message", m.getMessageText());
        assertEquals("Expected a message number", 4711, m.getMessageNo());
        assertNotSame("Same name and url", m.equals(new Message(4711, "Test message")));
    }

}
