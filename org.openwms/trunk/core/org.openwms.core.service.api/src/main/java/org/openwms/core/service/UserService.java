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

import org.openwms.core.domain.system.usermanagement.Role;
import org.openwms.core.domain.system.usermanagement.User;
import org.openwms.core.domain.system.usermanagement.UserPassword;

/**
 * An UserService extends the {@link EntityService} interface about some useful
 * methods regarding the general handling of {@link User}s and {@link Role}s.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public interface UserService {

/**
     * Change the current password of the {@link User}.
     * 
     * @param userPassword
     *            The new {@link UserPassword) to change
     * @return <code>true</code> if the password could be changed successfully.
     *         <code>false</code> when the new password conflicts with the rules defined for passwords.
     */
    boolean changeUserPassword(UserPassword userPassword);

    /**
     * Find and return all {@link User}s.
     * 
     * @return A list of all {@link User}s
     */
    List<User> findAll();

    /**
     * Call this method to store an image for an {@link User}.
     * 
     * @param username
     *            Username of the {@link User}
     * @param image
     *            Image to be saved as byte[]
     */
    void uploadImageFile(String username, byte[] image);

    /**
     * Return an transient {@link User} entity object, serving as a template.
     * 
     * @param username
     *            Username of the {@link User}
     * @return An empty template {@link User} instance
     */
    User getTemplate(String username);

    /**
     * Update the given {@link User} or persist it when it is transient.
     * 
     * @param user
     *            {@link User} entity to persist
     * @return Updated {@link User} entity instance
     */
    User save(User user);

    /**
     * Remove an {@link User} from the persistent storage.
     * 
     * @param user
     *            {@link User} entity to be removed
     */
    void remove(User user);

}