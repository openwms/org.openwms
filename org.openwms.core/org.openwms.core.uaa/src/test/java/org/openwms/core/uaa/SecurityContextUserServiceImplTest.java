/*
 * openwms.org, the Open Warehouse Management System.
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
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.core.uaa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import net.sf.ehcache.Ehcache;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openwms.core.event.UserChangedEvent;
import org.openwms.core.test.AbstractMockitoTests;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * A SecurityContextUserServiceImplTest.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
public class SecurityContextUserServiceImplTest extends AbstractMockitoTests {

    private static final String TEST_USER = "TEST_USER";
    @Mock
    private UserCache userCache;
    @Mock
    private Ehcache cache;
    @Mock
    private UserService userService;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private SaltSource saltSource;
    @InjectMocks
    private SecurityContextUserServiceImpl srv;

    /**
     * Test method for {@link org.openwms.core.uaa.SecurityContextUserServiceImpl#onApplicationEvent(org.openwms.core.event.UserChangedEvent)}
     * .
     */
    @Test
    public final void testOnApplicationEventWithCache() {
        srv.onApplicationEvent(new UserChangedEvent(this));
        verify(cache).removeAll();
    }

    /**
     * Test method for {@link org.openwms.core.uaa.SecurityContextUserServiceImpl#loadUserByUsername(java.lang.String)} .
     * <p>
     * Test that the cache works and no service nor dao is called.
     */
    @Test
    public final void testLoadUserByUsernameFromCache() {
        when(userCache.getUserFromCache(TEST_USER)).thenReturn(new UserWrapper(new User(TEST_USER)));
        UserDetails cachedUser = srv.loadUserByUsername(TEST_USER);

        assertTrue(cachedUser instanceof UserWrapper);
        assertEquals(((UserWrapper) cachedUser).getUser(), new User(TEST_USER));
        verify(userCache, never()).putUserInCache(new UserWrapper(new User(TEST_USER)));
        verify(userService, never()).createSystemUser();
    }

    /**
     * Test method for {@link org.openwms.core.uaa.SecurityContextUserServiceImpl#loadUserByUsername(java.lang.String)} .
     * <p>
     * Test for the SystemUser credentials, that user can be cached but not fetched from the dao. In this test the cache is not tested. We
     * expect that the cache is empty.
     */
    @Test
    public final void testLoadUserByUsernameSystemUser() {
        SystemUser su = new SystemUser(SystemUser.SYSTEM_USERNAME, SystemUser.SYSTEM_USERNAME);
        SystemUserWrapper suw = new SystemUserWrapper(su);

        when(userCache.getUserFromCache(SystemUser.SYSTEM_USERNAME)).thenReturn(null);
        when(userService.createSystemUser()).thenReturn(su);
        when(saltSource.getSalt(suw)).thenReturn(SystemUser.SYSTEM_USERNAME);
        when(encoder.encode(SystemUser.SYSTEM_USERNAME)).thenReturn(SystemUser.SYSTEM_USERNAME);
        UserDetails cachedUser = srv.loadUserByUsername(SystemUser.SYSTEM_USERNAME);

        verify(userService).createSystemUser();

        assertTrue(cachedUser instanceof UserWrapper);
        verify(userCache).putUserInCache(((UserWrapper) cachedUser));
        assertTrue(((UserWrapper) cachedUser).getUser() instanceof SystemUser);
    }

    /**
     * Test method for {@link org.openwms.core.uaa.SecurityContextUserServiceImpl#loadUserByUsername(java.lang.String)} .
     * <p>
     * Test for a usual User not a SystemUser, that User is can be resolved from the dao and is put in cache afterwards.
     */
    @Test
    public final void testLoadUserByUsernameNotCached() {
        when(userCache.getUserFromCache("NOT_CACHED_USER")).thenReturn(null);
        when(userService.findByUsername("NOT_CACHED_USER")).thenReturn(Optional.of(new User("NOT_CACHED_USER")));

        UserDetails cachedUser;
        cachedUser = srv.loadUserByUsername("NOT_CACHED_USER");

        verify(userService, never()).createSystemUser();
        verify(userService).findByUsername("NOT_CACHED_USER");

        assertTrue(cachedUser instanceof UserWrapper);
        verify(userCache).putUserInCache(((UserWrapper) cachedUser));
        assertFalse(((UserWrapper) cachedUser).getUser() instanceof SystemUser);
    }

    /**
     * Test method for {@link org.openwms.core.uaa.SecurityContextUserServiceImpl#loadUserByUsername(java.lang.String)} .
     * <p>
     * Test that calling the service to load an unknown User fails with an exception and nothing is put into cache.
     */
    @Test
    public final void testLoadUserByUsernameNotFound() {
        when(userCache.getUserFromCache("UNKNOWN_USER")).thenReturn(null);
        when(userService.findByUsername("UNKNOWN_USER")).thenReturn(null);

        UserDetails cachedUser = null;
        try {
            cachedUser = srv.loadUserByUsername("UNKNOWN_USER");
            fail("Must throw an UserNotFoundException when the user does not exist");
        } catch (UsernameNotFoundException unfe) {
            logger.debug("OK: Exception when no user found is ok.");
        }

        verify(userService, never()).createSystemUser();
        verify(userService).findByUsername("UNKNOWN_USER");
        verify(userCache, never()).putUserInCache(((UserWrapper) cachedUser));
    }
}