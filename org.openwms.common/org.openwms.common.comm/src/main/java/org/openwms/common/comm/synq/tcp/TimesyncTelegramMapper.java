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
package org.openwms.common.comm.synq.tcp;

import static org.openwms.common.comm.CommConstants.asDate;
import static org.openwms.common.comm.CommHeader.LENGTH_HEADER;
import static org.openwms.common.comm.Payload.DATE_LENGTH;

import java.text.ParseException;
import java.util.Map;

import org.openwms.common.comm.api.MessageMapper;
import org.openwms.common.comm.exception.MessageMismatchException;
import org.openwms.common.comm.synq.TimesyncRequest;
import org.openwms.common.comm.util.CommonMessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

/**
 * A TimesyncTelegramMapper.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Component
class TimesyncTelegramMapper implements MessageMapper<TimesyncRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimesyncTelegramMapper.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Message<TimesyncRequest> mapTo(String telegram, Map<String, Object> headers) {
        LOGGER.debug("Telegram to transform: [{}]", telegram);

        int startSendertime = LENGTH_HEADER + forType().length();
        TimesyncRequest request = new TimesyncRequest();
        try {
            request.setSenderTimer(asDate(telegram.substring(startSendertime, startSendertime + DATE_LENGTH)));
            GenericMessage<TimesyncRequest> result =
                    new GenericMessage<>(request, CommonMessageFactory.createHeaders(telegram, headers));
            LOGGER.debug("Transformed telegram into TimesyncRequest message:" + result);
            return result;
        } catch (ParseException e) {
            throw new MessageMismatchException(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String forType() {
        return TimesyncRequest.IDENTIFIER;
    }
}
