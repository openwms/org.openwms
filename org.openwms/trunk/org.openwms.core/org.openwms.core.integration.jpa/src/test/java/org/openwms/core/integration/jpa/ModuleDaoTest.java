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
package org.openwms.core.integration.jpa;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.openwms.core.domain.Module;
import org.openwms.core.integration.ModuleDao;
import org.openwms.core.test.AbstractJpaSpringContextTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

/**
 * A ModuleDaoTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 */
@ContextConfiguration("classpath:Test-context.xml")
public class ModuleDaoTest extends AbstractJpaSpringContextTests {

    @Autowired
    @Qualifier("moduleDao")
    private ModuleDao dao;

    /**
     * Setup some test data.
     */
    @Before
    public void onBefore() {
        entityManager.persist(new Module("WMS", "org.openwms.wms.swf"));
        entityManager.persist(new Module("TMS", "org.openwms.tms.swf"));
        entityManager.flush();
        entityManager.clear();
    }

    /**
     * Testing some of the finders.
     */
    @Test
    public final void testFindAll() {
        assertTrue("2 persisted modules have to be found", dao.findAll().size() == 2);
        assertNotNull("Expect to find a persisted module by moduleName", dao.findByUniqueId("WMS"));
        assertTrue("Find module by query",
                dao.findByPositionalParameters(Module.NQ_FIND_BY_UNIQUE_QUERY, "TMS").size() == 1);
    }

}
