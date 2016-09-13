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
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.openwms.tms.LocationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A RRDecoder.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @since 1.0
 */
@Component
public class RRDecoder implements Decoder {

    @Autowired
    private ObjectMapper objectMapper;
    /**
     * Decodes an http response into an object corresponding to its {@link Method#getGenericReturnType() generic return type}. If you need
     * to wrap exceptions, please do so via {@link DecodeException}.
     *
     * @param response the response to decode
     * @param type {@link Method#getGenericReturnType() generic return type} of the method corresponding to this {@code response}.
     * @return instance of {@code type}
     * @throws IOException will be propagated safely to the caller.
     * @throws DecodeException when decoding failed due to a checked exception besides IOException.
     * @throws FeignException when decoding succeeds, but conveys the operation failed.
     */
    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        if (type.getTypeName().contains(LocationGroup.class.getTypeName())) {
            return objectMapper.readValue(Util.toString(response.body().asReader()), LocationGroup.class);
        }
        if (type.getTypeName().contains(TransportUnit.class.getTypeName())) {
            return objectMapper.readValue(Util.toString(response.body().asReader()), TransportUnit.class);
        }
        return null;
    }
}
