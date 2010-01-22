/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service.exception;

/**
 * A ServiceException.
 * <p>
 * Is used as an application exception thrown by the service layer. This type of
 * exception is NOT used as an system exception.
 * </p>
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 877 $
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
     *            - Message text
     */
    public ServiceException(String arg0) {
        super(arg0);
    }

    /**
     * Create a new ServiceException.
     * 
     * @param arg0
     *            - Cause exception
     */
    public ServiceException(Throwable arg0) {
        super(arg0);
    }

    /**
     * Create a new ServiceException.
     * 
     * @param arg0
     *            - Message text
     * @param arg1
     *            - Cause exception
     */
    public ServiceException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
