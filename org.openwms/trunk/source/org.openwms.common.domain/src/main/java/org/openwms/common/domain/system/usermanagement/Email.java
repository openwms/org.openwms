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

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

/**
 * An Email.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.common.domain.system.usermanagement.User
 */
@Entity
@Table(name = "EMAIL", uniqueConstraints = @UniqueConstraint(columnNames = { "USERNAME", "ADDRESS" }))
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unique technical key.
     */
    @Id
    @GeneratedValue
    @ManyToOne
    private Long id;

    /**
     * Unique identifier of the {@link Email}.
     */
    @Column(name = "USERNAME", nullable = false)
    private String username;

    /**
     * Email address of the
     * {@link org.openwms.common.domain.system.usermanagement.User}.
     */
    @Column(name = "ADDRESS", nullable = false)
    private String emailAddress;

    /**
     * Full name belonging to this
     * {@link org.openwms.common.domain.system.usermanagement.User}.
     */
    private String fullName;

    /**
     * Version field.
     */
    @Version
    private long version;

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    @SuppressWarnings("unused")
    private Email() { }

    /**
     * Create a new Email for the
     * {@link org.openwms.common.domain.system.usermanagement.User} with his
     * <code>username</code> and <code>emailAddress</code>.
     * 
     * @param username
     *            The name of the User
     * @param emailAddress
     *            The email address of the User
     */
    public Email(String username, String emailAddress) {
        assert username != null && !username.equals("");
        assert emailAddress != null && !emailAddress.equals("");
        this.username = username;
        this.emailAddress = emailAddress;
    }

    /**
     * Get the Primary Key.
     * 
     * @return The unique technical key
     */
    public Long getId() {
        return id;
    }

    /**
     * Checks if the instance is transient.
     * 
     * @return - true: Entity is not present on the persistent storage.<br>
     *         - false : Entity already exists on the persistence storage
     */
    public boolean isNew() {
        return this.id == null;
    }

    /**
     * Return the name of the user that belongs to this email.
     * 
     * @return - Username as String
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Set the name of the user that belongs to this email.
     * 
     * @param username
     *            - Name of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the emailAddress.
     * 
     * @return the emailAddress.
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
     * Get the fullName.
     * 
     * @return the fullName.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Set the fullName.
     * 
     * @param fullName
     *            The fullName to set.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * JPA optimistic locking.
     * 
     * @return - Version field
     */
    public long getVersion() {
        return version;
    }

    /**
     * Return the emailAddress.
     * 
     * @see java.lang.Object#toString()
     * @return As String
     */
    @Override
    public String toString() {
        return emailAddress;
    }
}