/*
 * openwms.org, the Open Warehouse Management System.
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.common.domain.system;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.openwms.common.domain.AbstractEntity;

/**
 * A Message - Predefined message.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Entity
@Table(name = "APP_MESSAGE")
public class Message extends AbstractEntity implements Serializable {

    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 7836132529431969528L;

    /**
     * Unique technical key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    /**
     * Message number.
     */
    @Column(name = "MESSAGE_NO")
    private int messageNo;

    /**
     * Message description text.
     */
    @Column(name = "MESSAGE_TEXT")
    private String messageText;

    /**
     * Timestamp when the {@link Message} was created.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED")
    private Date created;

    /**
     * Version field.
     */
    @Version
    @Column(name = "C_VERSION")
    private long version;

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    @SuppressWarnings("unused")
    private Message() {}

    /**
     * Create a new Message with message number and message text.
     * 
     * @param messageNo
     *            The message number
     * @param messageText
     *            The message text
     */
    public Message(int messageNo, String messageText) {
        this.messageNo = messageNo;
        this.messageText = messageText;
        this.created = new Date();
    }

    /**
     * Return unique technical key.
     * 
     * @return The unique technical key
     */
    public Long getId() {
        return id;
    }

    /**
     * Return the message number.
     * 
     * @return The message number
     */
    public int getMessageNo() {
        return messageNo;
    }

    /**
     * Set the message number.
     * 
     * @param messageNo
     *            The messageNo to set.
     */
    public void setMessageNo(int messageNo) {
        this.messageNo = messageNo;
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
     * Set the message text.
     * 
     * @param messageText
     *            The messageText to set.
     */
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    /**
     * Return the date when this {@link Message} was created.
     * 
     * @return The date of creation
     */
    public Date getCreated() {
        return created;
    }

    /**
     * JPA optimistic locking.
     * 
     * @return The version field
     */
    public long getVersion() {
        return this.version;
    }
}