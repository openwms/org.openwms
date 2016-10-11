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

import static org.openwms.common.comm.CommConstants.asDate;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import org.openwms.common.comm.CommConstants;
import org.openwms.common.comm.Payload;
import org.springframework.util.StringUtils;

/**
 * A SystemUpdateMessage reflects the OSIP SYSU telegram type and is used to change the state of a {@code LocationGroup}.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public class SystemUpdateMessage extends Payload implements Serializable {

    /** Message identifier {@value} . */
    public static final String IDENTIFIER = "SYSU";
    private String locationGroupName;

    private SystemUpdateMessage(Builder builder) {
        locationGroupName = builder.locationGroupName;
    }

    /**
     * Get the name of the LocationGroup.
     *
     * @return The name
     */
    String getLocationGroupName() {
        return locationGroupName;
    }

    /**
     * Subclasses have to return an unique, case-sensitive message identifier.
     *
     * @return The message TYPE field (see OSIP specification)
     */
    @Override
    public String getMessageIdentifier() {
        return IDENTIFIER;
    }

    /**
     * Does this type of message needs to be replied to?
     *
     * @return {@literal true} no reply needed, otherwise {@literal false}
     */
    @Override
    public boolean isWithoutReply() {
        return false;
    }


    /**
     * {@code SystemUpdateMessage} builder static inner class.
     */
    public static final class Builder {

        private String locationGroupName;
        private String errorCode;
        private Date created;

        /**
         * Sets the {@code locationGroupName} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param locationGroupName the {@code locationGroupName} to set
         * @return a reference to this Builder
         */
        public Builder withLocationGroupName(String locationGroupName) {
            this.locationGroupName = StringUtils.trimTrailingCharacter(locationGroupName, CommConstants.LOCGROUP_FILLER_CHARACTER);
            return this;
        }

        /**
         * Add an error code.
         *
         * @param errorCode
         *            The error code
         * @return The builder
         */
        public Builder withErrorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        /**
         * Add the date of creation in an expected format as defined in {@link CommConstants#DATE_FORMAT_PATTERN}.
         *
         * @param createDate
         *            The creation date
         * @return The builder
         */
        public Builder withCreateDate(String createDate) throws ParseException {
            this.created = asDate(createDate);
            return this;
        }


        /**
         * Returns a {@code SystemUpdateMessage} built from the parameters previously set.
         *
         * @return a {@code SystemUpdateMessage} built with parameters of this {@code SystemUpdateMessage.Builder}
         */
        public SystemUpdateMessage build() {
            SystemUpdateMessage res = new SystemUpdateMessage(this);
            res.setErrorCode(this.errorCode);
            res.setCreated(this.created);
            return res;
        }
    }

    @Override
    public String asString() {
        return IDENTIFIER + locationGroupName;
    }

    @Override
    public String toString() {
        return "SystemUpdateMessage{" +
                "identifier='" + IDENTIFIER + '\'' +
                ", locationGroup=" + locationGroupName +
                "} with " + super.toString();
    }
}
