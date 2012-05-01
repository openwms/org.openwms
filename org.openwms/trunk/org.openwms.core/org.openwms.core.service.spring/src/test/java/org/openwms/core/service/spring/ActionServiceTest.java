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
package org.openwms.core.service.spring;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.openwms.core.service.spring.search.ActionServiceImpl;

/**
 * A ActionServiceTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
public class ActionServiceTest {

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.search.ActionServiceImpl#ActionServiceImpl()}
     * .
     */
    @Test
    public final void testActionServiceImpl() {
        new ActionServiceImpl();
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.search.ActionServiceImpl#findAllActions()}
     * .
     */
    @Test
    public final void testFindAllActions() {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.search.ActionServiceImpl#findAllActions(org.openwms.core.domain.system.usermanagement.User)}
     * .
     */
    @Test
    public final void testFindAllActionsUser() {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.search.ActionServiceImpl#findAllTags(org.openwms.core.domain.system.usermanagement.User)}
     * .
     */
    @Test
    public final void testFindAllTags() {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.search.ActionServiceImpl#save(org.openwms.core.domain.system.usermanagement.User, java.util.Collection)}
     * .
     */
    @Test
    public final void testSave() {
        fail("Not yet implemented");
    }

}
