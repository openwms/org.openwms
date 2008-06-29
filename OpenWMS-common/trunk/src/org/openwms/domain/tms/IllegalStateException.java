/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.tms;

/**
 * A IllegalStateException.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
public class IllegalStateException extends TransportManagementException {

	private static final long serialVersionUID = 1L;

	/**
	 * Create a new IllegalStateException.
	 * 
	 */
	public IllegalStateException() {
	}

	/**
	 * Create a new IllegalStateException.
	 * 
	 * @param message
	 */
	public IllegalStateException(String message) {
		super(message);
	}

	/**
	 * Create a new IllegalStateException.
	 * 
	 * @param cause
	 */
	public IllegalStateException(Throwable cause) {
		super(cause);
	}

	/**
	 * Create a new IllegalStateException.
	 * 
	 * @param message
	 * @param cause
	 */
	public IllegalStateException(String message, Throwable cause) {
		super(message, cause);
	}

}
