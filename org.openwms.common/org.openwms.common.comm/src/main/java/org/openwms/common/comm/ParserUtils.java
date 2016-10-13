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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A ParserUtils.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public final class ParserUtils {

    /**
     * Parses a String representation of a Date into a Date using the pre-defined format.
     *
     * @param dateString The date String to convert
     * @return The converted date String
     * @throws ParseException in case the dateString hasn't the expected format pattern
     */
    public static Date asDate(String dateString) throws ParseException {
        return new SimpleDateFormat(CommConstants.DATE_FORMAT_PATTERN).parse(dateString);
    }

    /**
     * Returns a Date object as formatted String.
     *
     * @param date The date to format
     * @return The formatted String
     */
    public static String asString(Date date) {
        return new SimpleDateFormat(CommConstants.DATE_FORMAT_PATTERN).format(date);
    }

    /**
     * Pad a String {@code s} with a number {@code n} of characters {@code chr}.
     *
     * @param s The String to pad
     * @param n Number of digits in sum
     * @return The padded String
     */
    public static String padRight(String s, int n, String chr) {
        return String.format("%1$-" + n + "s", s).replace(" ", chr);
    }

    /**
     * Pad a String {@code s} with a number {@code n} of characters {@code chr}.
     *
     * @param s The String to pad
     * @param n Number of digits in sum
     * @return The padded String
     */
    public static String padLeft(String s, int n, String chr) {
        return String.format("%1$" + n + "s", s).replace(" ", chr);
    }
}
