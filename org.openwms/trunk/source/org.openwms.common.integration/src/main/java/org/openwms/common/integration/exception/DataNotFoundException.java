/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */

package org.openwms.common.integration.exception;

import java.io.Serializable;

/**
 * A DataNotFoundException.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: $
 * 
 */
public class DataNotFoundException extends DataException {

    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 2524686849100170713L;

    /**
     * Create a new DataNotFoundException.
     * 
     * @param message
     * @param cause
     */
    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a new DataNotFoundException.
     * 
     * @param message
     */
    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(Serializable id) {
        super("Entity class not found in persistence layer, id: " + id);
    }

}
