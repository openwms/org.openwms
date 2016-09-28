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

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.openwms.common.comm.CommonHeader;
import org.openwms.common.comm.CommonMessage;

/**
 * A SystemUpdateMessage.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public class SystemUpdateMessage extends CommonMessage implements Serializable {

    /** Message identifier {@value} . */
    public static final String IDENTIFIER = "SYSU";
    private final String identifier = IDENTIFIER;

    private String locationGroupName;

    /**
     * Create a new SystemUpdateMessage.
     *
     * @param header The message header
     */
    public SystemUpdateMessage(CommonHeader header) {
        super(header);
    }

    private SystemUpdateMessage(CommonHeader header, Builder builder) {
        super(header);
        locationGroupName = builder.locationGroupName;
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
        private CommonHeader header;

        public Builder(CommonHeader header) {
            this.header = header;
        }

        /**
         * Sets the {@code locationGroupName} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param locationGroupName the {@code locationGroupName} to set
         * @return a reference to this Builder
         */
        public Builder withLocationGroupName(String locationGroupName) {
            this.locationGroupName = locationGroupName;
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
         * Add the date of creation.
         *
         * @param createDate
         *            The creation date
         * @return The builder
         */
        public Builder withCreateDate(Date createDate) {
            this.created = createDate;
            return this;
        }


        /**
         * Returns a {@code SystemUpdateMessage} built from the parameters previously set.
         *
         * @return a {@code SystemUpdateMessage} built with parameters of this {@code SystemUpdateMessage.Builder}
         */
        public SystemUpdateMessage build() {
            SystemUpdateMessage res = new SystemUpdateMessage(this.header, this);
            res.setErrorCode(this.errorCode);
            res.setCreated(this.created);
            return res;
        }
    }

    /**
     * {@inheritDoc}
     *
     * Use all fields.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SystemUpdateMessage that = (SystemUpdateMessage) o;
        return Objects.equals(identifier, that.identifier) &&
                Objects.equals(locationGroupName, that.locationGroupName);
    }

    /**
     * {@inheritDoc}
     *
     * Use all fields.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), identifier, locationGroupName);
    }

    @Override
    public String toString() {
        return "SystemUpdateMessage{" +
                "identifier='" + identifier + '\'' +
                ", locationGroup=" + locationGroupName +
                "} with " + super.toString();
    }
}
