/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
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
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.core.domain.values {

    /**
     * A Measurable.
     *
     * @version $Revision: 1719 $
     * @since 0.1
     */
    public interface Measurable {

        /**
         * Get the magnitude of this <code>Measureable</code>.
         *
         * @return the magnitude
         */
        function get magnitude() : Number;

        /**
         * Check whether the magnitude is of negative value.
         *
         * @return <code>true</code> if the magnitude is of negative value,
         *         otherwise <code>false</code>
         */
        function get negative() : Boolean;

        /**
         * Returns the type of <code>Measureable</code>.
         *
         * @return The <code>Measureable</code>'s type
         */
        function get unitType() : BaseUnit;

        /**
         * Check whether the magnitude is 0.
         *
         * @return <code>true</code> is magnitude is 0, otherwise <code>false</code>
         */
        function get zero() : Boolean;
    }
}
