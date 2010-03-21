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
    import com.adobe.cairngorm.control.CairngormEvent;
    import com.adobe.cairngorm.commands.ICommand;
    import mx.rpc.events.ResultEvent;
    import mx.rpc.IResponder;
    import mx.rpc.events.FaultEvent;
    import mx.collections.ArrayCollection;
    import mx.controls.Alert;
    import flash.events.EventDispatcher;
    import org.openwms.web.flex.client.service.ModuleLocator;
    import org.openwms.web.flex.client.business.ModulesDelegate;
    import org.openwms.web.flex.client.event.ModulesEvent;
    import org.openwms.web.flex.client.event.EventBroker;

    /**
     * A LoadModulesCommand.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision: 700 $
     */
    [Event(name="modulesLoaded", type="org.openwms.web.flex.client.event.ModulesEvent")]
    public class LoadModulesCommand extends EventDispatcher implements IResponder, ICommand
    {

        public function LoadModulesCommand()
        {
            super();
        }

        public function result(data:Object):void
        {
            var rawResult:ArrayCollection = (data as ResultEvent).result as ArrayCollection;
            var moduleLocator:ModuleLocator = ModuleLocator.getInstance();
            var broker:EventBroker = EventBroker.getInstance();
            moduleLocator.allModules = rawResult;
            var event:ModulesEvent = new ModulesEvent(ModulesEvent.MODULES_LOADED);
            broker.dispatchEvt(event);
        }

        public function fault(info:Object):void
        {
            trace("Fault in [" + this + "] Errormessage : " + info);
            var fault:FaultEvent = info as FaultEvent;
            Alert.show("Fault while loading all Application Modules from the server");
        }

        public function execute(event:CairngormEvent):void
        {
            var delegate:ModulesDelegate = new ModulesDelegate(this)
            delegate.getModules();
        }

    }
}