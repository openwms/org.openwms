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
package org.openwms.common.comm.req;

import org.openwms.common.comm.CommConstants;
import org.openwms.common.comm.api.RespondingServiceActivator;
import org.openwms.common.comm.req.api.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

/**
 * A RequestMessageServiceActivator takes incoming {@link RequestMessage}s and delegates them to an application POJO.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @since 0.2
 */
@Component
class RequestMessageServiceActivator implements RespondingServiceActivator<RequestMessage, ResponseMessage> {

    /** The name of the MessageChannel used as input-channel of this message processor. */
    public static final String INPUT_CHANNEL_NAME = RequestMessage.IDENTIFIER + CommConstants.CHANNEL_SUFFIX;
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestMessageServiceActivator.class);

    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private RequestHandler handler;

    /**
     * {@inheritDoc}
     */
    @Override
    @ServiceActivator(inputChannel = INPUT_CHANNEL_NAME, outputChannel = "outboundChannel")
    public ResponseMessage wakeUp(RequestMessage message) {
        LOGGER.debug("Message ready to process {}", message);
        return handler.handle(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageChannel getChannel() {
        return ctx.getBean(INPUT_CHANNEL_NAME, MessageChannel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getChannelName() {
        return INPUT_CHANNEL_NAME;
    }
}
