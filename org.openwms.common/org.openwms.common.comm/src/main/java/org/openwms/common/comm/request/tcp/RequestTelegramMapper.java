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
package org.openwms.common.comm.request.tcp;

import java.text.ParseException;

import org.openwms.common.comm.api.CommConstants;
import org.openwms.common.comm.api.CommonHeader;
import org.openwms.common.comm.api.CommonMessage;
import org.openwms.common.comm.api.MessageMapper;
import org.openwms.common.comm.exception.MessageMissmatchException;
import org.openwms.common.comm.request.RequestMessage;
import org.openwms.common.comm.util.CommonMessageFactory;
import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.values.Barcode;
import org.springframework.stereotype.Component;

/**
 * A RequestTelegramMapper tries to map a telegram String to a {@link RequestMessage}.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
@Component
public class RequestTelegramMapper implements MessageMapper<RequestMessage> {

    /**
     * {@inheritDoc}
     */
    @Override
    public RequestMessage mapTo(String telegram) {
        int startPayload = CommonHeader.getHeaderLength() + forType().length();
        int startActualLocation = startPayload + Barcode.BARCODE_LENGTH;
        int startTargetLocation = startActualLocation + LocationPK.getKeyLength();
        int startErrorCode = startTargetLocation + LocationPK.getKeyLength();
        int startCreateDate = startErrorCode + CommonMessage.getErrorCodeLength();

        RequestMessage message;
        try {
            message = new RequestMessage.Builder(CommonMessageFactory.createHeader(telegram))
                    .withBarcode(new Barcode(telegram.substring(startPayload, startActualLocation)))
                    .withActualLocation(
                            new LocationPK((telegram.substring(startActualLocation, startTargetLocation))
                                    .split("(?<=\\G.{" + LocationPK.getKeyLength() / LocationPK.NUMBER_OF_KEYS + "})")))
                    .withTargetLocation(
                            new LocationPK((telegram.substring(startTargetLocation, startErrorCode)).split("(?<=\\G.{"
                                    + LocationPK.getKeyLength() / LocationPK.NUMBER_OF_KEYS + "})")))

                    .withErrorCode(telegram.substring(startErrorCode, startCreateDate))
                    .withCreateDate(
                            CommConstants.asDate(telegram.substring(startCreateDate,
                                    startCreateDate + CommonMessage.getDateLength()))).build();
            return message;
        } catch (ParseException e) {
            throw new MessageMissmatchException(e.getMessage());
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
