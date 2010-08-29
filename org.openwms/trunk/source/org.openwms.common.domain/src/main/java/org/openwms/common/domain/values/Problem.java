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
package org.openwms.common.domain.values;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * A Problem.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Embeddable
public class Problem implements Serializable {

    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 2923793250934936203L;

    /**
     * Timestamp when the {@link Problem} occurred.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OCCURRED")
    private Date occurred;

    /**
     * Message number of this {@link Problem}.
     */
    @Column(name = "MESSAGE_NO")
    private int messageNo;

    /**
     * Message text as String about the {@link Problem}.
     */
    @Column(name = "MESSAGE")
    private String message;

    /* ----------------------------- methods ------------------- */
    /**
     * Creates a new {@link Problem}.
     */
    public Problem() {
        this.occurred = new Date();
    }

    /**
     * Create a new {@link Problem} with a message text.
     * 
     * @param message text as String
     */
    public Problem(String message) {
        this();
        this.message = message;
    }

    /**
     * Create a new {@link Problem} with a message text and a message number.
     * 
     * @param message text as String
     * @param messageNo message number
     */
    public Problem(String message, int messageNo) {
        this();
        this.message = message;
        this.messageNo = messageNo;
    }

    /**
     * Get the Date when occurred.
     * 
     * @return Date when occurred.
     */
    public Date getOccurred() {
        return occurred;
    }

    /**
     * Set the Date when occurred.
     * 
     * @param occurred
     *            The Date to set.
     */
    public void setOccurred(Date occurred) {
        this.occurred = occurred;
    }

    /**
     * Get the messageNo.
     * 
     * @return The messageNo.
     */
    public int getMessageNo() {
        return messageNo;
    }

    /**
     * Set the messageNo.
     * 
     * @param messageNo
     *            The messageNo to set.
     */
    public void setMessageNo(int messageNo) {
        this.messageNo = messageNo;
    }

    /**
     * Get the message.
     * 
     * @return The message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message.
     * 
     * @param message
     *            The message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
