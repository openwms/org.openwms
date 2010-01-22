/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */

package org.openwms.common.integration.exception;

import java.io.Serializable;

/**
 * A TooManyEntriesFoundException.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: $
 * 
 */
public class TooManyEntitiesFoundException extends DataException {

    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 4569016268461941846L;

    /**
     * Create a new TooManyEntriesFoundException.
     * 
     * @param message
     */
    public TooManyEntitiesFoundException(String message) {
        super(message);
    }

    /**
     * Create a new TooManyEntriesFoundException.
     * 
     * @param message
     * @param cause
     */
    public TooManyEntitiesFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a new TooManyEntriesFoundException.
     * 
     * @param id
     *            - Unique id of the Entity class
     */
    public TooManyEntitiesFoundException(Serializable id) {
        super("Persistence layer returned more than expected entities for id: " + id);
    }

}
