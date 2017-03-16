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
package org.openwms.common.comm.sysu.tcp;

import static org.openwms.common.comm.CommHeader.LENGTH_HEADER;
import static org.openwms.common.comm.Payload.DATE_LENGTH;
import static org.openwms.common.comm.Payload.ERROR_CODE_LENGTH;

import java.text.ParseException;
import java.util.Map;

import org.openwms.common.comm.CommonMessageFactory;
import org.openwms.common.comm.MessageMismatchException;
import org.openwms.common.comm.api.MessageMapper;
import org.openwms.common.comm.sysu.SystemUpdateMessage;
import org.openwms.common.comm.sysu.spi.SystemUpdateFieldLengthProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

/**
 * A SYSUTelegramMapper maps the incoming SYSU telegram String into an object representation.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Component
class SYSUTelegramMapper implements MessageMapper<SystemUpdateMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SYSUTelegramMapper.class);
    @Autowired(required = false)
    private SystemUpdateFieldLengthProvider provider;

    /**
     * {@inheritDoc}
     */
    @Override
    public Message<SystemUpdateMessage> mapTo(String telegram, Map<String, Object> headers) {
        LOGGER.debug("Telegram to transform: [{}]", telegram);
        if (provider == null) {
            throw new RuntimeException("Telegram handling " + SystemUpdateMessage.IDENTIFIER + " not supported");
        }
        int startLocationGroup = LENGTH_HEADER + forType().length();
        int startErrorCode = startLocationGroup + provider.lengthLocationGroupName();
        int startCreateDate = startErrorCode + ERROR_CODE_LENGTH;

        SystemUpdateMessage message;
        try {
            message = new SystemUpdateMessage.Builder()
                    .withLocationGroupName(telegram.substring(startLocationGroup, startErrorCode))
                    .withErrorCode(telegram.substring(startErrorCode, startCreateDate))
                    .withCreateDate(telegram.substring(startCreateDate, startCreateDate + DATE_LENGTH)).build();
            return new GenericMessage<>(message, CommonMessageFactory.createHeaders(telegram, headers));
        } catch (ParseException e) {
            throw new MessageMismatchException(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String forType() {
        return SystemUpdateMessage.IDENTIFIER;
    }
}
