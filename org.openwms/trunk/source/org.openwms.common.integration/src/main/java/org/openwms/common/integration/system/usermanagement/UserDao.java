/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.integration.system.usermanagement;

import org.openwms.common.domain.system.usermanagement.User;
import org.openwms.common.integration.GenericDao;

/**
 * A UserDao.
 * <p>
 * Adds additional behavior to the {@link GenericDao} to operate on {@link User}
 * Entity classes.
 * </p>
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 314 $
 * @since 0.1
 * @see {@link GenericDao}
 * @see {@link User}
 */
public interface UserDao extends GenericDao<User, Long> {

    /**
     * Name of the NamedQuery to find all {@link User} Entities
     */
    public final String NQ_FIND_ALL = "User.findAll";

    /**
     * Name of the NamedQuery to find {@link User} Entity classes in a ordered
     * way.
     */
    public final String NQ_FIND_ALL_ORDERED = "User.findAllOrdered";

    /**
     * Name of the NamedQuery to find a {@link User} by his
     * {@link User#getUsername()}
     */
    public final String NQ_FIND_BY_USERNAME = "User.findByUsername";

}