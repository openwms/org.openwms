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
package org.openwms.core.uaa.api;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * A UserDetailsVO.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
// @JsonAutoDetect
public class UserDetailsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private byte[] image = new byte[0];
    private String description = "";
    private String comment = "";
    private String phoneNo = "";
    private String skypeName = "";
    private String office = "";
    private String department = "";
    private String sex;

    /**
     * Create a new UserDetailsVO.
     */
    public UserDetailsVO() {
    }

    /**
     * Get the image.
     *
     * @return the image.
     *
     *         public byte[] getImage() { return image; }
     */
    /**
     * Set the image.
     *
     * @param image
     *            The image to set.
     *
     *            public void setImage(byte[] image) { this.image = image; }
     */
    /**
     * Get the description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description.
     *
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the comment.
     *
     * @return the comment.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set the comment.
     *
     * @param comment The comment to set.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Get the phoneNo.
     *
     * @return the phoneNo.
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * Set the phoneNo.
     *
     * @param phoneNo The phoneNo to set.
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * Get the skypeName.
     *
     * @return the skypeName.
     */
    public String getSkypeName() {
        return skypeName;
    }

    /**
     * Set the skypeName.
     *
     * @param skypeName The skypeName to set.
     */
    public void setSkypeName(String skypeName) {
        this.skypeName = skypeName;
    }

    /**
     * Get the office.
     *
     * @return the office.
     */
    public String getOffice() {
        return office;
    }

    /**
     * Set the office.
     *
     * @param office The office to set.
     */
    public void setOffice(String office) {
        this.office = office;
    }

    /**
     * Get the department.
     *
     * @return the department.
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Set the department.
     *
     * @param department The department to set.
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Get the sex.
     *
     * @return the sex.
     */
    public String getSex() {
        return sex;
    }

    /**
     * Set the sex.
     *
     * @param sex The sex to set.
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * Get the image.
     *
     * @return the image.
     */
    public byte[] getImage() {
        if (image == null) {
            return new byte[0];
        }
        return image;
    }

    /**
     * Set the image.
     *
     * @param image The image to set.
     */
    @JsonIgnore
    public void setImage(byte[] image) {
        this.image = image;
    }
}
