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
package org.openwms.tms;

/**
 * A TMSMessageCodes is a collection with message codes unique within this module.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @since 1.0
 */
public final class TMSMessageCodes {

    /*~ Messagetext Codes */


    /** Signals that the TransportOrder with the given persisted key wasn't found. */
    public static final String TO_WITH_PKEY_NOT_FOUND = "TMS.TO_WITH_PKEY_NOT_FOUND";
    /** Signals an exception because it was tried to turn back a TransportOrder into a state that isn't allowed. */
    public static final String TO_STATE_CHANGE_BACKWARDS_NOT_ALLOWED = "TMS.TO_STATE_CHANGE_BACKWARDS_NOT_ALLOWED";
    /** Signals an exception because it was tried to change the state of a TransportOrder into a following but not allowed state. */
    public static final String TO_STATE_CHANGE_NOT_READY = "TMS.TO_STATE_CHANGE_NOT_READY";
    /** Signals that a request with state of NULL. */
    public static final String TO_STATE_CHANGE_NULL_STATE = "TMS.TO_STATE_CHANGE_NULL_STATE";
    /** Signals an exception that it is not allowed to start a TransportOrder, because there is already a started one. */
    public static final String START_TO_NOT_ALLOWED_ALREADY_STARTED_ONE = "TMS.START_TO_NOT_ALLOWED_ALREADY_STARTED_ONE";
    /** Signals an exception that the requested state change is not allowed for the initialized TransportOrder. */
    public static final String STATE_CHANGE_ERROR_FOR_INITIALIZED_TO="TMS.STATE_CHANGE_ERROR_FOR_INITIALIZED_TO";


    /*~ Message Codes */

    /** Target LocationGroup or Location is blocked for infeed. */
    public static final String TARGET_BLOCKED_MSG = "TMS.TARGET_BLOCKED";
}
