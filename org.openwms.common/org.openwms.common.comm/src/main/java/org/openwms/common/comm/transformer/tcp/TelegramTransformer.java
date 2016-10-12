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
package org.openwms.common.comm.transformer.tcp;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openwms.common.comm.MessageMismatchException;
import org.openwms.common.comm.Payload;
import org.openwms.common.comm.api.MessageMapper;
import org.openwms.common.comm.tcp.TCPCommConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;

/**
 * A CommonMessageTransformer transforms incoming OSIP telegram structures to {@link Payload}s.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@MessageEndpoint("telegramTransformer")
public class TelegramTransformer<T extends Payload> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramTransformer.class);
    @Autowired
    private List<MessageMapper<T>> mappers;
    private final Map<String, MessageMapper<T>> mappersMap = new HashMap<>();

    /**
     * Do this once to query a Map not a List.
     */
    @PostConstruct
    void onPostConstruct() {
        for (MessageMapper<T> mapper : mappers) {
            mappersMap.put(mapper.forType(), mapper);
        }
    }

    /**
     * Transformer method to transform a telegram String {@code telegram} into a {@link Payload}.
     *
     * @param telegram The incoming telegram String
     * @return The {@link Payload} is transformable
     * @throws MessageMismatchException if no appropriate type was found.
     */
    @Transformer
    public Message<T> transform(String telegram, @Headers Map<String, Object> headers) {
        if (telegram == null || telegram.isEmpty()) {
            LOGGER.debug("Received telegram was null or of length == 0, just skip");
            return null;
        }
        MessageMapper<T> mapper = mappersMap.get(TCPCommConstants.getTelegramType(telegram));
        if (mapper == null) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Not mapper found for telegram type " + TCPCommConstants.getTelegramType(telegram));
            }
            throw new MessageMismatchException("Not mapper found for telegram type "
                    + TCPCommConstants.getTelegramType(telegram));
        }
        return mapper.mapTo(telegram, headers);
    }
}
