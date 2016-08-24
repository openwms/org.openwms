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
package org.openwms.common.comm.err;

import java.util.Date;

import org.openwms.common.comm.api.CommConstants;
import org.openwms.common.comm.api.CommonHeader;
import org.openwms.common.comm.api.CommonMessage;

/**
 * An ErrorMessage signals any error or failure situation from an external system and to external systems.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
public class ErrorMessage extends CommonMessage {

    private static final long serialVersionUID = 1L;
    private final String messageIdentifier = IDENTIFIER;
    /** Message identifier {@value} . */
    public static final String IDENTIFIER = "ERR_";

    /**
     * Create a new ErrorMessage.
     * 
     * @param header
     *            The message header
     */
    public ErrorMessage(CommonHeader header) {
        super(header);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessageIdentifier() {
        return messageIdentifier;
    }

    /**
     * A Builder.
     * 
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision: $
     * @since 0.1
     */
    public static class Builder {

        private final ErrorMessage message;

        /**
         * Create a new Builder.
         * 
         * @param header
         *            The message header
         */
        public Builder(CommonHeader header) {
            this.message = new ErrorMessage(header);
        }

        /**
         * Add an error code to the message.
         * 
         * @param errorCode
         *            The error code
         * @return The builder
         */
        public Builder withErrorCode(String errorCode) {
            message.setErrorCode(errorCode);
            return this;
        }

        /**
         * Add a creation Date to the Message.
         * 
         * @param createDate
         *            The date of creation
         * @return The builder
         */
        public Builder withCreateDate(Date createDate) {
            message.setCreated(createDate);
            return this;
        }

        /**
         * Add a new instance of Date to the Message.
         * 
         * @return The builder
         */
        public Builder withCreateDate() {
            message.setCreated(new Date());
            return this;
        }

        /**
         * Build and return the Message.
         * 
         * @return The Message
         */
        public ErrorMessage build() {
            return message;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWithoutReply() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString()).append(IDENTIFIER).append(getErrorCode())
                .append(CommConstants.asString(super.getCreated()));
        return CommConstants.padRight(sb.toString(), getHeader().getMessageLength());
    }
}
