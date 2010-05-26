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
    import com.adobe.cairngorm.commands.ICommand;
    import com.adobe.cairngorm.control.CairngormEvent;
    
    import mx.collections.ArrayCollection;
    import mx.controls.Alert;
    import mx.rpc.IResponder;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;
    
    import org.openwms.common.domain.Rule;
    import org.openwms.common.domain.TransportUnitType;
    import org.openwms.common.domain.TypePlacingRule;
    import org.openwms.common.domain.TypeStackingRule;
    import org.openwms.web.flex.client.common.business.TransportUnitTypeDelegate;
    import org.openwms.web.flex.client.command.AbstractCommand;
    
    /**
     * A LoadRulesForTransportUnitTypeCommand.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision: 700 $
     */
    public class LoadRulesForTransportUnitTypeCommand extends AbstractCommand implements IResponder, ICommand {

        private var transportUnitType:TransportUnitType;

        public function LoadRulesForTransportUnitTypeCommand() {
            super();
        }

        public function result(data:Object):void {
        	var rules:ArrayCollection = (data as ResultEvent).result as ArrayCollection;
            for each (var rule:Rule in rules) {
                if (rule is TypePlacingRule) {
                    trace("Rule added as Placing Rule");
                	transportUnitType.typePlacingRules.addItem(rule);
                }
                if (rule is TypeStackingRule) {
                    trace("Rule added as Stacking Rule");
                    transportUnitType.typeStackingRules.addItem(rule);
                }
            }
        }

        public function fault(event:Object):void {
            if (onFault(event)) {
                return;
            }
            Alert.show("Could not load Rules for Transport Unit Type");
        }

        public function execute(event:CairngormEvent):void {
            if (event.data == null) {
                Alert.show("ERROR: No Transport Unit Type selected");
                return;
            }
            transportUnitType=event.data as TransportUnitType;
            var delegate:TransportUnitTypeDelegate=new TransportUnitTypeDelegate(this);
            delegate.loadRules(transportUnitType.type);
        }

    }
}