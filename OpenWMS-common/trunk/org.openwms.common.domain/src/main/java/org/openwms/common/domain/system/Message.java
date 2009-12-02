/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
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

/**
 * A Message. Predefined messages.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 */
@Entity
@Table(name = "MESSAGE")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Primary key.
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
     * Timestamp when the <code>Message</code> was created.
     */
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED")
    private Date created;

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    @SuppressWarnings("unused")
    private Message() {}

    /**
     * Create a new Message.
     * 
     * @param messageNo
     * @param messageText
     */
    public Message(int messageNo, String messageText) {
	this.messageNo = messageNo;
	this.messageText = messageText;
	this.created = new Date();
    }

    /**
     * {@inheritDoc}
     */
    public Long getId() {
	return id;
    }

    /**
     * {@inheritDoc}
     */
    public int getMessageNo() {
	return messageNo;
    }

    /**
     * Set the messageNo.
     * 
     * @param messageNo
     *                The messageNo to set.
     */
    public void setMessageNo(int messageNo) {
	this.messageNo = messageNo;
    }

    /**
     * {@inheritDoc}
     */
    public String getMessageText() {
	return messageText;
    }

    /**
     * Set the messageText.
     * 
     * @param messageText
     *                The messageText to set.
     */
    public void setMessageText(String messageText) {
	this.messageText = messageText;
    }

    /**
     * {@inheritDoc}
     */
    public Date getCreated() {
	return created;
    }
}
