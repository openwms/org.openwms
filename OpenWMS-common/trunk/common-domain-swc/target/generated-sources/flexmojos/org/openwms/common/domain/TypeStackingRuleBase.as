/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import flash.utils.IExternalizable;
    import org.granite.collections.IPersistentCollection;
    import org.granite.meta;

    use namespace meta;

    [Bindable]
    public class TypeStackingRuleBase implements IExternalizable {

        private var __initialized:Boolean = true;
        private var __detachedState:String = null;

        private var _allowedTransportUnitType:TransportUnitType;
        private var _id:Number;
        private var _noTransportUnits:int;
        private var _transportUnitType:TransportUnitType;

        meta function isInitialized(name:String = null):Boolean {
            if (!name)
                return __initialized;

            var property:* = this[name];
            return (
                (!(property is TypeStackingRule) || (property as TypeStackingRule).meta::isInitialized()) &&
                (!(property is IPersistentCollection) || (property as IPersistentCollection).isInitialized())
            );
        }

        public function get allowedTransportUnitType():TransportUnitType {
            return _allowedTransportUnitType;
        }

        public function get id():Number {
            return _id;
        }

        public function get noTransportUnits():int {
            return _noTransportUnits;
        }

        public function get transportUnitType():TransportUnitType {
            return _transportUnitType;
        }

        public function readExternal(input:IDataInput):void {
            __initialized = input.readObject() as Boolean;
            __detachedState = input.readObject() as String;
            if (meta::isInitialized()) {
                _allowedTransportUnitType = input.readObject() as TransportUnitType;
                _id = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
                _noTransportUnits = input.readObject() as int;
                _transportUnitType = input.readObject() as TransportUnitType;
            }
            else {
                _id = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
            }
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(__initialized);
            output.writeObject(__detachedState);
            if (meta::isInitialized()) {
                output.writeObject(_allowedTransportUnitType);
                output.writeObject(_id);
                output.writeObject(_noTransportUnits);
                output.writeObject(_transportUnitType);
            }
            else {
                output.writeObject(_id);
            }
        }
    }
}