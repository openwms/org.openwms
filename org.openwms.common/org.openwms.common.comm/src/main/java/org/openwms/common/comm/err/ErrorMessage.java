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

import static org.openwms.common.comm.CommConstants.asDate;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import org.openwms.common.comm.CommConstants;
import org.openwms.common.comm.Payload;

/**
 * An ErrorMessage signals any error or failure situation from an external system and to external systems.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public class ErrorMessage extends Payload implements Serializable {

    /** Message identifier {@value} . */
    public static final String IDENTIFIER = "ERR_";

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessageIdentifier() {
        return IDENTIFIER;
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
         */
        public Builder() {
            this.message = new ErrorMessage();
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
         * Add the date of creation in an expected format as defined in {@link CommConstants#DATE_FORMAT_PATTERN}.
         *
         * @param createDate
         *            The creation date as String
         * @return The builder
         */
        public Builder withCreateDate(String createDate) throws ParseException {
            message.setCreated(asDate(createDate));
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
    public String asString() {
        return IDENTIFIER + getErrorCode() +
                CommConstants.asString(super.getCreated());
    }
}
