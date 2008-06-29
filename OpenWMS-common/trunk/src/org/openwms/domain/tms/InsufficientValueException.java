/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.tms;

/**
 * A InsufficientValueException.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
public class InsufficientValueException extends TransportManagementException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Create a new InsufficientValueException.
	 * 
	 */
	public InsufficientValueException() {
	}

	/**
	 * Create a new InsufficientValueException.
	 * 
	 * @param message
	 */
	public InsufficientValueException(String message) {
		super(message);
	}

	/**
	 * Create a new InsufficientValueException.
	 * 
	 * @param cause
	 */
	public InsufficientValueException(Throwable cause) {
		super(cause);
	}

	/**
	 * Create a new InsufficientValueException.
	 * 
	 * @param message
	 * @param cause
	 */
	public InsufficientValueException(String message, Throwable cause) {
		super(message, cause);
	}

}
