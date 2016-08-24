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
package org.openwms.core.search;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openwms.core.configuration.ConfigurationService;
import org.openwms.core.configuration.UserPreference;
import org.openwms.core.test.AbstractMockitoTests;
import org.openwms.core.uaa.SystemUser;
import org.openwms.core.uaa.User;

/**
 * A ActionServiceTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
public class ActionServiceTest extends AbstractMockitoTests {

    @Mock
    private ConfigurationService confSrv;
    @InjectMocks
    private ActionServiceImpl srv = new ActionServiceImpl();

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
        Collection<Action> result = srv.findAllActions();
        Assert.assertNotNull(result);
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.search.ActionServiceImpl#findAllActions(org.openwms.core.system.usermanagement.User)}
     * .
     */
    @Test
    public final void testFindAllActionsUser() {
        Collection<Action> result = srv.findAllActions(new SystemUser(SystemUser.SYSTEM_USERNAME,
                SystemUser.SYSTEM_USERNAME));
        Assert.assertNotNull(result);
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.search.ActionServiceImpl#findAllTags(org.openwms.core.system.usermanagement.User)}
     * .
     */
    @Test
    public final void testFindAllTags() {
        Collection<Tag> result = srv
                .findAllTags(new SystemUser(SystemUser.SYSTEM_USERNAME, SystemUser.SYSTEM_USERNAME));
        Assert.assertNotNull(result);
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.search.ActionServiceImpl#save(org.openwms.core.system.usermanagement.User, java.util.Collection)}
     * .
     */
    @Ignore
    @Test
    public final void testSave() {
        Collection<Action> actions = new ArrayList<Action>();
        actions.add(new Action());

        UserPreference uPref = new UserPreference(SystemUser.SYSTEM_USERNAME, "lastSearchActions", "");
        Collection<UserPreference> userPrefs = new ArrayList<UserPreference>();

        User user = new SystemUser(SystemUser.SYSTEM_USERNAME, SystemUser.SYSTEM_USERNAME);
        userPrefs.add(uPref);
        //when(confSrv.findByType(UserPreference.class, user.getUsername())).thenReturn(userPrefs);
        when(confSrv.save(uPref)).thenReturn(uPref);

        Collection<Action> result = srv.save(user, actions);
        Assert.assertNotNull(result);
    }
}