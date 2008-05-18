/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common.system;

import java.util.Date;

public interface IMessage {

	/**
	 * Get the id.
	 * 
	 * @return the id.
	 */
	public abstract long getId();

	/**
	 * Get the messageNo.
	 * 
	 * @return the messageNo.
	 */
	public abstract int getMessageNo();

	/**
	 * Get the messageText.
	 * 
	 * @return the messageText.
	 */
	public abstract String getMessageText();

	/**
	 * Get the created.
	 * 
	 * @return the created.
	 */
	public abstract Date getCreated();

}