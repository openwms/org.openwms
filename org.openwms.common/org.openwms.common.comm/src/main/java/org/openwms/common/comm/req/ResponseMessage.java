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
package org.openwms.common.comm.req;

import java.io.Serializable;

import org.openwms.common.comm.ParserUtils;
import org.openwms.common.comm.Payload;

/**
 * A ResponseMessage on {@link RequestMessage}s.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public class ResponseMessage extends Payload implements Serializable {

    /** Message identifier {@value} . */
    public static final String IDENTIFIER = "RES_";

    private String barcode;
    private String actualLocation;
    private String targetLocation;
    private String targetLocationGroup;

    private ResponseMessage(Builder builder) {
        barcode = builder.barcode;
        actualLocation = builder.actualLocation;
        targetLocation = builder.targetLocation;
        targetLocationGroup = builder.targetLocationGroup;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessageIdentifier() {
        return IDENTIFIER;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getActualLocation() {
        return actualLocation;
    }

    public String getTargetLocation() {
        return targetLocation;
    }

    public String getTargetLocationGroup() {
        return targetLocationGroup;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString() + IDENTIFIER + getErrorCode() + ParserUtils.asString(super.getCreated());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWithoutReply() {
        return true;
    }

    @Override
    public String asString() {
        return IDENTIFIER + barcode + actualLocation + targetLocation + targetLocationGroup;
    }

    /**
     * {@code ResponseMessage} builder static inner class.
     */
    public static final class Builder {

        private String barcode;
        private String actualLocation;
        private String targetLocation;
        private String targetLocationGroup;

        public Builder() {
        }

        /**
         * Sets the {@code barcode} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code barcode} to set
         * @return a reference to this Builder
         */
        public Builder withBarcode(String val) {
            barcode = val;
            return this;
        }

        /**
         * Sets the {@code actualLocation} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code actualLocation} to set
         * @return a reference to this Builder
         */
        public Builder withActualLocation(String val) {
            actualLocation = val;
            return this;
        }

        /**
         * Sets the {@code targetLocation} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code targetLocation} to set
         * @return a reference to this Builder
         */
        public Builder withTargetLocation(String val) {
            targetLocation = val;
            return this;
        }

        /**
         * Sets the {@code targetLocationGroup} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code targetLocationGroup} to set
         * @return a reference to this Builder
         */
        public Builder withTargetLocationGroup(String val) {
            targetLocationGroup = val;
            return this;
        }

        /**
         * Returns a {@code ResponseMessage} built from the parameters previously set.
         *
         * @return a {@code ResponseMessage} built with parameters of this {@code ResponseMessage.Builder}
         */
        public ResponseMessage build() {
            return new ResponseMessage(this);
        }
    }
}
