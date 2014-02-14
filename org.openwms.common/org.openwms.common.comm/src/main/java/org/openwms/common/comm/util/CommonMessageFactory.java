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
package org.openwms.common.comm.util;

import org.openwms.common.comm.api.CommonHeader;

/**
 * A CommonMessageFactory.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
public final class CommonMessageFactory {

    /**
     * Hide Constructor.
     */
    private CommonMessageFactory() {}

    /**
     * Create a {@link CommonHeader} from a passed telegram structure.
     * 
     * @param telegram
     *            The telegram
     * @return A {@link CommonHeader} instance
     */
    public static CommonHeader createHeader(String telegram) {
        String sync = telegram.substring(0, CommonHeader.getLengthSyncField());

        int start = sync.length();
        int end = start + CommonHeader.getLengthTelegramLengthField();
        short messageLength = Short.parseShort(telegram.substring(start, end));

        start = end;
        end += CommonHeader.getLengthSenderField();
        String sender = telegram.substring(start, end);

        start = end;
        end += CommonHeader.getLengthReceiverField();
        String receiver = telegram.substring(start, end);

        start = end;
        end += CommonHeader.getLengthSequenceNoField();
        short sequenceNo = Short.parseShort(telegram.substring(start, end));
        return new CommonHeader(sync, messageLength, sender, receiver, sequenceNo);
    }
}
