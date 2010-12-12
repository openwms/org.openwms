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
package org.openwms.web.flex.client.common.view.dialogs
{

    import flash.events.Event;
    
    import mx.collections.ArrayCollection;
    
    import org.openwms.common.domain.Location;
    import org.openwms.common.domain.LocationType;
    import org.openwms.web.flex.client.common.event.LocationEvent;
    
    /**
     * A ConfirmDeletionLocationDialog.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision: 796 $
     */
    [Name]
    [ManagedEvent(name="DELETE_LOCATION")]
    [Bindable]
    public class ConfirmDeletionLocationDialog extends ConfirmDeletionDialog
    {

        /**
         * Used to construct the instance.
         */
        public function ConfirmDeletionLocationDialog()
        {
            super();
            super.title = "Delete selected Locations";
            super.messageText = "Delete selected Locations";
        }
        
        override protected function accept(e:Event):void
        {
            var event:LocationEvent = new LocationEvent(LocationEvent.DELETE_LOCATION);
            event.data = (deleteLst.dataProvider as ArrayCollection).getItemAt(0);
            dispatchEvent(event);
            closeDialog();
        }
        
        override protected function formatFunction(item:*):String
        {
            return (item as Location).locationId.toString()+"\t"+(item as Location).description;
        }
        
     }
}