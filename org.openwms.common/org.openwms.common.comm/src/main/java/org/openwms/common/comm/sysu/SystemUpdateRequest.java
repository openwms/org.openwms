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
package org.openwms.common.comm.sysu;

import org.openwms.common.comm.api.CommonHeader;
import org.openwms.common.comm.api.CommonMessage;

/**
 * A SystemUpdateRequest.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
class SystemUpdateRequest extends CommonMessage {

    /** Message identifier {@value} . */
    public static final String IDENTIFIER = "SYSU";
    private final String identifier = IDENTIFIER;

    /**
     * Create a new CommonMessage.
     *
     * @param header The message header
     */
    public SystemUpdateRequest(CommonHeader header) {
        super(header);
    }

    /**
     * Subclasses have to return an unique, case-sensitive message identifier.
     *
     * @return The message TYPE field (see OSIP specification)
     */
    @Override
    public String getMessageIdentifier() {
        return identifier;
    }

    /**
     * Does this type of message needs to be replied to?
     *
     * @return <code>true</code> no reply needed, otherwise <code>false</code>
     */
    @Override
    public boolean isWithoutReply() {
        return false;
    }
}
