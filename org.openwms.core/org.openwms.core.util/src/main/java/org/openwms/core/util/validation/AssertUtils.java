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
package org.openwms.core.util.validation;

/**
 * A AssertUtils. Simple utility class to encapsulate some assert methods
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public final class AssertUtils {

    private AssertUtils() {}

    /**
     * Asserts that <code>obj</code> is not <code>null</code> .
     * 
     * @param obj
     *            the Object to be checked against <code>null</code>
     * @param msg
     *            the message text of the thrown exception
     * @throws IllegalArgumentException
     *             if <code>obj</code> is <code>null</code>
     */
    public static void notNull(Object obj, String msg) {
        if (null == obj) {
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Asserts that <code>obj</code> is not <code>null</code> .
     * 
     * @param obj
     *            the Object to be checked
     * @throws IllegalArgumentException
     *             if <code>obj</code> is <code>null</code>
     */
    public static void notNull(Object obj) {
        notNull(obj, "Argument must not be null");
    }

    /**
     * Asserts that the String <code>str</code> is not <code>null</code> and not empty.
     * 
     * @param str
     *            the String to be checked
     * @param message
     *            the message in case of failure
     * @throws IllegalArgumentException
     *             if <code>str</code> is <code>null</code> or empty
     * @see java.lang.String#isEmpty()
     */
    public static void isNotEmpty(String str, String message) {
        if (null == str || str.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Asserts that the String <code>str</code> is not <code>null</code> and not empty.
     * 
     * @param str
     *            the String to check
     * @throws IllegalArgumentException
     *             if <code>str</code> is <code>null</code> or empty
     * @see java.lang.String#isEmpty()
     */
    public static void isNotEmpty(String str) {
        isNotEmpty(str, "The String must not be empty nor be null");
    }
}