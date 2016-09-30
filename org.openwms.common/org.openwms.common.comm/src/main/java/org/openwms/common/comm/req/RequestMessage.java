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

import static org.openwms.common.comm.CommConstants.asDate;

import java.text.ParseException;

import org.openwms.common.comm.CommConstants;
import org.openwms.common.comm.CommonHeader;
import org.openwms.common.comm.CommonMessage;
import org.openwms.common.comm.req.spi.RequestFieldLengthProvider;

/**
 * A RequestMessage requests an order for a TransportUnit with id <tt>Barcode</tt> on a particular location <tt>actualLocation</tt>.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @since 0.2
 */
public class RequestMessage extends CommonMessage {

    /** Message identifier {@value} . */
    public static final String IDENTIFIER = "REQ_";
    private final String identifier = IDENTIFIER;

    private String barcode;
    private String actualLocation;
    private String targetLocation;

    /**
     * Create a new RequestMessage.
     * 
     * @param header
     *            The message header
     */
    public RequestMessage(CommonHeader header) {
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
     * A Builder.
     * 
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision: $
     * @since 0.1
     */
    public static class Builder {

        private final RequestMessage requestMessage;
        private final RequestFieldLengthProvider provider;

        /**
         * Create a new RequestMessage.Builder.
         * 
         * @param header
         *            The message header
         */
        public Builder(RequestFieldLengthProvider provider, CommonHeader header) {
            this.provider = provider;
            this.requestMessage = new RequestMessage(header);
        }

        /**
         * Add an {@code Barcode} to the message.
         * 
         * @param barcode
         *            The barcode
         * @return The builder
         */
        public Builder withBarcode(String barcode) {
            requestMessage.barcode = barcode;
            return this;
        }

        /**
         * Add an actual {@code Location} by the given unique {@code LocationPk} in an expected format like {@literal AAAAAAA/BBBBBB/...}.
         * Where the number of digits each coordinate has and the number of coordinates at all is defined by the {@code RequestFieldLengthProvider}.
         * 
         * @param actualLocation
         *            The String representation of {@code LocationPK} of the actual location
         * @return The builder
         */
        public Builder withActualLocation(String actualLocation) {
            requestMessage.actualLocation = String.join("/",
                    actualLocation.split("(?<=\\G.{" + provider.locationIdLength() / provider.noLocationIdFields() + "})"));
            return this;
        }

        /**
         * Add an target {@code Location} by the given unique {@code LocationPk} in an expected format like {@literal AAAAAAA/BBBBBB/...}.
         * Where the number of digits each coordinate has and the number of coordinates at all is defined by the {@code RequestFieldLengthProvider}.
         * 
         * @param targetLocation
         *            The String representation of {@code LocationPK} of the target location
         * @return The builder
         */
        public Builder withTargetLocation(String targetLocation) {
            requestMessage.targetLocation = String.join("/",
                    targetLocation.split("(?<=\\G.{" + provider.locationIdLength() / provider.noLocationIdFields() + "})"));
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
            requestMessage.setErrorCode(errorCode);
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
            requestMessage.setCreated(asDate(createDate));
            return this;
        }

        /**
         * Finally build the message.
         * 
         * @return The completed message
         */
        public RequestMessage build() {
            return requestMessage;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(IDENTIFIER).append(this.barcode).append(this.actualLocation).append(this.targetLocation)
                .append(getErrorCode()).append(CommConstants.asString(super.getCreated()));
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWithoutReply() {
        return false;
    }
}
