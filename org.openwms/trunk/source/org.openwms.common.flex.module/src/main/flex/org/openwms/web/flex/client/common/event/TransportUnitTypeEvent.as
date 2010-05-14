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
package org.openwms.web.flex.client.common.event
{
    import com.adobe.cairngorm.control.CairngormEvent;

    /**
     * A TransportUnitTypeEvent.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision: 771 $
     */
    public class TransportUnitTypeEvent extends CairngormEvent
    {
        public static const LOAD_ALL_TRANSPORT_UNIT_TYPES:String = "LoadAllTransportUnitTypes";
        public static const CREATE_TRANSPORT_UNIT_TYPE:String = "Create_TransportUnitType";
        public static const DELETE_TRANSPORT_UNIT_TYPE:String = "Delete_TransportUnitType";
        public static const SAVE_TRANSPORT_UNIT_TYPE:String = "Save_TransportUnitType";
        public static const LOAD_TUT_RULES:String = "Load_RulesForTUT";

        public function TransportUnitTypeEvent(type:String, bubbles:Boolean = false, cancelable:Boolean = false)
        {
            super(type, bubbles, cancelable);
        }

    }
}