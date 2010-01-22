/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.integration.exception;

/**
 * A DaoException.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 877 $
 */
public class DataException extends RuntimeException {

    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -4896951691234279331L;

    /**
     * Create a new DaoException.
     * 
     */
    public DataException() {
        super();
    }

    /**
     * Create a new DaoException.
     * 
     * @param message
     */
    public DataException(String message) {
        super(message);
    }

    /**
     * Create a new DaoException.
     * 
     * @param cause
     */
    public DataException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a new DaoException.
     * 
     * @param message
     * @param cause
     */
    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

}
