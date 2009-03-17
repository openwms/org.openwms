/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao;

/**
 * A DAOAccessException.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class DAOAccessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Create a new DAOAccessException.
	 * 
	 */
	public DAOAccessException() {
		super();
	}

	/**
	 * Create a new DAOAccessException.
	 * 
	 * @param message
	 */
	public DAOAccessException(String message) {
		super(message);
	}

	/**
	 * Create a new DAOAccessException.
	 * 
	 * @param cause
	 */
	public DAOAccessException(Throwable cause) {
		super(cause);
	}

	/**
	 * Create a new DAOAccessException.
	 * 
	 * @param message
	 * @param cause
	 */
	public DAOAccessException(String message, Throwable cause) {
		super(message, cause);
	}

}
