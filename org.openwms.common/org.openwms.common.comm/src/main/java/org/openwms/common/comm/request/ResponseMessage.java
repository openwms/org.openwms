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
package org.openwms.common.comm.request;

import java.io.Serializable;

import org.openwms.common.comm.api.CommConstants;
import org.openwms.common.comm.api.CommonHeader;
import org.openwms.common.comm.api.CommonMessage;

/**
 * A ResponseMessage on <tt>RequestMessage</tt>s.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
public class ResponseMessage extends CommonMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    /** Message identifier {@value} . */
    public static final String IDENTIFIER = "RES_";
    private final String identifier = IDENTIFIER;

    /**
     * Create a new ResponseMessage.
     * 
     * @param header
     *            The message header
     */
    public ResponseMessage(CommonHeader header) {
        super(header);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessageIdentifier() {
        return identifier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(IDENTIFIER).append(getErrorCode()).append(CommConstants.asString(super.getCreated()));
        return CommConstants.padRight(sb.toString(), getHeader().getMessageLength());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWithoutReply() {
        return true;
    }
}
