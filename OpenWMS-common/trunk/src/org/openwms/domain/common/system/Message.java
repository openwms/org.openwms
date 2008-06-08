/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * A Message. Predefined messages.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
@Entity
@Table(name = "MESSAGE")
public class Message implements IMessage {

	/**
	 * Primary key.
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private long id;

	/**
	 * Parent <code>Location</code>.
	 */
	@Column(name = "LOCATION_ID")
	private long locationId;

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
	@Column(name = "CREATED")
	private Date created;

	/**
	 * Accessed by persistence provider.
	 */
	@SuppressWarnings("unused")
	private Message() {
	}

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
	 * Get the id.
	 * 
	 * @return the id.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Get the messageNo.
	 * 
	 * @return the messageNo.
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
	 * Get the messageText.
	 * 
	 * @return the messageText.
	 */
	public String getMessageText() {
		return messageText;
	}

	/**
	 * Set the messageText.
	 * 
	 * @param messageText
	 *            The messageText to set.
	 */
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	/**
	 * Get the created.
	 * 
	 * @return the created.
	 */
	public Date getCreated() {
		return created;
	}
}
