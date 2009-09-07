/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain.system.usermanagement;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;

/**
 * All minor details of an <code>User</code>.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 * 
 */
@Embeddable
public class UserDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	public static enum SEX {
		MAN, WOMAN
	}

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

	/**
	 * Image of the user.
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "IMAGE")
	private byte[] image;

	/**
	 * Sex of the user.
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "SEX")
	private SEX sex;

	/* ----------------------------- methods ------------------- */
	public UserDetails() {}

	public String getPhoneNo() {
		return this.phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getOffice() {
		return this.office;
	}

	public void setOffice(String office) {
		this.office = office;
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

	public byte[] getImage() {
		return this.image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public SEX getSex() {
		return sex;
	}

	public void setSex(SEX sex) {
		this.sex = sex;
	}
}
