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
package org.openwms.tms.exception;

import java.io.Serializable;

import org.ameba.exception.BehaviorAwareException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A StateChangeException signals that the request to change the state of a {@code TransportOrder} was not allowed.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @since 1.0
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StateChangeException extends BehaviorAwareException {

    /**
     * {@inheritDoc}
     */
    public StateChangeException(String s) {
        super(s);
    }

    /**
     * {@inheritDoc}
     *
     * @param message
     * @param msgKey
     * @param data
     */
    public StateChangeException(String message, String msgKey, Serializable... data) {
        super(message, msgKey, data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
