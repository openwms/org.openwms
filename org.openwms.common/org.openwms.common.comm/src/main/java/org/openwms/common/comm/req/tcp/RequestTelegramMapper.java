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
package org.openwms.common.comm.req.tcp;

import static org.openwms.common.comm.CommHeader.LENGTH_HEADER;

import java.text.ParseException;

import org.openwms.common.comm.Payload;
import org.openwms.common.comm.api.MessageMapper;
import org.openwms.common.comm.exception.MessageMismatchException;
import org.openwms.common.comm.req.RequestMessage;
import org.openwms.common.comm.req.spi.RequestFieldLengthProvider;
import org.openwms.common.comm.util.CommonMessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

/**
 * A RequestTelegramMapper tries to map a telegram String to a {@link RequestMessage}.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Component
class RequestTelegramMapper implements MessageMapper<RequestMessage> {

    @Autowired(required = false)
    private RequestFieldLengthProvider provider;

    /**
     * {@inheritDoc}
     */
    @Override
    public Message<RequestMessage> mapTo(String telegram) {
        if (provider == null) {
            throw new RuntimeException("Telegram handling "+ RequestMessage.IDENTIFIER+" not supported");
        }
        int startPayload = LENGTH_HEADER + forType().length();
        int startActualLocation = startPayload + provider.barcodeLength();
        int startTargetLocation = startActualLocation + provider.locationIdLength();
        int startErrorCode = startTargetLocation + provider.locationIdLength();
        int startCreateDate = startErrorCode + Payload.ERROR_CODE_LENGTH;

        RequestMessage message;
        try {
            message = new RequestMessage.Builder(provider)
                    .withBarcode(telegram.substring(startPayload, startActualLocation))
                    .withActualLocation(telegram.substring(startActualLocation, startTargetLocation))
                    .withTargetLocation(telegram.substring(startTargetLocation, startErrorCode))
                    .withErrorCode(telegram.substring(startErrorCode, startCreateDate))
                    .withCreateDate(telegram.substring(startCreateDate, startCreateDate + Payload.DATE_LENGTH)).build();
            return new GenericMessage<>(message, CommonMessageFactory.createHeaders(telegram));
        } catch (ParseException e) {
            throw new MessageMismatchException(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String forType() {
        return RequestMessage.IDENTIFIER;
    }
}
