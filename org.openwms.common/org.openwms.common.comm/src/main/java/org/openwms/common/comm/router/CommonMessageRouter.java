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
package org.openwms.common.comm.router;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openwms.common.comm.api.CommConstants;
import org.openwms.common.comm.api.CommonMessage;
import org.openwms.common.comm.api.CustomServiceActivator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Router;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

/**
 * A CommonMessageRouter collects all {@link CustomServiceActivator}s from the ApplicationContext and tries to find a suitable
 * {@link CustomServiceActivator} when an incoming message arrives. If no suitable processor is found, the message will be delegated to the
 * default exception channel.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
@Component
public class CommonMessageRouter {

    @Autowired
    private List<CustomServiceActivator> processors;
    private final Map<String, CustomServiceActivator> processorMap = new HashMap<String, CustomServiceActivator>();

    /**
     * From all existing {@link CustomServiceActivator}s build up a Map with key equals to channelName.
     */
    @PostConstruct
    void onPostConstruct() {
        for (CustomServiceActivator processor : processors) {
            processorMap.put(processor.getChannelName(), processor);
        }
    }

    /**
     * Routing method, tries to map an incoming {@link CommonMessage} to a MessageChannel.
     * 
     * @param message
     *            The message to process
     * @return The MessageChannel where to put the message
     */
    @Router(inputChannel = "transformerOutput", defaultOutputChannel = "commonExceptionChannel")
    public MessageChannel resolve(CommonMessage message) {
        return processorMap.get(message.getMessageIdentifier() + CommConstants.CHANNEL_SUFFIX).getChannel();
    }
}
