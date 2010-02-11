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
package org.openwms.common.service.management;

import java.io.Serializable;

import org.openwms.common.domain.system.usermanagement.User;
import org.openwms.common.service.EntityService;

/**
 * An UserService.
 * <p>
 * Extends the {@link EntityService} interface about some useful methods
 * regarding the general handling with {@link User}s.
 * </p>
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 877 $
 * @since 0.1
 * @see EntityService
 */
public interface UserService<T extends Serializable> extends EntityService<T> {

    /**
     * Call this method to store an image of an {@link User}.
     * 
     * @param username
     *            - Username of the User
     * @param image
     *            - Image as byte[]
     * @return - true, if operation successful<br>
     *         - false, if not
     */
    boolean uploadImageFile(String username, byte[] image);

    /**
     * Return an transient {@link User} Entity class, serving as a template.
     * 
     * @param username
     *            - Username of the {@link User}
     * @return - An empty template {@link User} instance
     */
    T getTemplate(String username);

    /**
     * Save the given {@link User} Entity or persist it when it is transient.
     * 
     * @param user
     *            - {@link User} Entity to persist
     * @return - Saved {@link User} Entity instance
     */
    T save(T user);

    /**
     * Remove {@link User} Entity from the persistence storage.
     * 
     * @param user
     *            - {@link User} Entity to be removed
     */
    void remove(T user);

}