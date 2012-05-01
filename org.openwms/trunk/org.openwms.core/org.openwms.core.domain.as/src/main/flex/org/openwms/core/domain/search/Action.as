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
package org.openwms.core.domain.search {

    [Bindable]
    [RemoteClass(alias="org.openwms.core.domain.search.Action")]
    /**
     * An Action represents a possible UI action an User can take. Each Action has a
     * resulting URL to a webpage and a descriptive text that is displayed in the
     * UI. Additionally a field <code>weight</code> is used to count how many times
     * the User has chosen this Action.
     *
     * @version $Revision$
     * @since 0.1
     */
    public class Action extends ActionBase {
    }
}