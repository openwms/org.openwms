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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.ip.tcp.connection.TcpConnection;
import org.springframework.integration.ip.tcp.connection.TcpMessageMapper;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.util.Assert;

/**
 * A CustomTcpMessageMapper.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
class CustomTcpMessageMapper extends TcpMessageMapper {

    private final MessageConverter inboundMessageConverter;
    private final MessageConverter outboundMessageConverter;

    private static final Logger LOGGER = LoggerFactory.getLogger("CORE_INTEGRATION_MESSAGING");

    public CustomTcpMessageMapper(MessageConverter inboundMessageConverter, MessageConverter outboundMessageConverter) {
        Assert.notNull(inboundMessageConverter, "'inboundMessageConverter' must not be null");
        Assert.notNull(outboundMessageConverter, "'outboundMessageConverter' must not be null");
        this.inboundMessageConverter = inboundMessageConverter;
        this.outboundMessageConverter = outboundMessageConverter;
    }

    @Override
    public Message<?> toMessage(TcpConnection connection) throws Exception {
        Object data = connection.getPayload();
        LOGGER.debug("Incoming:" + data);
        if (data != null) {
            Message<?> message = this.inboundMessageConverter.toMessage(data, null);
            AbstractIntegrationMessageBuilder<?> messageBuilder = this.getMessageBuilderFactory().fromMessage(message);
            this.addStandardHeaders(connection, messageBuilder);
            this.addCustomHeaders(connection, messageBuilder);
            return messageBuilder.build();
        } else {
            if (logger.isWarnEnabled()) {
                logger.warn("Null payload from connection " + connection.getConnectionId());
            }
            return null;
        }
    }

    @Override
    public Object fromMessage(Message<?> message) throws Exception {
        Object data = this.outboundMessageConverter.fromMessage(message, Object.class);
        LOGGER.debug("Outgoing:" + data);
        return data;
    }
}
