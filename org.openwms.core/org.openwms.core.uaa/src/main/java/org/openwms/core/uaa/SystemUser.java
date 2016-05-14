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

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * A SystemUser is granted with all privileges and omits all defined security constraints. Whenever a SystemUser logs in, she is assigned to
 * a virtual {@link Role} with the name ROLE_SYSTEM. Furthermore this kind of {@link Role} is immutable and it is not allowed for the
 * SystemUser to change her {@link UserDetails} or {@link UserPassword}. Changing the {@link UserPassword} has to be done in the application
 * configuration when the project is setup.
 *
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version 0.2
 * @GlossaryTerm
 * @see User
 * @since 0.1
 */
@Entity
public class SystemUser extends User implements Serializable {

    /**
     * The defined fullname of the system user. Default {@value} .
     */
    public static final String SYSTEM_USERNAME = "openwms";
    /**
     * The virtual {@code Role} of the SystemUser.
     */
    public static final String SYSTEM_ROLE_NAME = "ROLE_SYSTEM";

    /**
     * Dear JPA...
     */
    protected SystemUser() {
    }

    /**
     * Create a new SystemUser.
     *
     * @param username SystemUser's username
     * @param password SystemUser's password
     */
    public SystemUser(String username, String password) {
        super(username, password);
        setFullname(SYSTEM_USERNAME);
    }

    /**
     * Check whether {@code user} is the system user.
     *
     * @param user The user to check
     * @return {@literal true} if user is the system user, otherwise {@literal false}
     */
    public static boolean isSuperUser(User user) {
        return (user instanceof SystemUser);
    }
}