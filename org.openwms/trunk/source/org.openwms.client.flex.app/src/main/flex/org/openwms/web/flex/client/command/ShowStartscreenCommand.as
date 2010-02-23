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
package org.openwms.web.flex.client.command
{
    import com.adobe.cairngorm.commands.ICommand;
    import com.adobe.cairngorm.control.CairngormEvent;
    import org.openwms.web.flex.client.model.ModelLocator;

    /**
     * A ShowStartscreenCommand.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision: 235 $
     */
    public class ShowStartscreenCommand implements ICommand
    {
        [Bindable]
        private var modelLocator:ModelLocator = ModelLocator.getInstance();

        public function ShowStartscreenCommand()
        {
            super();
        }

        public function execute(event:CairngormEvent):void
        {
            modelLocator.mainViewStackIndex = ModelLocator.MAIN_VIEW_STACK_LOCATION_VIEW;
        }

    }
}