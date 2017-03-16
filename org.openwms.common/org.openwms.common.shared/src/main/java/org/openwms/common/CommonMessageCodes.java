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
package org.openwms.common;

/**
 * A CommonMessageCodes.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @since 1.0
 */
public class CommonMessageCodes {

    /** Code to signal that no TransportUnit with given Barcode exists. */
    public static final String BARCODE_NOT_FOUND ="COMMON.BARCODE_NOT_FOUND";
    public static final String LOCATION_NOT_FOUND = "COMMON.LOCATION_NOT_FOUND";
    public static final String LOCATION_GROUP_NOT_FOUND = "COMMON.LOCATION_GROUP_NOT_FOUND";
    public static final String TRANSPORT_UNIT_TYPE_NOT_FOUND = "COMMON.TRANSPORT_UNIT_TYPE_NOT_FOUND";
}
