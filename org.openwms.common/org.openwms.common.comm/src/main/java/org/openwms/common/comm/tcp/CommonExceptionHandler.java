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
package org.openwms.common.comm.tcp;

import org.openwms.common.comm.CommHeader;
import org.openwms.common.comm.CommonMessageFactory;
import org.openwms.common.comm.err.ErrorCodes;
import org.openwms.common.comm.err.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

/**
 * A CommonExceptionHandler.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@MessageEndpoint("errorServiceActivator")
public class CommonExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonExceptionHandler.class);

    /**
     * Reply to incoming OSIP telegrams on the inputChannel with an {@link ErrorMessage} o the outputChannel.
     * 
     * @param telegram
     *            The incoming OSIP telegram
     * @return An {@link ErrorMessage}
     */
    @ServiceActivator(inputChannel = "commonExceptionChannel", outputChannel = "outboundChannel")
    public ErrorMessage handle(String telegram) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Common error: " + telegram);
        }
        CommHeader header = CommonMessageFactory.createHeader(telegram);
        String sender = header.getSender();
        header.setSender(header.getReceiver());
        header.setReceiver(sender);
        return new ErrorMessage.Builder().withErrorCode(ErrorCodes.UNKNOWN_MESSAGE_TYPE).withCreateDate().build();
    }
}
