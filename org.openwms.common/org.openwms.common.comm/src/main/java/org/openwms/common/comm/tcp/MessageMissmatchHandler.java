/*
 * openwms.org, the Open Warehouse Management System.
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.common.comm.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

/**
 * A MessageMissmatchHandler cares about incoming error telegrams on a defined
 * error channel with name {@value ERROR_CHANNEL_ID}.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
@Component
public class MessageMissmatchHandler {

    private static final String ERROR_CHANNEL_ID = "errExceptionChannel";
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageMissmatchHandler.class);

    /**
     * Current implementation just logs error messages.
     * 
     * @param telegram
     *            The error telegram
     */
    @ServiceActivator(inputChannel = ERROR_CHANNEL_ID)
    public void handle(String telegram) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Invalid telegram : " + telegram);
        }
    }
}
