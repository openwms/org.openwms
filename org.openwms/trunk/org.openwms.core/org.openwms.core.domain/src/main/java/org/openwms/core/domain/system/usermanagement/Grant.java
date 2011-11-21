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

import org.openwms.core.annotation.GlossaryTerm;

/**
 * A Grant encapsulates a permission to any kind of application object. Grants
 * (permissions) to security aware application objects can be permitted or
 * denied to <code>User</code>s or <code>Role</code>s, in order of the security
 * configuration. Usually one or more <code>Grant</code>s are assigned to a
 * <code>Role</code> and <code>User</code>s are assigned to one or more
 * <code>Role</code>s.
 * <p>
 * UI permissions are managed with <code>Grant</code>s as well.
 * </p>
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.domain.system.usermanagement.User
 * @see org.openwms.core.domain.system.usermanagement.Role
 * @see org.openwms.core.domain.system.usermanagement.SecurityObject
 */
@GlossaryTerm
@Entity
@DiscriminatorValue("GRANT")
public class Grant extends SecurityObject {

    private static final long serialVersionUID = 2061059874657176727L;

    /**
     * Create a new Grant.
     */
    public Grant() {
        super();
    }

    /**
     * Create a new Grant.
     * 
     * @param name
     *            The name of the <code>Grant</code>
     * @param description
     *            The description text of the <code>Grant</code>
     */
    public Grant(String name, String description) {
        super(name, description);
    }

    /**
     * Create a new Grant.
     * 
     * @param name
     *            The name of the <code>Grant</code>
     */
    public Grant(String name) {
        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * Use the hashCode of the superclass with the hashCode of 'GRANT' to
     * distinguish between <code>Grant</code>s and other
     * <code>SecurityObject</code>s like <code>Role</code>s.
     * 
     * @see org.openwms.core.domain.system.usermanagement.SecurityObject#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = super.hashCode();
        result = prime * result + "GRANT".hashCode();
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.domain.system.usermanagement.SecurityObject#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Grant)) {
            return false;
        }
        Grant other = (Grant) obj;
        if (this.getName() == null) {
            if (other.getName() != null) {
                return false;
            }
        } else if (!this.getName().equals(other.getName())) {
            return false;
        }
        return true;
    }
}
