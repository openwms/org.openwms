/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain.values {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import flash.utils.IExternalizable;
    import org.granite.util.Enum;

    [Bindable]
    public class WeightBase implements IExternalizable, Unit {

        private var _unit:WeightUnit;
        private var _value:Number;

        public function get unit():WeightUnit {
            return _unit;
        }

        public function get value():Number {
            return _value;
        }

        public function readExternal(input:IDataInput):void {
            _unit = Enum.readEnum(input) as WeightUnit;
            _value = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(_unit);
            output.writeObject(_value);
        }
    }
}