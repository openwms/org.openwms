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

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.openwms.core.domain.system.AbstractPreference;

/**
 * A RolePreference. Used to store settings in Role scope, only valid for the
 * assigned Role.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Entity
@DiscriminatorValue("ROLE")
@Table(name = "COR_ROLE_PREFERENCE")
public class RolePreference extends AbstractPreference {

    private static final long serialVersionUID = 8267024349554036680L;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID", nullable = false)
    private Role role;

    /**
     * Create a new RolePreference.
     */
    protected RolePreference() {
        super();
    }

    /**
     * Create a new RolePreference.
     * 
     * @param key
     */
    public RolePreference(String rolename, String key) {
        super(key);
        super.setOwner(rolename);
    }

    @PrePersist
    protected void onPersist() {
        if (super.getOwner() == null || super.getOwner() != this.role.getName()) {
            super.setOwner(this.role.getName());
        }
    }

}
