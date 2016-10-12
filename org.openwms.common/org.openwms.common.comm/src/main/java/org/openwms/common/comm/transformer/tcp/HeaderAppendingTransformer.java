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
package org.openwms.common.comm.transformer.tcp;

import org.openwms.common.comm.CommHeader;
import org.openwms.common.comm.Payload;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageHeaderAccessor;

/**
 * A HeaderAppendingTransformer.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@MessageEndpoint
public class HeaderAppendingTransformer {

    @Transformer
    public Message<Payload> transform(Message<Payload> msg) {
        MessageHeaderAccessor mha = new MessageHeaderAccessor();
        mha.copyHeaders(msg.getHeaders());
        mha.setHeader(CommHeader.SYNC_FIELD_NAME, msg.getHeaders().get(CommHeader.SYNC_FIELD_NAME));
        mha.setHeader(CommHeader.MSG_LENGTH_FIELD_NAME, headerLength(msg.getHeaders()) + msg.getPayload().asString().length());
        mha.setHeader(CommHeader.SENDER_FIELD_NAME, msg.getHeaders().get(CommHeader.RECEIVER_FIELD_NAME));
        mha.setHeader(CommHeader.RECEIVER_FIELD_NAME, msg.getHeaders().get(CommHeader.SENDER_FIELD_NAME));
        mha.setHeader(CommHeader.SEQUENCE_FIELD_NAME, Integer.parseInt(String.valueOf(msg.getHeaders().get(CommHeader.SEQUENCE_FIELD_NAME))) + 1);
        return org.springframework.messaging.support.MessageBuilder.withPayload(msg.getPayload()).setHeaders(mha).build();
    }

    private int headerLength(MessageHeaders h) {
        return String.valueOf(h.get(CommHeader.SYNC_FIELD_NAME)).length() +
                String.valueOf( h.get(CommHeader.MSG_LENGTH_FIELD_NAME)).length() +
                String.valueOf( h.get(CommHeader.SENDER_FIELD_NAME)).length() +
                String.valueOf( h.get(CommHeader.RECEIVER_FIELD_NAME)).length() +
                String.valueOf( h.get(CommHeader.SEQUENCE_FIELD_NAME)).length();
    }
}