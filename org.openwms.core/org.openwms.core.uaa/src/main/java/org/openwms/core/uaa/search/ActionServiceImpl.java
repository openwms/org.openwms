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
package org.openwms.core.uaa.search;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.openwms.core.domain.preferences.ConfigurationService;
import org.openwms.core.system.PreferenceKey;
import org.openwms.core.system.PropertyScope;
import org.openwms.core.system.usermanagement.User;
import org.openwms.core.system.usermanagement.UserPreference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * An ActionServiceImpl is used by the GUI to find all defined search actions.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
@Transactional
@Service(ActionServiceImpl.COMPONENT_NAME)
public class ActionServiceImpl implements ActionService {

    /** Springs service name. */
    public static final String COMPONENT_NAME = "actionService";

    @Autowired
    private ConfigurationService confSrv;
    private Collection<Action> actions = new HashSet<Action>();
    private Collection<Tag> tags = new HashSet<Tag>();

    /**
     * Create a new ActionServiceImpl.
     */
    public ActionServiceImpl() {}

    /**
     * {@inheritDoc}
     * 
     * Returns a HashSet with all Actions.
     */
    @Override
    public Collection<Action> findAllActions() {
        return actions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public Collection<Action> findAllActions(User user) {
        Collection<UserPreference> userPrefs = confSrv.findByType(UserPreference.class, user.getUsername());
        PreferenceKey pKey = new PreferenceKey(PropertyScope.USER, user.getUsername(), "lastSearchActions");
        for (UserPreference pref : userPrefs) {
            if (pref.getPrefKey().equals(pKey)) {
                return (Collection<Action>) pref.getBinValue();
            }
        }
        return new HashSet<Action>(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Tag> findAllTags(User user) {
        return tags;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<Action> save(User user, Collection<Action> actions) {
        Collection<UserPreference> userPrefs = confSrv.findByType(UserPreference.class, user.getUsername());
        PreferenceKey pKey = new PreferenceKey(PropertyScope.USER, user.getUsername(), "lastSearchActions");
        UserPreference result = null;
        for (UserPreference pref : userPrefs) {
            if (pref.getPrefKey().equals(pKey)) {
                result = pref;
                break;
            }
        }
        if (result == null) {
            result = new UserPreference(user.getUsername(), "lastSearchActions");
            result.setDescription("All UI actions the User has previously chosen");
            Set<Action> all = new HashSet<Action>();
            all.addAll(actions);
            result.setBinValue((Serializable) all);
        } else {
            result.setBinValue((Serializable) actions);
        }
        result = confSrv.save(result);
        return (Collection<Action>) result.getBinValue();
    }
}