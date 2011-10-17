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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;

import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.domain.DomainObject;
import org.openwms.core.util.validation.AssertUtils;

/**
 * Encapsulates the {@link User}s password.
 * <p>
 * When an {@link User} changes his password, the current password is added to
 * the history list of passwords.
 * </p>
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.domain.system.usermanagement.User
 */
@Entity
@Table(name = "COR_USER_PASSWORD")
public class UserPassword extends AbstractEntity implements DomainObject<Long> {

    private static final long serialVersionUID = 1678609250279381615L;

    /**
     * Unique technical key.
     */
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    /**
     * {@link User} assigned to this password.
     */
    @ManyToOne
    private User user;

    /**
     * Password.
     */
    @Column(name = "C_PASSWORD")
    private String password;

    /**
     * Date of the last password change.
     */
    @Column(name = "PASSWORD_CHANGED")
    @OrderBy
    private Date passwordChanged = new Date();

    /**
     * Version field.
     */
    @Version
    @Column(name = "C_VERSION")
    private long version;

    /* ----------------------------- methods ------------------- */
    /**
     * Create a new <code>UserPassword</code>.
     * 
     * @param user
     *            The {@link User} to assign.
     * @param password
     *            The password as String to assign.
     */
    public UserPassword(User user, String password) {
        AssertUtils.notNull(user, "User must not be null");
        AssertUtils.notNull(password, "Password must not be null");
        this.user = user;
        this.password = password;
    }

    /**
     * Constructor only for the persistence provider.
     */
    protected UserPassword() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getId() {
        return this.id;
    }

    /**
     * Returns the {@link User} of this password.
     * 
     * @return The {@link User} of this password.
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Change the {@link User}.
     * 
     * @param user
     *            The new {@link User}.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns the current password.
     * 
     * @return The current password.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Returns the date of the last password change.
     * 
     * @return The date when the password was changed.
     */
    public Date getPasswordChanged() {
        return passwordChanged == null ? null : new Date(passwordChanged.getTime());
    }

    /**
     * @see java.lang.Object#hashCode()
     * @return the hashCode
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    /**
     * Comparison is done with the business-key (user and password).
     * {@link AbstractEntity#equals(Object)} is not called to avoid comparison
     * with the UUID.
     * 
     * @see AbstractEntity#equals(java.lang.Object)
     * @param obj
     *            The other to compare
     * @return <code>true</code> if equals otherwise <code>false</code>
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserPassword)) {
            return false;
        }
        UserPassword other = (UserPassword) obj;
        if (password == null) {
            if (other.password != null) {
                return false;
            }
        } else if (!password.equals(other.password)) {
            return false;
        }
        if (user == null) {
            if (other.user != null) {
                return false;
            }
        } else if (!user.equals(other.user)) {
            return false;
        }
        return true;
    }

    /**
     * Return the persistent id as String or an empty String.
     * 
     * @see java.lang.Object#toString()
     * @return The id or an empty String
     */
    @Override
    public String toString() {
        return (id == null ? "" : id.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getVersion() {
        return this.version;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNew() {
        return this.id == null;
    }
}