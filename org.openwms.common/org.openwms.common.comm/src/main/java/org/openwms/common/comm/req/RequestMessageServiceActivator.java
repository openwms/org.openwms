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

import java.util.function.Function;

import org.openwms.common.comm.CommConstants;
import org.openwms.common.comm.api.NotRespondingServiceActivator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageChannel;

/**
 * A RequestMessageServiceActivator takes incoming {@link RequestMessage}s and delegates them to an application POJO.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@MessageEndpoint("requestmessageServiceActivator")
class RequestMessageServiceActivator implements NotRespondingServiceActivator<RequestMessage> {

    /** The name of the MessageChannel used as input-channel of this message processor. */
    static final String INPUT_CHANNEL_NAME = RequestMessage.IDENTIFIER + CommConstants.CHANNEL_SUFFIX;

    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private Function<RequestMessage, Void> handler;

    /**
     * {@inheritDoc}
     */
    @Override
    @ServiceActivator(inputChannel = INPUT_CHANNEL_NAME, outputChannel = "outboundChannel")
    public void wakeUp(RequestMessage message) {
        handler.apply(message);
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
