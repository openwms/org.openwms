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
package org.openwms.common.comm.api;

import org.openwms.common.comm.CommonMessage;

/**
 * A RespondingServiceActivator delegates incoming messages to an Application Service and response to the incoming message.
 * 
 * @param <T>
 *            Type of incoming message that is being processed
 * @param <U>
 *            Type of outgoing message that is returned
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @since 0.2
 */
public interface RespondingServiceActivator<T extends CommonMessage, U extends CommonMessage> extends
        CustomServiceActivator {

    /**
     * Wake up a service, processor or bean an that accepts incoming messages of type <tt>T</tt> and returns messages of type <tt>U</tt>.
     * 
     * @param message
     *            The message to forward
     * @return The response returned from the service
     */
    U wakeUp(T message);
}
