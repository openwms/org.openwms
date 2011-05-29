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
package org.openwms.core.domain.system.usermanagement;

import javax.persistence.Entity;

/**
 * A SystemUser is granted with all privileges and omits all security
 * constraints.
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.domain.system.usermanagement.User
 */
@Entity
public class SystemUser extends User {

    private static final long serialVersionUID = -7575215406745881912L;

    /**
     * The defined fullname of the system user.
     */
    public static final String SYSTEM_USERNAME = "OPENWMS";

    /**
     * Create a new SystemUser.
     */
    protected SystemUser() {}

    /**
     * Create a new SystemUser.
     * 
     * @param username
     *            User's username
     */
    public SystemUser(String username, String password) {
        super(username, password);
        this.setFullname(SYSTEM_USERNAME);
    }

}
