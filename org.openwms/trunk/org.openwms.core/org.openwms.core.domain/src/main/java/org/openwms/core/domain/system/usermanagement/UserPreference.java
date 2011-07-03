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
 * A UserPreference.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Entity
@DiscriminatorValue("USER")
@Table(name = "COR_USER_PREFERENCE")
public class UserPreference extends AbstractPreference {

    private static final long serialVersionUID = -6569559231034802554L;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    /**
     * Create a new UserPreference.
     * 
     */
    protected UserPreference() {
        super();
    }

    /**
     * Create a new UserPreference.
     * 
     * @param key
     */
    public UserPreference(String username, String key) {
        super(key);
        super.setOwner(username);
    }

    @PrePersist
    protected void onPersist() {
        if (super.getOwner() == null || super.getOwner() != this.user.getUsername()) {
            super.setOwner(this.user.getUsername());
        }
    }
}
