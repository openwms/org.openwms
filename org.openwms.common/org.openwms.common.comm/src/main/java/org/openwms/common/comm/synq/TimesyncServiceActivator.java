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
package org.openwms.common.comm.synq;

import java.util.function.Function;

import org.openwms.common.comm.CommConstants;
import org.openwms.common.comm.api.RespondingServiceActivator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

/**
 * A TimesyncServiceActivator is activated on SYNC telegrams and delegates
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@MessageEndpoint("timesyncServiceActivator")
class TimesyncServiceActivator implements RespondingServiceActivator<TimesyncRequest, TimesyncResponse> {

    /** The name of the MessageChannel used as input-channel of this message processor. */
    public static final String INPUT_CHANNEL_NAME = TimesyncRequest.IDENTIFIER + CommConstants.CHANNEL_SUFFIX;

    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private Function<Message<TimesyncRequest>, Message<TimesyncResponse>> handler;

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

    /**
     * {@inheritDoc}
     */
    @Override
    @ServiceActivator(inputChannel = INPUT_CHANNEL_NAME, outputChannel = "outboundChannel")
    public Message<TimesyncResponse> wakeUp(Message<TimesyncRequest> message) {
        return handler.apply(message);
    }
}
