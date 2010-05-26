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
package org.openwms.web.flex.client.common.command {
    import com.adobe.cairngorm.control.CairngormEvent;
    import com.adobe.cairngorm.commands.ICommand;
    import mx.collections.ArrayCollection;
    import org.openwms.web.flex.client.model.ModelLocator;
    import org.openwms.web.flex.client.common.business.LocationDelegate;
    import org.openwms.web.flex.client.command.AbstractCommand;
    import mx.rpc.IResponder;
    import mx.controls.Alert;
    import mx.rpc.events.ResultEvent;

    /**
     * A LoadLocationsCommand.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision$
     */
    public class LoadLocationsCommand extends AbstractCommand implements IResponder, ICommand {
        [Bindable]
        private var modelLocator : ModelLocator = ModelLocator.getInstance();

        public function LoadLocationsCommand() {
            super();
        }

        public function result(data : Object) : void {
            modelLocator.allLocations = (data as ResultEvent).result as ArrayCollection;
        }

        public function fault(event : Object) : void {
            if (onFault(event)) {
                return;
            }
            Alert.show("Cannot load Locations");
        }

        public function execute(event : CairngormEvent) : void {
            new LocationDelegate(this).getLocations();
        }

    }
}