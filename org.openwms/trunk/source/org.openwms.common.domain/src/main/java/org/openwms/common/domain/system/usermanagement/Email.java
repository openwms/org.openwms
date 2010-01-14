/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain.system.usermanagement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * An Email.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 */
@Entity
@Table(name = "EMAIL")
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
	 * Unique identifier of the <code>User</code>.
	 */
	@Column(name = "USERNAME", nullable = false)
	private String username;

	/**
	 * Email Address.
	 */
	@Column(name = "ADDRESS", nullable = false)
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

	public Email(String username, String emailAddress) {
		assert username != null && !username.equals("");
		assert emailAddress != null && !emailAddress.equals("");
		this.username = username;
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
	 * Returns true if this is a transient object.
	 * 
	 * @return
	 */
	public boolean isNew() {
		return this.id == null;
	}

	public String getUsername() {
		return this.username;
	}

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
	 * Returns Hibernate version field.
	 * 
	 * @return
	 */
	public long getVersion() {
		return version;
	}
}
