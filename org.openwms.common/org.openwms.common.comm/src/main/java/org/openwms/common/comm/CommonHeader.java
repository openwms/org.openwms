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

import java.io.Serializable;

/**
 * A CommonHeader represents the header part of a CommonMessage.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @since 0.2
 */
public class CommonHeader implements Serializable {

    private String sync;
    private short messageLength;
    private String sender;
    private String receiver;
    private short sequenceNo;

    private static final short LENGTH_SYNC_FIELD = 3;
    private static final short LENGTH_MESSAGE_LENGTH_FIELD = 5;
    private static final short LENGTH_SENDER_FIELD = 5;
    private static final short LENGTH_RECEIVER_FIELD = 5;
    private static final short LENGTH_SEQUENCE_NO_FIELD = 5;

    /**
     * Create a new CommonHeader.
     * 
     * @param sync
     *            The sync field
     * @param messageLength
     *            The length of the message
     * @param sender
     *            The sender identifier
     * @param receiver
     *            The receiver identifier
     * @param sequenceNo
     *            An incremental sequence number
     */
    public CommonHeader(String sync, short messageLength, String sender, String receiver, short sequenceNo) {
        super();
        this.sync = sync;
        this.messageLength = messageLength;
        this.sender = sender;
        this.receiver = receiver;
        this.sequenceNo = sequenceNo;
    }

    /**
     * Create a new CommonHeader.
     *
     * @param commonHeader The header to get the values from.
     */
    public CommonHeader(CommonHeader commonHeader) {
        this(commonHeader.getSync(), commonHeader.getMessageLength(), commonHeader.getSender(), commonHeader.getReceiver(), commonHeader.getSequenceNo());
    }

    /**
     * Create a new CommonHeader.
     * 
     * @param telegram
     *            The telegram String
     */
    // TODO [scherrer] : remove this it is specific to telegrams.
    public CommonHeader(String telegram) {
        this.sync = telegram.substring(0, getLengthSyncField());

        int start = sync.length();
        int end = start + getLengthTelegramLengthField();
        this.messageLength = Short.parseShort(telegram.substring(start, end));

        start = end;
        end += getLengthSenderField();
        this.sender = telegram.substring(start, end);

        start = end;
        end += getLengthReceiverField();
        this.receiver = telegram.substring(start, end);

        start = end;
        end += getLengthSequenceNoField();
        this.sequenceNo = Short.parseShort(telegram.substring(start, end));
    }

    /**
     * Return the number of characters the message header allocates.
     * 
     * @return The header length in characters
     */
    public static final short getHeaderLength() {
        return LENGTH_SYNC_FIELD + LENGTH_MESSAGE_LENGTH_FIELD + LENGTH_RECEIVER_FIELD + LENGTH_SENDER_FIELD
                + LENGTH_SEQUENCE_NO_FIELD;
    }

    /**
     * Get the lengthSyncField.
     * 
     * @return the lengthSyncField.
     */
    public static short getLengthSyncField() {
        return LENGTH_SYNC_FIELD;
    }

    /**
     * Get the lengthTelegramLengthField.
     * 
     * @return the lengthTelegramLengthField.
     */
    public static short getLengthTelegramLengthField() {
        return LENGTH_MESSAGE_LENGTH_FIELD;
    }

    /**
     * Get the lengthReceiverField.
     * 
     * @return the lengthReceiverField.
     */
    public static short getLengthReceiverField() {
        return LENGTH_RECEIVER_FIELD;
    }

    /**
     * Get the lengthSenderField.
     * 
     * @return the lengthSenderField.
     */
    public static short getLengthSenderField() {
        return LENGTH_SENDER_FIELD;
    }

    /**
     * Get the lengthSequenceNoField.
     * 
     * @return the lengthSequenceNoField.
     */
    public static short getLengthSequenceNoField() {
        return LENGTH_SEQUENCE_NO_FIELD;
    }

    /**
     * Get the sync.
     * 
     * @return the sync.
     */
    public String getSync() {
        return sync;
    }

    /**
     * Set the sync.
     * 
     * @param sync
     *            The sync to set.
     */
    public void setSync(String sync) {
        this.sync = sync;
    }

    /**
     * Get the messageLength.
     * 
     * @return the messageLength.
     */
    public short getMessageLength() {
        return messageLength;
    }

    /**
     * Set the messageLength.
     * 
     * @param messageLength
     *            The messageLength to set.
     */
    public void setMessageLength(short messageLength) {
        this.messageLength = messageLength;
    }

    /**
     * Get the sender.
     * 
     * @return the sender.
     */
    public String getSender() {
        return sender;
    }

    /**
     * Set the sender.
     * 
     * @param sender
     *            The sender to set.
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Get the receiver.
     * 
     * @return the receiver.
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Set the receiver.
     * 
     * @param receiver
     *            The receiver to set.
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * Get the sequenceNo.
     * 
     * @return the sequenceNo.
     */
    public short getSequenceNo() {
        return sequenceNo;
    }

    /**
     * Set the sequenceNo.
     * 
     * @param sequenceNo
     *            The sequenceNo to set.
     */
    public void setSequenceNo(short sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((receiver == null) ? 0 : receiver.hashCode());
        result = prime * result + ((sender == null) ? 0 : sender.hashCode());
        result = prime * result + sequenceNo;
        result = prime * result + ((sync == null) ? 0 : sync.hashCode());
        result = prime * result + messageLength;
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CommonHeader other = (CommonHeader) obj;
        if (receiver == null) {
            if (other.receiver != null) {
                return false;
            }
        } else if (!receiver.equals(other.receiver)) {
            return false;
        }
        if (sender == null) {
            if (other.sender != null) {
                return false;
            }
        } else if (!sender.equals(other.sender)) {
            return false;
        }
        if (sequenceNo != other.sequenceNo) {
            return false;
        }
        if (sync == null) {
            if (other.sync != null) {
                return false;
            }
        } else if (!sync.equals(other.sync)) {
            return false;
        }
        if (messageLength != other.messageLength) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CommonHeader{" +
                "sync='" + sync + '\'' +
                ", messageLength=" + messageLength +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", sequenceNo=" + sequenceNo +
                '}';
    }
}
