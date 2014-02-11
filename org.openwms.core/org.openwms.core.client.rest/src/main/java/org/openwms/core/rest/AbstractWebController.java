/*
 * openwms.org, the Open Warehouse Management System.
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.core.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * A AbstractWebController.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
public abstract class AbstractWebController {

    @Autowired
    private MessageSource messageSource;

    /**
     * All general exceptions thrown by services are caught here and translated into http conform responses with a status code {@value
     * HttpStatus.INTERNAL_SERVER_ERROR}.
     * 
     * @param ex
     *            The exception occurred
     * @return A response object that wraps the server result
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseVO> handleIOException(Exception ex) {
        if (ex.getClass().equals(HttpBusinessException.class)) {
            HttpBusinessException hbe = (HttpBusinessException) ex;
            return new ResponseEntity<>(new ResponseVO(ex.getMessage(), hbe.getHttpStatus()), hbe.getHttpStatus());
        }
        return new ResponseEntity<>(new ResponseVO(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Get the messageSource.
     * 
     * @return the messageSource.
     */
    protected String translate(String key, Object... objects) {
        return messageSource.getMessage(key, objects, null);
    }
}
