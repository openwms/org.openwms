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

import org.openwms.common.comm.api.CommonHeader;
import org.openwms.common.comm.api.CommonMessage;

/**
 * A TCPCommConstants.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
public final class TCPCommConstants {

    /** Maximum telegram length. */
    public static final int MAX_TELEGRAM_LENGTH = 99999;

    /**
     * Hide Constructor.
     */
    private TCPCommConstants() {}

    /**
     * A CommonMessage is able to define the type of message from the telegram
     * String. Currently the type identifier starts directly after the header
     * and has a length of 4 characters.
     * 
     * @param telegram
     *            The telegram String to resolve the type for
     * @return The telegram type as case-insensitive String
     */
    public static String getTelegramType(String telegram) {
        short headerLength = CommonHeader.getHeaderLength();
        return telegram.substring(headerLength, headerLength + CommonMessage.getMessageIdentifierLength());
    }
}
