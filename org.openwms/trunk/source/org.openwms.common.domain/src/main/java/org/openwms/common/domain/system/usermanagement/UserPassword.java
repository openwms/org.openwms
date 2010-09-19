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
package org.openwms.common.domain.system.usermanagement;

import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.openwms.common.domain.AbstractEntity;

/**
 * A Password.
 * <p>
 * All passwords used by the {@link User}. When the {@link User} changes her
 * password, the current password moves to the beginning of the password history
 * list.
 * </p>
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Entity
@Table(name = "APP_USER_PASSWORD")
public class UserPassword extends AbstractEntity implements Serializable {

    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 1678609250279381615L;

    /**
     * Unique technical key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = AUTO)
    private Long id;

    /**
     * User assigned to this password.
     */
    @ManyToOne
    private User user;

    /**
     * Password.
     */
    @Column(name = "C_PASSWORD")
    private String password;

    @Column(name = "PASSWORD_CHANGED")
    private Date passwordChanged = new Date();

    /* ----------------------------- methods ------------------- */
    /**
     * Create a new <code>UserPassword</code>.
     * 
     * @param user
     *            The {@link User} to assign
     * @param password
     *            The password as String to assign
     */
    public UserPassword(User user, String password) {
        this.user = user;
        this.password = password;
    }

    /**
     * Constructor only for the persistence provider.
     */
    protected UserPassword() {}

    /**
     * Return the unique technical key.
     * 
     * @return The unique technical key
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Get the {@link User} of this password.
     * 
     * @return The {@link User} of this password
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
     * Return the stored password.
     * 
     * @return The stored password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * When was the password changed.
     * 
     * @return The date when password changed
     */
    public Date getPasswordChanged() {
        return passwordChanged;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (!(obj instanceof UserPassword)) return false;
        final UserPassword other = (UserPassword) obj;
        if (password == null) {
            if (other.password != null) return false;
        } else if (!password.equals(other.password)) return false;
        if (user == null) {
            if (other.user != null) return false;
        } else if (!user.equals(other.user)) return false;
        return true;
    }

    /**
     * Return the persistent id as String.
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return id.toString();
    }
}