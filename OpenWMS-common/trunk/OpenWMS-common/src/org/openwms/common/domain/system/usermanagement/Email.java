/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain.system.usermanagement;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

/**
 * 
 * An Email.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
@Entity
public class Email implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Primary Key.
     */
    @Id
    @GeneratedValue
    @ManyToOne
    private Long id;
    /**
     * Email Address.
     */
    //@org.hibernate.validator.Email
    private String emailAddress;

    /**
     * Fullname belonging to this <code>Email</code>.
     */
    private String fullName;

    /**
     * Hibernate Version field.
     */
    @Version
    private long version;

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    @SuppressWarnings("unused")
    private Email() {}

    public Email(String emailAddress) {
	this.emailAddress = emailAddress;
    }

    /**
     * Get the Primary Key.
     * 
     * @return
     */
    public Long getId() {
	return id;
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
     *                The emailAddress to set.
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
     *                The fullName to set.
     */
    public void setFullName(String fullName) {
	this.fullName = fullName;
    }

    /**
     * Returns Hibernate version field.
     * 
     * @return
     */
    public long getVersion() {
	return version;
    }
}
