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
package org.openwms.web.flex.client.control
{
    import com.adobe.cairngorm.control.FrontController;

    /**
     * A MainController.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision$
     */
    public class MainController extends FrontController
    {
        public function MainController():void
        {
            super();
            setupEventHandler();
        }

        private function setupEventHandler():void
        {
        }

        /**
         * Register a new command as handler for incoming events. If the event occurrs,
         * the command is executed.
         */
        public function registerHander(event:String, command:Class):void
        {
            this.addCommand(event, command);
        }

        /**
         * Unregister a command. This is useful when an application module is unloaded. In
         * that case, all commands belonging to that module and that are registered previously
         * must be unregistered again.
         */
        public function unregisterHandler(event:String):void
        {
            this.removeCommand(event)
        }
    }
}
