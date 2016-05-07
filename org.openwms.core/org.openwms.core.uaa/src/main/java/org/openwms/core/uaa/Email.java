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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import java.io.Serializable;

import org.openwms.core.AbstractEntity;
import org.openwms.core.validation.AssertUtils;

/**
 * An Email represents the email address of an <code>User</code>.
 * 
 * @GlossaryTerm
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see User
 */
@Entity
@Table(name = "COR_EMAIL", uniqueConstraints = @UniqueConstraint(columnNames = { "C_USERNAME", "C_ADDRESS" }))
public class Email extends AbstractEntity<Long> implements Serializable {

    private static final long serialVersionUID = 3182027866592095069L;
    /**
     * Unique technical key.
     */
    @Id
    @Column(name = "C_ID")
    @GeneratedValue
    private Long id;
    /**
     * Unique identifier of the <code>Email</code> (not nullable).
     */
    @Column(name = "C_USERNAME", nullable = false)
    private String username;
    /**
     * The email address as String (not nullable).
     */
    @Column(name = "C_ADDRESS", nullable = false)
    private String emailAddress;
    /**
     * The fullname of the <code>User</code>.
     */
    @Column(name = "C_FULL_NAME")
    private String fullname;
    /**
     * Version field.
     */
    @Version
    @Column(name = "C_VERSION")
    private long version;

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    @SuppressWarnings("unused")
    private Email() {

    }

    /**
     * Create a new <code>Email</code> with an <code>username</code> and an <code>emailAddress</code>.
     * 
     * @param username
     *            The name of the User
     * @param emailAddress
     *            The email address of the User
     * @throws IllegalArgumentException
     *             when userName or emailAddress is <code>null</code> or empty
     */
    public Email(String username, String emailAddress) {
        AssertUtils.isNotEmpty(username, "Username must not be null or empty");
        AssertUtils.isNotEmpty(emailAddress, "EmailAddress must not be null or empty");
        this.username = username;
        this.emailAddress = emailAddress;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNew() {
        return id == null;
    }

    /**
     * Returns the name of the <code>User</code> who owns this <code>Email</code>.
     * 
     * @return The username as String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Assign the <code>Email</code> to an <code>User</code>.
     * 
     * @param userName
     *            Name of the <code>User</code>.
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
     * @param emailAddress
     *            The emailAddress to set.
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
     * @param fullname
     *            The fullname to set.
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getVersion() {
        return version;
    }

    /**
     * Return the emailAddress as String.
     * 
     * @see java.lang.Object#toString()
     * @return the emailAddress
     */
    @Override
    public String toString() {
        return emailAddress;
    }
}