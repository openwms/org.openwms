package org.openwms.tms;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * A Message is used to encapsulate a message text with an identifier.
 * 
 * @GlossaryTerm
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Embeddable
public class Message implements Serializable {

    /** Timestamp when the {@literal Message} has occurred. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_OCCURRED")
    private Date occurred;

    /** Message number of the {@literal Message}. */
    @Column(name = "C_NO")
    private String messageNo;

    /** Message text about the {@literal Message}. */
    @Column(name = "C_MESSAGE")
    private String message;

    /* ----------------------------- methods ------------------- */
    /**
     * Dear JPA...
     */
    public Message() {
    }

    private Message(Builder builder) {
        occurred = builder.occurred;
        messageNo = builder.messageNo;
        message = builder.message;
    }

    /**
     * Return the Date when the {@literal Message} has occurred.
     * 
     * @return Date when occurred.
     */
    public Date getOccurred() {
        return occurred;
    }

    /**
     * Get the messageNo.
     * 
     * @return The messageNo.
     */
    public String getMessageNo() {
        return messageNo;
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
     * {@code Message} builder static inner class.
     */
    public static final class Builder {

        private Date occurred;
        private String messageNo;
        private String message;

        public Builder() {
        }

        /**
         * Sets the {@code occurred} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code occurred} to set
         * @return a reference to this Builder
         */
        public Builder withOccurred(Date val) {
            occurred = val;
            return this;
        }

        /**
         * Sets the {@code messageNo} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code messageNo} to set
         * @return a reference to this Builder
         */
        public Builder withMessageNo(String val) {
            messageNo = val;
            return this;
        }

        /**
         * Sets the {@code message} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code message} to set
         * @return a reference to this Builder
         */
        public Builder withMessage(String val) {
            message = val;
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