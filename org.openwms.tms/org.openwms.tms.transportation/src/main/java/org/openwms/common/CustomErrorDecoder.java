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
package org.openwms.common;

import java.io.IOException;
import java.text.ParseException;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.ameba.exception.NotFoundException;
import org.springframework.stereotype.Component;

/**
 * A CustomErrorDecoder.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@Component
public class CustomErrorDecoder implements ErrorDecoder {

    /**
     * {@inheritDoc}
     */
    @Override
    public Exception decode(String methodKey, Response response) {

        try {
            if (response.status() == 404) {
                String d = Util.toString(response.body().asReader());
                org.ameba.http.Response r = org.ameba.http.Response.parse(d);
                return new NotFoundException(r.getMessage(), r.getMessageKey(), r.getObj());
            }
        } catch (IOException | ParseException e) {
            return new IllegalArgumentException(e);
        }
        return null;//new IllegalArgumentException(methodKey);
    }
}
