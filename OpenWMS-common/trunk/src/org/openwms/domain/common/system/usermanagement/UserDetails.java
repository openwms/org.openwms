/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common.system.usermanagement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * All minor details of an <code>User</code>.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 * TODO: + List<Email> instead of a single property.
 *
 */
@Entity
@Table(name = "USER_DETAILS")
public class UserDetails {
	private static final long serialVersionUID = 1L;

	/**
	 * Mapping to the <code>User</code> entity.
	 */
	@OneToOne
	@JoinColumn(name = "user_USERNAME", referencedColumnName = "USERNAME")
	private IUser user;

	/**
	 * Description assigned to the <code>User</code> entity.
	 */
	@Column(name = "DESCRIPTION")
	private String description;

	/**
	 * Comment assigned to the <code>User</code> entity.
	 */
	@Column(name = "COMMENT")
	private String comment;

	/**
	 * Email address assigned to the <code>User</code> entity.
	 */
	@Column(name = "EMAIL")
	private String email;

	/**
	 * Phone number assigned to the <code>User</code> entity.
	 */
	@Column(name = "PHONE_NO")
	private String phoneNo;

	/**
	 * Skype account name assigned to the <code>User</code> entity.
	 */
	@Column(name = "SKYPE_NAME")
	private String skypeName;

	/**
	 * Office description assigned to the <code>User</code> entity.
	 */
	@Column(name = "OFFICE")
	private String office;

	/**
	 * Department description assigned to the <code>User</code> entity.
	 */
	@Column(name = "DEPARTMENT")
	private String department;

	public UserDetails() {
		super();
	}

	public IUser getUser() {
		return this.user;
	}

	public void setUser(IUser user) {
		this.user = user;
	}

	public String getPhoneNo() {
		return this.phoneNo;
	}

	public void setPhone(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserComment() {
		return this.comment;
	}

	public void setUserComment(String userComment) {
		this.comment = userComment;
	}

	public String getOffice() {
		return this.office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSkypeName() {
		return this.skypeName;
	}

	public void setSkypeName(String skypeName) {
		this.skypeName = skypeName;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
}
