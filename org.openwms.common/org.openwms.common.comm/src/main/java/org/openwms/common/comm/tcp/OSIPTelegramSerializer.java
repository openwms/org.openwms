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

import static org.openwms.common.comm.CommConstants.padLeft;
import static org.openwms.common.comm.CommConstants.padRight;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Map;

import org.openwms.common.comm.CommConstants;
import org.openwms.common.comm.CommHeader;
import org.openwms.common.comm.Payload;
import org.openwms.common.comm.exception.MessageMismatchException;
import org.springframework.core.serializer.Serializer;

/**
 * An OSIPTelegramSerializer is able to read OSIP telegram structures from an InputStream (deserialization) and can also serialize Object
 * structures into OSIP telegrams.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
class OSIPTelegramSerializer implements Serializer<Map<?, ?>> {

    private static final byte[] CRLF = "\r\n".getBytes();

    /**
     * Writes the source object to an output stream using Java Serialization. The source object must implement {@link Serializable}.
     */
    @Override
    public void serialize(Map<?, ?> map, OutputStream outputStream) throws IOException {
        BufferedOutputStream os = new BufferedOutputStream(outputStream);
        Map<String, String> headers = (Map<String, String>) map.get("headers");
        String header = String.valueOf(headers.get(CommHeader.SYNC_FIELD_NAME)) +
                padLeft(String.valueOf(CommConstants.TELEGRAM_LENGTH), CommHeader.LENGTH_MESSAGE_LENGTH_FIELD, "0") +
                String.valueOf(headers.get(CommHeader.SENDER_FIELD_NAME)) +
                String.valueOf(headers.get(CommHeader.RECEIVER_FIELD_NAME) +
                        padLeft(String.valueOf(headers.get(CommHeader.SEQUENCE_FIELD_NAME)),CommHeader.LENGTH_SEQUENCE_NO_FIELD, "0"));
        String s = header + ((Payload) map.get("payload")).asString();
        if (s.length() > CommConstants.TELEGRAM_LENGTH) {
            throw new MessageMismatchException("Defined telegram length exceeded, size is" + s.length());
        }
        os.write(padRight(s, CommConstants.TELEGRAM_LENGTH, CommConstants.TELEGRAM_FILLER_CHARACTER).getBytes(Charset.defaultCharset()));
        os.write(CRLF);
        os.flush();
    }
}
