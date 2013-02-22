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
package org.openwms.core.domain {

    /**
     * A DomainObject, implementations of this interface offer some
     * functionality common to all domain objects.<br>
     * Each domain object:
     * <ul>
     * <li>shall have an optimistic locking field</li>
     * <li>shall know if it is a transient or persisted instance</li>
     * </ul>
     *
     * @version $Revision$
     * @since 0.1
     */
    public interface DomainObject {

        /**
         * Check whether the persistent domain object is transient or not.
         *
         * @return <code>true</code> if transient (not persisted before), otherwise
         *         <code>false</code>.
         */
        function isNew() : Boolean;

        /**
         * Each persistent domain class must have an optimistic locking field.
         *
         * @return the version number
         */
        function get version() : Number;
    }
}