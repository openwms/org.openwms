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

import javax.validation.constraints.NotNull;
import java.util.Optional;

import org.ameba.integration.FindOperations;
import org.ameba.integration.SaveOperations;
import org.openwms.core.configuration.UserPreference;

/**
 * An UserService offers functionality according to the handling with {@link User}s.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.2
 * @see org.openwms.core.uaa.User
 * @since 0.1
 */
public interface UserService extends FindOperations<User, Long>, SaveOperations<User, Long> {

    /**
     * Change the current {@link User}s password.
     *
     * @param userPassword The {@link UserPassword} to change
     */
    void changeUserPassword(@NotNull UserPassword userPassword);

    /**
     * Attach and save an {@code image} to an {@link User} with {@code id}.
     *
     * @param id Id of the {@link User}
     * @param image Image to be stored
     */
    void uploadImageFile(Long id, byte[] image);

    /**
     * Return a transient {@link User} entity object, serving as a template.
     *
     * @param username Username of the {@link User}
     * @return An empty {@link User} template
     */
    User getTemplate(String username);

    /**
     * Save changes on an {@link User} and additionally save the User's password and preferences.
     *
     * @param user The {@link User} to change
     * @param userPassword The {@link User}s password
     * @param prefs An array of {@link UserPreference} objects
     * @return The saved {@link User} instance
     */
    User saveUserProfile(User user, UserPassword userPassword, UserPreference... prefs);

    /**
     * Create and return the {@link SystemUser} without persisting this user.
     *
     * @return the {@link SystemUser} instance
     */
    SystemUser createSystemUser();

    User create(User user);

    /**
     *
     * @param username
     * @return
     */
    Optional<User> findByUsername(String username);

    void remove(String username);
}