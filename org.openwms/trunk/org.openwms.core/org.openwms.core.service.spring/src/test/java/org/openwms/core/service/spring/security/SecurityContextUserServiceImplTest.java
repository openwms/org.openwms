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
package org.openwms.core.service.spring.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openwms.core.domain.system.usermanagement.SystemUser;
import org.openwms.core.domain.system.usermanagement.User;
import org.openwms.core.integration.GenericDao;
import org.openwms.core.service.UserService;
import org.openwms.core.service.spring.UserWrapper;
import org.openwms.core.test.AbstractMockitoTests;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * A SecurityContextUserServiceImplTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
public class SecurityContextUserServiceImplTest extends AbstractMockitoTests {

    @Mock
    private UserCache cache;
    @Mock
    private GenericDao<User, Long> dao;
    @Mock
    private UserService userService;
    @InjectMocks
    private SecurityContextUserServiceImpl srv;

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.security.SecurityContextUserServiceImpl#onApplicationEvent(org.openwms.core.util.event.UserChangedEvent)}
     * .
     */
    @Test
    public final void testOnApplicationEvent() {}

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.security.SecurityContextUserServiceImpl#loadUserByUsername(java.lang.String)}
     * .
     * 
     * Test that the cache works and no service nor dao is called.
     */
    @Test
    public final void testLoadUserByUsernameFromCache() {
        when(cache.getUserFromCache("TEST_USER")).thenReturn(new UserWrapper(new User("TEST_USER")));
        UserDetails cachedUser = srv.loadUserByUsername("TEST_USER");

        assertTrue(cachedUser instanceof UserWrapper);
        assertEquals(((UserWrapper) cachedUser).getUser(), new User("TEST_USER"));
        verify(cache, never()).putUserInCache(new UserWrapper(new User("TEST_USER")));
        verify(userService, never()).createSystemUser();
        verify(dao, never()).findByUniqueId("TEST_USER");
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.security.SecurityContextUserServiceImpl#loadUserByUsername(java.lang.String)}
     * .
     * 
     * Test for the SystemUser credentials, that user can be cached but not
     * fetched from the dao. In this test the cache is not tested. We expect
     * that the cache is empty.
     */
    @Test
    public final void testLoadUserByUsernameSystemUser() {
        when(cache.getUserFromCache(SystemUser.SYSTEM_USERNAME)).thenReturn(null);
        when(userService.createSystemUser()).thenReturn(
                new SystemUser(SystemUser.SYSTEM_USERNAME, SystemUser.SYSTEM_USERNAME));
        UserDetails cachedUser = srv.loadUserByUsername(SystemUser.SYSTEM_USERNAME);

        verify(userService).createSystemUser();
        verify(dao, never()).findByUniqueId(SystemUser.SYSTEM_USERNAME);

        assertTrue(cachedUser instanceof UserWrapper);
        verify(cache).putUserInCache(((UserWrapper) cachedUser));
        assertTrue(((UserWrapper) cachedUser).getUser() instanceof SystemUser);
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.security.SecurityContextUserServiceImpl#loadUserByUsername(java.lang.String)}
     * .
     * 
     * Test for a usual User not a SystemUser, that User is can be resolved from
     * the dao and is put in cache afterwards.
     */
    @Test
    public final void testLoadUserByUsernameNotCached() {
        when(cache.getUserFromCache("NOT_CACHED_USER")).thenReturn(null);
        when(dao.findByUniqueId("NOT_CACHED_USER")).thenReturn(new User("NOT_CACHED_USER"));

        UserDetails cachedUser = srv.loadUserByUsername("NOT_CACHED_USER");

        verify(userService, never()).createSystemUser();
        verify(dao).findByUniqueId("NOT_CACHED_USER");

        assertTrue(cachedUser instanceof UserWrapper);
        verify(cache).putUserInCache(((UserWrapper) cachedUser));
        assertFalse(((UserWrapper) cachedUser).getUser() instanceof SystemUser);
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.security.SecurityContextUserServiceImpl#loadUserByUsername(java.lang.String)}
     * .
     * 
     * Test that calling the service to load an unknown User fails with an
     * exception and nothing is put into cache.
     */
    @Test
    public final void testLoadUserByUsernameNotFound() {
        when(cache.getUserFromCache("UNKNOWN_USER")).thenReturn(null);
        when(dao.findByUniqueId("UNKNOWN_USER")).thenReturn(null);

        UserDetails cachedUser = null;
        try {
            cachedUser = srv.loadUserByUsername("UNKNOWN_USER");
            fail("Must throw an UserNotFoundException when the user does not exist");
        } catch (UsernameNotFoundException unfe) {
            logger.debug("OK: Exception when no user found is ok.");
        }

        verify(userService, never()).createSystemUser();
        verify(dao).findByUniqueId("UNKNOWN_USER");
        verify(cache, never()).putUserInCache(((UserWrapper) cachedUser));
    }
}