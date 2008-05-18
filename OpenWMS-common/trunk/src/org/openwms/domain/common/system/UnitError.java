/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A UnitError. Describes errors occuring on <code>TransportUnit</code>s as
 * well as <code>LoadUnit</code>s and others.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
@Entity
@Table(name = "UNIT_ERROR")
public class UnitError implements IUnitError {

	private static final long serialVersionUID = 1L;

	/**
	 * Primary key.
	 */
	@Id
	@Column(name = "ID")
	private long id;

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

	/**
	 * Get the errorNo.
	 * 
	 * @return the errorNo.
	 */
	public String getErrorNo() {
		return errorNo;
	}

	/**
	 * Set the errorNo.
	 * 
	 * @param errorNo
	 *            The errorNo to set.
	 */
	public void setErrorNo(String errorNo) {
		this.errorNo = errorNo;
	}

	/**
	 * Get the errorText.
	 * 
	 * @return the errorText.
	 */
	public String getErrorText() {
		return errorText;
	}

	/**
	 * Set the errorText.
	 * 
	 * @param errorText
	 *            The errorText to set.
	 */
	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	/**
	 * Get the id.
	 * 
	 * @return the id.
	 */
	public long getId() {
		return id;
	}

}
