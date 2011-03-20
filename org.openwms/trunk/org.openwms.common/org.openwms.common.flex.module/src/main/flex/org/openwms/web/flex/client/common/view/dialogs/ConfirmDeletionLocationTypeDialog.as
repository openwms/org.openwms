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
package org.openwms.web.flex.client.common.view.dialogs {

    import flash.events.Event;
    import mx.collections.ArrayCollection;
    import org.openwms.common.domain.LocationType;
    import org.openwms.web.flex.client.common.event.LocationTypeEvent;
    
    /**
     * A ConfirmDeletionLocationTypeDialog.
     *
     * @author <a href="mailto:scherrer@users.sourceforge.net">Heiko Scherrer</a>
     * @version $Revision: 796 $
     * @since 0.1
     */
    [Name]
    [ManagedEvent(name="DELETE_LOCATION_TYPE")]
    [Bindable]
    public class ConfirmDeletionLocationTypeDialog extends ConfirmDeletionDialog {

        /**
         * Used to construct the instance.
         */
        public function ConfirmDeletionLocationTypeDialog() {
            super();
            super.title = "Delete selected Location Types";
            super.messageText = "Delete selected Location Types";
        }
        
        override protected function accept(e:Event):void {
            var event:LocationTypeEvent = new LocationTypeEvent(LocationTypeEvent.DELETE_LOCATION_TYPE);
            event.data = deleteLst.dataProvider as ArrayCollection;
            dispatchEvent(event);
            closeDialog();
        }
        
        override protected function formatFunction(item:*):String {
            return (item as LocationType).type+"\t"+(item as LocationType).description;
        }
     }
}