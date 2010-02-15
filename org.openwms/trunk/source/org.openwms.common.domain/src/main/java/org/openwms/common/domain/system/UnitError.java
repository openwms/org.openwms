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
package org.openwms.common.domain.system;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A UnitError.
 * <p>
 * Describes errors occurring on <code>TransportUnit</code>s as well as
 * <code>LoadUnit</code>s and others.
 * </p>
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Entity
@Table(name = "UNIT_ERROR")
public class UnitError implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unique technical key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    /**
     * Error number.
     */
    @Column(name = "ERROR_NO")
    private String errorNo;

    /**
     * Error message text.
     */
    @Column(name = "ERROR_TEXT")
    private String errorText;

    /* ----------------------------- methods ------------------- */
    /**
     * Create a new UnitError.
     */
    public UnitError() {}

    /**
     * 
     * Create a new UnitError with an error number.
     * 
     * @param errorNo
     */
    public UnitError(String errorNo) {
        this.errorNo = errorNo;
    }

    /**
     * Return the unique technical key.
     * 
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * Return the error number.
     * 
     * @return
     */
    public String getErrorNo() {
        return errorNo;
    }

    /**
     * Set the error number.
     * 
     * @param errorNo
     *            The errorNo to set.
     */
    public void setErrorNo(String errorNo) {
        this.errorNo = errorNo;
    }

    /**
     * Return the error text.
     * 
     * @return
     */
    public String getErrorText() {
        return errorText;
    }

    /**
     * Set the error text.
     * 
     * @param errorText
     *            The errorText to set.
     */
    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

}