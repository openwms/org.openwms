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
package org.openwms.web.flex.client {
	
    import mx.collections.ArrayCollection;
    import mx.collections.XMLListCollection;

    /**
     * DEPRECATED
     */
    public class MenuItemMap extends HashMap {
    	
        /**
         * MenuItemMap.
         */
        public function MenuItemMap(dataProvider:XMLListCollection) {
            super();
            var appendOthers:Boolean = false;
            for each (var item:XML in dataProvider) {
                if (item.hasOwnProperty("@targetPos")) {
                    trace("Put " + item.@label + " to target position " + item.@targetPos);
                    put(parseInt(item.@targetPos), item);
                }
                else {
                    appendOthers = true;
                }
            }
            if (appendOthers) {
                // Do it once again and append unknowns to the end...
                for each (item in dataProvider) {
                    if (!item.hasOwnProperty("@targetPos")) {
                        var i:int = getKeys().length;
                        var pos:int = 0;
                        if (i > 0) {
                            pos = getKeys()[i - 1];
                            pos++;
                        }
                        trace("Put " + item.@label + " to position " + pos);
                        put(pos, item);
                    }
                }
            }
        }
    }
}