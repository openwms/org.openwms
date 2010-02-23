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

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;

/**
 * Detail information about an {@link User}.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Embeddable
public class UserDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A Sex.
     * <p>
     * {@link User}s sex.
     * </p>
     * 
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public static enum SEX {
        /**
         * Male sex.
         */
        MALE,
        /**
         * Female sex.
         */
        FEMALE
    }

    /**
     * Description text of the {@link User}.
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * Comment text of the {@link User}.
     */
    @Column(name = "COMMENT")
    private String comment;

    /**
     * Phone number assigned to the {@link User}.
     */
    @Column(name = "PHONE_NO")
    private String phoneNo;

    /**
     * Skype account name assigned to the {@link User}.
     */
    @Column(name = "SKYPE_NAME")
    private String skypeName;

    /**
     * Office description assigned to the {@link User}.
     */
    @Column(name = "OFFICE")
    private String office;

    /**
     * Department description assigned to the {@link User}.
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
    /**
     * Create a new <code>UserDetails</code> instance.
     */
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