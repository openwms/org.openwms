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
package org.openwms.common.comm.api;

import java.util.Map;

import org.openwms.common.comm.Payload;
import org.springframework.messaging.Message;

/**
 * A MessageMapper is able to map from a String telegram to a {@link Payload}.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public interface MessageMapper<T> {

    /**
     * Investigate the telegram String {@code telegram} and retrieve from the telegram type a subtype of {@link
     * Payload CommonMessage}. Implementations probably throw some kind of RuntimeExceptions if no telegram
     * type was found. An implementation can expect that the caller has checked the telegram length.
     *
     * @param telegram The telegram String to investigate
     * @param headers A map of the underlying protocol headers
     * @return The mapped CommonMessage
     */
    Message<T> mapTo(String telegram, Map<String, Object> headers);

    /**
     * Return the telegram type, this mapper is responsible for.
     *
     * @return the telegram type as String
     */
    String forType();
}
