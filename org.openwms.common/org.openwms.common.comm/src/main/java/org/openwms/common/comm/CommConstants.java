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

/**
 * A final CommConstants class aggregates common used data and formatting types and provides useful conversation methods.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public final class CommConstants {

    /** The date format used in messages. */
    public static final String DATE_FORMAT_PATTERN = "yyyyMMddHHmmss";
    /** Defined telegram length. Validated in Serializer! */
    public static final int TELEGRAM_LENGTH = 160;
    /** Used as suffix to create channels dynamically. */
    public static final String CHANNEL_SUFFIX = "MessageInputChannel";
    public static final String DEFAULT_HTTP_SERVICE_ACCESS = "DEFAULT_HTTP_SERVICE_ACCESS";

    /** The character String used to pad LocationGroup fields till the defined length. */
    public static final char LOCGROUP_FILLER_CHARACTER = '_';
    /** The character String used to pad till the defined telegram length. */
    public static final String TELEGRAM_FILLER_CHARACTER = "*";

    private CommConstants() {
    }
}
