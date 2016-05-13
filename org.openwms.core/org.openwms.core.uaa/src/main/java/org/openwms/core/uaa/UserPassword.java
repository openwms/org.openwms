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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

import org.ameba.integration.jpa.BaseEntity;
import org.openwms.core.AbstractEntity;
import org.springframework.util.Assert;

/**
 * Is a representation of an {@link User} together with her password. <p> When an {@link User} changes her password, the current password is
 * added to a history list of passwords. This is necessary to omit {@link User}s from setting formerly used passwords. </p>
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.2
 * @GlossaryTerm
 * @see org.openwms.core.uaa.User
 * @since 0.1
 */
@Entity
@Table(name = "COR_USER_PASSWORD")
public class UserPassword extends BaseEntity implements Serializable {

    /** {@link User} assigned to this password. */
    @ManyToOne
    private User user;
    /** Password. */
    @Column(name = "C_PASSWORD")
    private String password;
    /** Date of the last password change. */
    @Column(name = "C_PASSWORD_CHANGED")
    @OrderBy
    private Date passwordChanged = new Date();

    /* ----------------------------- methods ------------------- */

    /**
     * Create a new {@link UserPassword}.
     *
     * @param user The {@link User} to assign
     * @param password The {@code password} as String to assign
     * @throws IllegalArgumentException when {@link User} or {@code password} is {@literal null} or empty
     */
    public UserPassword(User user, String password) {
        Assert.notNull(user, "User must not be null");
        Assert.hasText(password, "Password must not be null");
        this.user = user;
        this.password = password;
    }

    /**
     * Dear JPA...
     */
    protected UserPassword() {
        super();
    }

    /**
     * Return the {@link User} of this password.
     *
     * @return The {@link User} of this password
     */
    public User getUser() {
        return user;
    }

    /**
     * Change the {@link User}.
     *
     * @param user The new {@link User}
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Return the current password.
     *
     * @return The current password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Return the date of the last password change.
     *
     * @return The date when the password has changed
     */
    public Date getPasswordChanged() {
        return new Date(passwordChanged.getTime());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Does not call the superclass. Uses the password and user for calculation.
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
     * {@inheritDoc}
     * <p>
     * Comparison is done with the business-key (user and password). {@link AbstractEntity#equals(Object)} is not called to avoid comparison
     * with the UUID.
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
}