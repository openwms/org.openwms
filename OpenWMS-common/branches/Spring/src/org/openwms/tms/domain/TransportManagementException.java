/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.tms.domain;

/**
 * A TransportManagementException.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
public class TransportManagementException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Create a new TransportManagementException.
	 * 
	 */
	public TransportManagementException() {	}

	/**
	 * Create a new TransportManagementException.
	 * 
	 * @param message
	 */
	public TransportManagementException(String message) {
		super(message);
	}

	/**
	 * Create a new TransportManagementException.
	 * 
	 * @param cause
	 */
	public TransportManagementException(Throwable cause) {
		super(cause);
	}

	/**
	 * Create a new TransportManagementException.
	 * 
	 * @param message
	 * @param cause
	 */
	public TransportManagementException(String message, Throwable cause) {
		super(message, cause);
	}

}
