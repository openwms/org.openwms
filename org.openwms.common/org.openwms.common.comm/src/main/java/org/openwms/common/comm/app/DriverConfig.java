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
package org.openwms.common.comm.app;

import java.util.concurrent.Executors;

import org.openwms.common.comm.tcp.CustomTcpMessageMapper;
import org.openwms.common.comm.tcp.OSIPTelegramSerializer;
import org.openwms.common.comm.transformer.tcp.HeaderAppendingTransformer;
import org.openwms.common.comm.transformer.tcp.TelegramTransformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpMessageMapper;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.integration.ip.tcp.serializer.ByteArrayCrLfSerializer;
import org.springframework.integration.support.converter.MapMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.ByteArrayMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * A DriverConfig.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Configuration
class DriverConfig {

    public
    @LoadBalanced
    @Bean
    RestTemplate aLoadBalanced() {
        return new RestTemplate();
    }

    @Bean
    MapMessageConverter mapMessageConverter() {
        MapMessageConverter result = new MapMessageConverter();
        result.setHeaderNames("SYNC_FIELD", "SENDER", "MSG_LENGTH", "SEQUENCENO", "RECEIVER", "ip_connectionId");
        return result;
    }

    @Bean
    TcpMessageMapper customTcpMessageMapper(ByteArrayMessageConverter byteArrayMessageConverter, MapMessageConverter mapMessageConverter) {
        return new CustomTcpMessageMapper(byteArrayMessageConverter, mapMessageConverter);
    }

    /*~ ---------------- TCP/IP stuff ------------- */
    @Bean
    AbstractConnectionFactory tcpConnectionFactory(@Value("${driver.server.port}") int port,
                                                   @Value("${driver.server.so-timeout}") int soTimeout,
                                                   @Value("${driver.server.so-receive-buffer-size}") int soReceiveBufferSize,
                                                   @Value("${driver.server.so-send-buffer-size}") int soSendBufferSize,
                                                   TcpMessageMapper customTcpMessageMapper) {
        TcpNetServerConnectionFactory connectionFactory = new TcpNetServerConnectionFactory(port);
        connectionFactory.setSoTimeout(soTimeout);
        connectionFactory.setSerializer(telegramSerializer());
        connectionFactory.setDeserializer(byteArraySerializer());
        connectionFactory.setSoReceiveBufferSize(soReceiveBufferSize);
        connectionFactory.setSoSendBufferSize(soSendBufferSize);
        connectionFactory.setMapper(customTcpMessageMapper);
        return connectionFactory;
    }

    @Bean
    TcpInboundGateway inboundAdapter(AbstractConnectionFactory tcpConnectionFactory) {
        TcpInboundGateway gate = new TcpInboundGateway();
        gate.setConnectionFactory(tcpConnectionFactory);
        gate.setRequestChannel(inboundChannel());
        gate.setReplyChannel(enrichedOutboundChannel());
        return gate;
    }

    /*~ --------------- MessageChannels ------------ */
    @Bean
    MessageChannel commonExceptionChannel() {
        return MessageChannels.executor(Executors.newCachedThreadPool()).get();
    }

    @Bean
    MessageChannel inboundChannel() {
        return MessageChannels.executor(Executors.newCachedThreadPool()).get();
    }

    @Bean
    MessageChannel outboundChannel() {
        return MessageChannels.executor(Executors.newCachedThreadPool()).get();
    }

    @Bean
    MessageChannel enrichedOutboundChannel() {
        return MessageChannels.executor(Executors.newCachedThreadPool()).get();
    }
    /*~ --------- Serializer / Deserializer -------- */
    @Bean
    ByteArrayCrLfSerializer byteArraySerializer() {
        return new ByteArrayCrLfSerializer();
    }

    @Bean
    OSIPTelegramSerializer telegramSerializer() {
        return new OSIPTelegramSerializer();
    }

    /*~ ----------------   Converter---------------- */
    @Bean
    ByteArrayMessageConverter byteArrayMessageConverter() {
        return new ByteArrayMessageConverter();
    }

    /*~ -------------------- Flows ----------------- */
    @Bean
    IntegrationFlow inboundFlow(TelegramTransformer telegramTransformer) {
        return IntegrationFlows.from("inboundChannel")
                .transform(telegramTransformer)
                .channel("transformerOutputChannel")
                .route("messageRouter")
                .get();
    }

    @Bean
    IntegrationFlow outboundFlow(HeaderAppendingTransformer headerAppendingTransformer) {
        return IntegrationFlows.from("outboundChannel")
                .transform(headerAppendingTransformer)
                .channel("enrichedOutboundChannel")
                .get();
    }
}
