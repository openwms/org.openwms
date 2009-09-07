/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service.exception;

/**
 * A ServiceException.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
@SuppressWarnings("serial")
public class ServiceException extends RuntimeException {

    /**
     * Create a new ServiceException.
     * 
     */
    public ServiceException() {}

    /**
     * Create a new ServiceException.
     * 
     * @param arg0
     */
    public ServiceException(String arg0) {
	super(arg0);
    }

    /**
     * Create a new ServiceException.
     * 
     * @param arg0
     */
    public ServiceException(Throwable arg0) {
	super(arg0);
    }

    /**
     * Create a new ServiceException.
     * 
     * @param arg0
     * @param arg1
     */
    public ServiceException(String arg0, Throwable arg1) {
	super(arg0, arg1);
    }

}
