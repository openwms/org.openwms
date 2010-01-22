/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service.management;

import java.io.Serializable;

import org.openwms.common.service.EntityService;

/**
 * A UserService.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 877 $
 */
public interface UserService<T extends Serializable> extends EntityService<T> {

    /**
     * Call this method to store an image of an user.
     * 
     * @param username
     *            - Username of the User
     * @param image
     *            - Image as byte[]
     * @return - true, if operation successful - false, if not successful
     */
    boolean uploadImageFile(String username, byte[] image);

    /**
     * 
     * Return an transient User entity class, serving as a template
     * 
     * @param username
     *            - Username of the User
     * @return - An empty template object of an User entity
     */
    T getTemplate(String username);

    /**
     * 
     * Save the given User entity class or persist it when it is transient.
     * 
     * @param user
     *            - User entity instance
     * @return - Saved User entity instance
     */
    T save(T user);

    /**
     * 
     * Remove the User entity from the persistence.
     * 
     * @param user
     *            - User entity instance
     */
    void remove(T user);

}
