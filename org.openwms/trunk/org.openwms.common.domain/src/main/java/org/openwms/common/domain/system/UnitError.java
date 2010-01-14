/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain.system;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A UnitError. Describes errors occuring on <code>TransportUnit</code>s as well as <code>LoadUnit</code>s and
 * others.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 */
@Entity
@Table(name = "UNIT_ERROR")
public class UnitError implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Primary key.
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
     * Create a new UnitError.
     * 
     * @param errorNo
     */
    public UnitError(String errorNo) {
	this.errorNo = errorNo;
    }

    /**
     * {@inheritDoc}
     */
    public Long getId() {
	return id;
    }

    /**
     * {@inheritDoc}
     */
    public String getErrorNo() {
	return errorNo;
    }

    /**
     * Set the errorNo.
     * 
     * @param errorNo
     *                The errorNo to set.
     */
    public void setErrorNo(String errorNo) {
	this.errorNo = errorNo;
    }

    /**
     * {@inheritDoc}
     */
    public String getErrorText() {
	return errorText;
    }

    /**
     * Set the errorText.
     * 
     * @param errorText
     *                The errorText to set.
     */
    public void setErrorText(String errorText) {
	this.errorText = errorText;
    }

}
