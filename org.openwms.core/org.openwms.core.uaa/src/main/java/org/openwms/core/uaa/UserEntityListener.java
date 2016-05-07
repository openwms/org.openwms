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

import javax.persistence.PrePersist;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openwms.core.AbstractGenericJpaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * An UserDaoImpl is an extension of a {@link AbstractGenericJpaDao} about functionality regarding {@link User}s. The stereotype annotation
 * {@link Repository} marks this class as DAO in the architecture and enables exception translation and component scanning. It can be
 * injected by name {@value #COMPONENT_NAME}. <p> All methods have to be invoked within an active transaction context. </p>
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @see AbstractGenericJpaDao
 * @see UserRepository
 * @since 0.1
 */
public class UserEntityListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEntityListener.class);

    /**
     * Is the passed in User object is the SuperUser or no action is performed.
     *
     * @param user The user to persist
     */
    @PrePersist
    public void prePersist(User user) {
        if (isSuperUser(user)) {
            return;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Is the passed in User object is the SuperUser or <code>null</code> no action is performed.
     */
    @Override
    public User save(User user) {
        if (isSuperUser(user)) {
            return user;
        }
        return super.save(user);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Is the passed in User object is the SuperUser or <code>null</code> no action is performed.
     */
    @Override
    public void remove(User user) {
        if (isSuperUser(user)) {
            LOGGER.info("Not allowed to remove system user, return quietly");
            return;
        }
        for (Role role : user.getRoles()) {
            role.removeUser(user);
        }
        user.setRoles(null);
        super.remove(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findByNameAndPassword(UserPassword userPassword) {
        Map<String, String> params = new HashMap<>();
        params.put("username", userPassword.getUser().getUsername());
        params.put("password", userPassword.getPassword());
        List<User> users = super.findByNamedParameters(User.NQ_FIND_BY_USERNAME_PASSWORD, params);
        if (users == null || users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    private boolean isSuperUser(User user) {
        return (user == null || user instanceof SystemUser || SystemUser.SYSTEM_USERNAME.equals(user.getFullname()));
    }
}