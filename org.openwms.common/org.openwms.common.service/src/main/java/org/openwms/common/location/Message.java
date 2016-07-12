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
package org.openwms.common.location;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

import org.ameba.integration.jpa.BaseEntity;

/**
 * A Message can be used to store useful information about errors or events.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 0.1
 */
@Entity
@Table(name = "COR_MESSAGE")
public class Message extends BaseEntity implements Serializable {

    /** Message number. */
    @Column(name = "C_MESSAGE_NO")
    private int messageNo;

    /** Message description text. */
    @Column(name = "C_MESSAGE_TEXT")
    private String messageText;

    /*~ ----------------------------- constructors ------------------- */
    /**
     * Dear JPA...
     */
    protected Message() {
    }

    /**
     * Create a new {@code Message} with message number and message text.
     * 
     * @param messageNo
     *            The message number
     * @param messageText
     *            The message text
     */
    public Message(int messageNo, String messageText) {
        this.messageNo = messageNo;
        this.messageText = messageText;
    }

    private Message(Builder builder) {
        messageNo = builder.messageNo;
        messageText = builder.messageText;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /*~ ----------------------------- methods ------------------- */
    /**
     * Return the message number.
     * 
     * @return The message number
     */
    public int getMessageNo() {
        return messageNo;
    }

    /**
     * Return the message text.
     * 
     * @return The message text
     */
    public String getMessageText() {
        return messageText;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return messageNo == message.messageNo &&
                Objects.equals(messageText, message.messageText);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(messageNo, messageText);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return messageNo+"::"+messageText;
    }


    /**
     * {@code Message} builder static inner class.
     */
    public static final class Builder {

        private int messageNo;
        private String messageText;

        private Builder() {
        }

        /**
         * Sets the {@code messageNo} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code messageNo} to set
         * @return a reference to this Builder
         */
        public Builder messageNo(int val) {
            messageNo = val;
            return this;
        }

        /**
         * Sets the {@code messageText} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code messageText} to set
         * @return a reference to this Builder
         */
        public Builder messageText(String val) {
            messageText = val;
            return this;
        }

        /**
         * Returns a {@code Message} built from the parameters previously set.
         *
         * @return a {@code Message} built with parameters of this {@code Message.Builder}
         */
        public Message build() {
            return new Message(this);
        }
    }
}