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
import java.util.Date;
import java.util.Objects;

/**
 * A Payload is the abstract superclass of all messages sent to subsystems like PLC or ERP.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public abstract class Payload implements Serializable {

    private String errorCode;
    private Date created;

    public static final short ERROR_CODE_LENGTH = 8;
    public static final short DATE_LENGTH = 14;
    public static final int MESSAGE_IDENTIFIER_LENGTH = 4;

    /**
     * Subclasses have to return an unique, case-sensitive message identifier.
     * 
     * @return The message TYPE field (see OSIP specification)
     */
    public abstract String getMessageIdentifier();

    /**
     * Does this type of message needs to be replied to?
     * 
     * @return {@literal true} no reply needed, otherwise {@literal false}
     */
    public abstract boolean isWithoutReply();

    /**
     * Get the errorCode.
     * 
     * @return the errorCode.
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Checks wether the {@code errorCode} is not {@literal null}.
     *
     * @return {@literal true} if errorCode is set, otherwise {@literal false}
     */
    public boolean hasErrorCode() {
        return errorCode != null;
    }

    /**
     * Set the errorCode.
     * 
     * @param errorCode
     *            The errorCode to set.
     */
    protected void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Get the created.
     * 
     * @return the created.
     */
    public Date getCreated() {
        if (created == null) {
            created = new Date();
        }
        return created;
    }

    /**
     * Set the created.
     * 
     * @param created
     *            The created to set.
     */
    protected void setCreated(Date created) {
        this.created = created;
    }

    /**
     * Checks if the given {@code str} starts with an '*'. An optional telegram value contains '*' only.
     *
     * @param str to check
     * @return true if set
     */
    public static boolean exists(String str) {
        return !str.startsWith("*");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payload payload = (Payload) o;
        return Objects.equals(errorCode, payload.errorCode) &&
                Objects.equals(created, payload.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorCode, created);
    }

    /**
     * todo: This needs to be extracted from the business object to an 'transformer'.
     * @return
     */
    public abstract String asString();
}
