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
package org.openwms.web.flex.client.command {
    import com.adobe.cairngorm.control.CairngormEvent;
    import com.adobe.cairngorm.commands.ICommand;
    import mx.collections.ArrayCollection;
    import org.openwms.web.flex.client.module.ModuleLocator;
    import org.openwms.web.flex.client.business.ModulesDelegate;
    import org.openwms.web.flex.client.event.ApplicationEvent;
    import org.openwms.common.domain.Module;
    import mx.rpc.IResponder;
    import mx.controls.Alert;
    import mx.rpc.events.ResultEvent;

    /**
     * A DeleteModuleCommand.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision: 700 $
     */
    public class DeleteModuleCommand extends AbstractCommand implements IResponder, ICommand {
        private var toRemove : Module;

        public function DeleteModuleCommand() {
            super();
        }

        public function result(data : Object) : void {
            ModuleLocator.getInstance().removeFromModules(toRemove, true);
        }

        public function fault(event : Object) : void {
            if (onFault(event)) {
                return;
            }
            Alert.show("Could not delete Module");
        }

        public function execute(event : CairngormEvent) : void {
            toRemove = event.data as Module;
            new ModulesDelegate(this).deleteModule(event.data as Module);
        }

    }
}