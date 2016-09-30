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
package org.openwms.common.comm.err.tcp;

import static org.openwms.common.comm.CommonHeader.LENGTH_HEADER;
import static org.openwms.common.comm.CommonMessage.DATE_LENGTH;
import static org.openwms.common.comm.CommonMessage.ERROR_CODE_LENGTH;

import java.text.ParseException;

import org.openwms.common.comm.api.MessageMapper;
import org.openwms.common.comm.err.ErrorMessage;
import org.openwms.common.comm.exception.MessageMissmatchException;
import org.openwms.common.comm.util.CommonMessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * An ErrorTelegramMapper transforms an incoming OSIP telegram string, that is expected to be an error telegram, into an ErrorMessage.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
@Component
public class ErrorTelegramMapper implements MessageMapper<ErrorMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorTelegramMapper.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public ErrorMessage mapTo(String telegram) {
        int startPayload = LENGTH_HEADER + forType().length();
        int startCreateDate = startPayload + ERROR_CODE_LENGTH;
        try {
            return new ErrorMessage.Builder(CommonMessageFactory.createHeader(telegram))
                    .withErrorCode(telegram.substring(startPayload, startCreateDate))
                    .withCreateDate(telegram.substring(startCreateDate, startCreateDate + DATE_LENGTH)).build();
        } catch (ParseException e) {
            throw new MessageMissmatchException(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String forType() {
        return ErrorMessage.IDENTIFIER;
    }
}
