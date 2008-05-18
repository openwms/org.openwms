/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common.system.usermanagement;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * 
 * An Email.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
@Embeddable
public class Email implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Email Address.
	 */
	@org.hibernate.validator.Email
	private String emailAddress;
	
	/**
	 * Fullname belonging to this <code>Email</code>.
	 */
	private String fullName;

	/* ----------------------------- methods ------------------- */
	public Email(String emailAddress) {
		this.emailAddress = emailAddress;
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
	 * @param emailAddress The emailAddress to set.
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
	 * @param fullName The fullName to set.
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	
}
