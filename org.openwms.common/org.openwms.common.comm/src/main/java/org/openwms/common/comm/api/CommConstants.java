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
package org.openwms.common.comm.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A final CommConstants class aggregates common used data and formatting types
 * and provides useful conversation methods.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
public final class CommConstants {
    /** The date format used in messages. */
    public static final String DATE_FORMAT_PATTERN = "yyyyMMddHHmmss";
    /** Used as suffix to create channels dynamically. */
    public static final String CHANNEL_SUFFIX = "MessageInputChannel";

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_PATTERN);

    /**
     * Parses a String representation of a Date into a Date using the
     * pre-defined format.
     * 
     * @param dateString
     *            The date String to convert
     * @return The converted date String
     * @throws ParseException
     *             in case the dateString hasn't the expected format pattern
     */
    public static Date asDate(String dateString) throws ParseException {
        return DATE_FORMAT.parse(dateString);
    }

    /**
     * Returns a Date object as formatted String.
     * 
     * @param date
     *            The date to format
     * @return The formatted String
     */
    public static String asString(Date date) {
        return DATE_FORMAT.format(date);
    }

    /**
     * Pad a String <tt>s</tt> with a number <tt>n</tt> of '*' characters.
     * 
     * @param s
     *            The String to pad
     * @param n
     *            Number of digits in sum
     * @return The padded String
     */
    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s).replace(" ", "*");
    }

    /**
     * Create a new CommConstants.
     */
    private CommConstants() {}
}
