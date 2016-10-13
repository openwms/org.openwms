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
package org.openwms.common.comm;

import java.util.HashMap;
import java.util.Map;

import org.springframework.messaging.MessageHeaders;

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
    private CommonMessageFactory() {
    }

    /**
     * Create a {@link CommHeader} from a passed telegram structure.
     *
     * @param telegram The telegram
     * @return A {@link CommHeader} instance
     */
    public static CommHeader createHeader(String telegram) {
        String sync = telegram.substring(0, CommHeader.LENGTH_SYNC_FIELD);

        int start = sync.length();
        int end = start + CommHeader.LENGTH_MESSAGE_LENGTH_FIELD;
        short messageLength = Short.parseShort(telegram.substring(start, end));

        start = end;
        end += CommHeader.LENGTH_SENDER_FIELD;
        String sender = telegram.substring(start, end);

        start = end;
        end += CommHeader.LENGTH_RECEIVER_FIELD;
        String receiver = telegram.substring(start, end);

        start = end;
        end += CommHeader.LENGTH_SEQUENCE_NO_FIELD;
        short sequenceNo = Short.parseShort(telegram.substring(start, end));
        return new CommHeader(sync, messageLength, sender, receiver, sequenceNo);
    }

    public static MessageHeaders createHeaders(String telegram, Map<String, Object> headers) {
        String sync = telegram.substring(0, CommHeader.LENGTH_SYNC_FIELD);

        int start = sync.length();
        int end = start + CommHeader.LENGTH_MESSAGE_LENGTH_FIELD;
        short messageLength = Short.parseShort(telegram.substring(start, end));

        start = end;
        end += CommHeader.LENGTH_SENDER_FIELD;
        String sender = telegram.substring(start, end);

        start = end;
        end += CommHeader.LENGTH_RECEIVER_FIELD;
        String receiver = telegram.substring(start, end);

        start = end;
        end += CommHeader.LENGTH_SEQUENCE_NO_FIELD;
        short sequenceNo = Short.parseShort(telegram.substring(start, end));
        Map<String, Object> h = new HashMap<>(headers);
        h.put(CommHeader.SYNC_FIELD_NAME, sync);
        h.put(CommHeader.MSG_LENGTH_FIELD_NAME, messageLength);
        h.put(CommHeader.SENDER_FIELD_NAME, sender);
        h.put(CommHeader.RECEIVER_FIELD_NAME, receiver);
        h.put(CommHeader.SEQUENCE_FIELD_NAME, sequenceNo);
        return new MessageHeaders(h);
    }
}
