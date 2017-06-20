/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.core.http;

import org.springframework.http.HttpStatus;

/**
 * A HttpBusinessException encapsulates well-known BusinessExceptions into exceptions particular to http translation.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
public class HttpBusinessException extends RuntimeException {

    private static final long serialVersionUID = -8367065455583950981L;

    private HttpStatus httpStatus;

    /**
     * Create a new HttpBusinessException.
     * 
     * @param pHttpStatus
     */
    public HttpBusinessException(HttpStatus pHttpStatus) {
        super();
        this.httpStatus = pHttpStatus;
    }

    /**
     * Create a new HttpBusinessException.
     * 
     * @param pMessage
     * @param pHttpStatus
     */
    public HttpBusinessException(String pMessage, HttpStatus pHttpStatus) {
        super(pMessage);
        this.httpStatus = pHttpStatus;
    }

    /**
     * Create a new HttpBusinessException.
     * 
     * @param pCause
     * @param pHttpStatus
     */
    public HttpBusinessException(Throwable pCause, HttpStatus pHttpStatus) {
        super(pCause);
        this.httpStatus = pHttpStatus;
    }

    /**
     * Get the httpStatus.
     * 
     * @return the httpStatus.
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
