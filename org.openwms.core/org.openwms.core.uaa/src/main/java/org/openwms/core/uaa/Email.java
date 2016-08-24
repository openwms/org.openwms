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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.Objects;

import org.ameba.integration.jpa.BaseEntity;
import org.springframework.util.Assert;

/**
 * An Email represents the email address of an {@code User}.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.2
 * @GlossaryTerm
 * @see User
 * @since 0.1
 */
@Entity
@Table(name = "COR_EMAIL", uniqueConstraints = @UniqueConstraint(columnNames = {"C_USERNAME", "C_ADDRESS"}))
public class Email extends BaseEntity implements Serializable {

    /** Unique identifier of the {@code Email} (not nullable). */
    @Column(name = "C_USERNAME", nullable = false)
    private String username;
    /** The email address as String (not nullable). */
    @Column(name = "C_ADDRESS", nullable = false)
    private String emailAddress;
    /** The fullname of the {@code User}. */
    @Column(name = "C_FULL_NAME")
    private String fullname;

    /* ----------------------------- methods ------------------- */

    /**
     * Dear JPA...
     */
    protected Email() {
    }

    /**
     * Create a new {@code Email} with an {@code username} and an {@code emailAddress}.
     *
     * @param username The name of the User
     * @param emailAddress The email address of the User
     * @throws IllegalArgumentException when userName or emailAddress is {@literal null} or empty
     */
    public Email(String username, String emailAddress) {
        Assert.hasText(username, "Username must not be null or empty");
        Assert.hasText(emailAddress, "EmailAddress must not be null or empty");
        this.username = username;
        this.emailAddress = emailAddress;
    }

    /**
     * Returns the name of the {@code User} who owns this {@code Email}.
     *
     * @return The username as String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Assign the {@code Email} to an {@code User}.
     *
     * @param userName Name of the {@code User}.
     */
    public void setUsername(String userName) {
        this.username = userName;
    }

    /**
     * Return the emailAddress.
     *
     * @return The emailAddress.
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Set the emailAddress.
     *
     * @param emailAddress The emailAddress to set.
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Return the fullname.
     *
     * @return The fullname.
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Set the fullname.
     *
     * @param fullname The fullname to set.
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * {@inheritDoc}
     *
     * All fields.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(username, email.username) &&
                Objects.equals(emailAddress, email.emailAddress) &&
                Objects.equals(fullname, email.fullname);
    }

    /**
     * {@inheritDoc}
     *
     * All fields.
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, emailAddress, fullname);
    }

    /**
     * Return the emailAddress as String.
     *
     * @return the emailAddress
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return emailAddress;
    }
}