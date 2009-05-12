/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain.system.usermanagement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

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
    private Email email;

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

    /* ----------------------------- methods ------------------- */
    public UserDetails() {}

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

    public Email getEmails() {
	return email;
    }

    public void setEmail(Email email) {
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
