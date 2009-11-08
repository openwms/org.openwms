/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain.values;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * A Problem.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 */
@Embeddable
public class Problem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "OCCURED")
	private Date occured;

	@Column(name = "MESSAGE_NO")
	private int messageNo;

	@Column(name = "MESSAGE")
	private String message;

	/* ----------------------------- methods ------------------- */
	/**
	 * Creates a new Problem.
	 */
	public Problem() {
		this.occured = new Date();
	}

	/**
	 * Get the occured.
	 * 
	 * @return the occured.
	 */
	public Date getOccured() {
		return occured;
	}

	/**
	 * Set the occured.
	 * 
	 * @param occured
	 *            The occured to set.
	 */
	public void setOccured(Date occured) {
		this.occured = occured;
	}

	/**
	 * Get the messageNo.
	 * 
	 * @return the messageNo.
	 */
	public int getMessageNo() {
		return messageNo;
	}

	/**
	 * Set the messageNo.
	 * 
	 * @param messageNo
	 *            The messageNo to set.
	 */
	public void setMessageNo(int messageNo) {
		this.messageNo = messageNo;
	}

	/**
	 * Get the message.
	 * 
	 * @return the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set the message.
	 * 
	 * @param message
	 *            The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
