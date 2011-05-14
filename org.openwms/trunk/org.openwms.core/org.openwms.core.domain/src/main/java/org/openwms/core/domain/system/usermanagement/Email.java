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

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.domain.DomainObject;
import org.openwms.core.util.validation.AssertUtils;

/**
 * An Email, encapsulates the email address of an <code>User</code>.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.domain.system.usermanagement.User
 */
@Entity
@Table(name = "COR_EMAIL", uniqueConstraints = @UniqueConstraint(columnNames = { "USERNAME", "ADDRESS" }))
public class Email extends AbstractEntity implements DomainObject<Long>, Serializable {

    private static final long serialVersionUID = 3182027866592095069L;

    /**
     * Unique technical key.
     */
    @Id
    @GeneratedValue
    // CHECK [scherrer] : what is this??
    @ManyToOne
    private Long id;

    /**
     * Unique identifier of the <code>Email</code> (not-null).
     */
    @Column(name = "USERNAME", nullable = false)
    private String username;

    /**
     * The email address as String (not-null).
     */
    @Column(name = "ADDRESS", nullable = false)
    private String emailAddress;

    /**
     * The fullname of the <code>Email</code>.
     */
    @Column(name = "FULL_NAME")
    private String fullName;

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
    private Email() {}

    /**
     * Create a new <code>Email</code> with an username and an emailAddress.
     * 
     * @param username
     *            The name of the <code>User</code>
     * @param emailAddress
     *            The email address of the <code>User</code>
     */
    public Email(String username, String emailAddress) {
        AssertUtils.isNotEmpty(username, "Username must not be null or empty");
        AssertUtils.isNotEmpty(emailAddress, "EmailAddress must not be null or empty");
        assert username != null && !username.equals("");
        assert emailAddress != null && emailAddress.equals("");
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
        return this.id == null;
    }

    /**
     * Returns the name of the <code>User</code> who owns the <code>Email</code>
     * .
     * 
     * @return The username as String
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Assign the <code>Email</code> to an <code>User</code>.
     * 
     * @param username
     *            Name of the <code>User</code>.
     */
    public void setUsername(String username) {
        this.username = username;
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
     * Return the fullName.
     * 
     * @return The fullName.
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
     * {@inheritDoc}
     */
    @Override
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