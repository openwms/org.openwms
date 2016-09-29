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

import java.text.ParseException;

import org.openwms.common.comm.CommConstants;
import org.openwms.common.comm.CommonHeader;
import org.openwms.common.comm.CommonMessage;
import org.openwms.common.comm.api.MessageMapper;
import org.openwms.common.comm.exception.MessageMissmatchException;
import org.openwms.common.comm.sysu.SystemUpdateMessage;
import org.openwms.common.comm.sysu.spi.SystemUpdateFieldLengthProvider;
import org.openwms.common.comm.util.CommonMessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
    public SystemUpdateMessage mapTo(String telegram) {
        LOGGER.debug("Telegram to transform: [{}]", telegram);
        if (provider == null) {
            throw new RuntimeException("Telegram handling "+SystemUpdateMessage.IDENTIFIER+" not supported");
        }
        int startLocationGroup = CommonHeader.getHeaderLength() + forType().length();
        int startErrorCode = startLocationGroup + provider.lengthLocationGroupName();
        int startCreateDate = startErrorCode + CommonMessage.getErrorCodeLength();

        SystemUpdateMessage message;
        try {
            message = new SystemUpdateMessage.Builder(CommonMessageFactory.createHeader(telegram))
                    .withLocationGroupName(
                            StringUtils.trimTrailingCharacter(telegram.substring(startLocationGroup, startErrorCode), '_'))
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
        return SystemUpdateMessage.IDENTIFIER;
    }
}
