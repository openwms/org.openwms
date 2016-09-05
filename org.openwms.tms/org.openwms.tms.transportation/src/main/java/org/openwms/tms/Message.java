package org.openwms.tms;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A Message is used to encapsulate a message text with an identifier.
 * 
 * @GlossaryTerm
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @since 1.0
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
    protected Message() {
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
     * {@inheritDoc}
     *
     * Use all fields.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Objects.equals(occurred, message1.occurred) &&
                Objects.equals(messageNo, message1.messageNo) &&
                Objects.equals(message, message1.message);
    }

    /**
     * {@inheritDoc}
     *
     * Use all fields.
     */
    @Override
    public String toString() {
        return "Message{" +
                "occurred=" + occurred +
                ", messageNo='" + messageNo + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    /**
     * {@inheritDoc}
     *
     * Use all fields.
     */
    @Override
    public int hashCode() {
        return Objects.hash(occurred, messageNo, message);
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