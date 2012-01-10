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
package org.openwms.core.service;

import java.util.List;

import org.openwms.core.domain.system.usermanagement.SystemUser;
import org.openwms.core.domain.system.usermanagement.User;
import org.openwms.core.domain.system.usermanagement.UserPassword;
import org.openwms.core.domain.system.usermanagement.UserPreference;

/**
 * An UserService offers functionality according to the handling with
 * {@link User}s.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.domain.system.usermanagement.User
 */
public interface UserService {

    /**
     * Change the current {@link User}s password.
     * 
     * @param userPassword
     *            The {@link UserPassword} to change
     */
    void changeUserPassword(UserPassword userPassword);

    /**
     * Find and return all {@link User}s.
     * 
     * @return The list of all {@link User}s
     */
    List<User> findAll();

    /**
     * Call this method to store an image for an {@link User}.
     * 
     * @param username
     *            Username of the {@link User}
     * @param image
     *            Image to be stored
     */
    void uploadImageFile(String username, byte[] image);

    /**
     * Return a transient {@link User} entity object, serving as a template.
     * 
     * @param username
     *            Username of the {@link User}
     * @return An empty {@link User} template
     */
    User getTemplate(String username);

    /**
     * Update the given {@link User} or persist it when it is a transient one.
     * 
     * @param user
     *            {@link User} entity to save
     * @return Saved {@link User} instance
     */
    User save(User user);

    /**
     * Save changes on an {@link User} and additionally save the User's password
     * and preferences.
     * 
     * @param user
     *            The {@link User} to change
     * @param userPassword
     *            The {@link User}s password
     * @param prefs
     *            An array of {@link UserPreference} objects
     * @return The saved {@link User} instance
     */
    User saveUserProfile(User user, UserPassword userPassword, UserPreference... prefs);

    /**
     * Remove an {@link User}.
     * 
     * @param user
     *            {@link User} to be removed
     */
    void remove(User user);

    /**
     * Create and return the {@link SystemUser}.
     * 
     * @return the {@link SystemUser} instance
     */
    SystemUser createSystemUser();
}