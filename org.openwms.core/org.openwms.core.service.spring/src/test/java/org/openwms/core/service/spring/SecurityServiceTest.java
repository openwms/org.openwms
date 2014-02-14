/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
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
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.core.service.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openwms.core.domain.system.usermanagement.Grant;
import org.openwms.core.domain.system.usermanagement.SecurityObject;
import org.openwms.core.integration.RoleDao;
import org.openwms.core.integration.SecurityObjectDao;
import org.openwms.core.test.AbstractMockitoTests;

/**
 * A SecurityServiceTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
public class SecurityServiceTest extends AbstractMockitoTests {

    @Mock
    private SecurityObjectDao dao;
    @Mock
    private RoleDao roleDao;
    @InjectMocks
    private SecurityServiceImpl srv;

    /**
     * Positive test findAll method.
     */
    @Test
    public final void testFindAll() {
        List<SecurityObject> result = new ArrayList<SecurityObject>();
        result.add(new Grant("GRANT1"));
        result.add(new Grant("GRANT2"));
        when(dao.findAll()).thenReturn(result);

        Assert.assertTrue(srv.findAll().size() == 2);
        verify(dao, times(1)).findAll();
    }

    /**
     * Positive test findAll method with empty result
     */
    @Test
    public final void testFindAllEmpty() {
        when(dao.findAll()).thenReturn(null);

        Assert.assertTrue(srv.findAll().isEmpty());
        verify(dao, times(1)).findAll();
    }

    /**
     * Test method for {@link org.openwms.core.service.spring.SecurityServiceImpl#login()}.
     */
    @Test
    public final void testLogin() {
        srv.login();
    }

    /**
     * Test method for {@link org.openwms.core.service.spring.SecurityServiceImpl#mergeGrants(java.lang.String, java.util.List)} .
     */
    @Test
    public final void testMergeGrantsWithNull() {
        try {
            srv.mergeGrants(null, null);
            fail("Must throw an illegalArgumentException");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK: IllegalArgumentException thrown when calling merge with null");
        }
    }

    /**
     * Test method for {@link org.openwms.core.service.spring.SecurityServiceImpl#mergeGrants(java.lang.String, java.util.List)} .
     * 
     * Add a new Grant.
     */
    @Test
    public final void testMergeGrantsNew() {
        // prepare data
        List<Grant> persistedGrants = new ArrayList<Grant>();
        Grant testGrant = new Grant("TMS_NEW");
        persistedGrants.add(new Grant("TMS_KEY1"));
        List<Grant> newGrants = new ArrayList<Grant>();
        newGrants.add(testGrant);

        // preparing mocks
        when(dao.findAllOfModule("TMS%")).thenReturn(persistedGrants);
        when(dao.merge(testGrant)).thenReturn(testGrant);
        // when(roleDao.removeFromRoles(persistedGrants));

        // do test call
        List<Grant> result = srv.mergeGrants("TMS", newGrants);

        // verify mock invocations
        // New Grant shall be added...
        verify(dao).merge(testGrant);
        // Verify the the new Grant will not be in the list of removed Grants...
        verify(dao, never()).delete(Arrays.asList(new Grant[] { testGrant }));
        // But the existing Grant has to be removed, because it is not in the
        // list of merging Grants...
        verify(dao).delete(Arrays.asList(new Grant[] { new Grant("TMS_KEY1") }));

        // check the results
        assertEquals(1, result.size());
        assertTrue(result.contains(testGrant));
    }

    /**
     * Test method for {@link org.openwms.core.service.spring.SecurityServiceImpl#mergeGrants(java.lang.String, java.util.List)} .
     * 
     * Merge existing Grants.
     */
    @Test
    public final void testMergeGrantsExisting() {
        // FIXME [scherrer] : Verify test!
        // prepare data
        List<Grant> persistedGrants = new ArrayList<Grant>();
        persistedGrants.add(new Grant("TMS_NEW"));

        List<Grant> newGrants = new ArrayList<Grant>();
        Grant testGrant = new Grant("TMS_NEW");
        newGrants.add(testGrant);

        // preparing mocks
        when(dao.findAllOfModule("TMS%")).thenReturn(persistedGrants);
        when(dao.merge(testGrant)).thenReturn(testGrant);

        // do test call
        List<Grant> result = srv.mergeGrants("TMS", newGrants);

        // verify mock invocations
        // New Grant shall be added...
        verify(dao, never()).merge(testGrant);
        // Verify the the new Grant will not be in the list of removed Grants...
        verify(dao, never()).delete(Arrays.asList(new Grant[] { testGrant }));

        // check the results
        assertEquals(1, result.size());
        assertTrue(result.contains(testGrant));
    }
}