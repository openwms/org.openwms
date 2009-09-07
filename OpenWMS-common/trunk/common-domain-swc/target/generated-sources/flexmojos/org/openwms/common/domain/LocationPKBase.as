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

    [Bindable]
    public class LocationPKBase implements IExternalizable {

        private var _aisle:String;
        private var _area:String;
        private var _x:String;
        private var _y:String;
        private var _z:String;

        public function get aisle():String {
            return _aisle;
        }

        public function get area():String {
            return _area;
        }

        public function get x():String {
            return _x;
        }

        public function get y():String {
            return _y;
        }

        public function get z():String {
            return _z;
        }

        public function readExternal(input:IDataInput):void {
            _aisle = input.readObject() as String;
            _area = input.readObject() as String;
            _x = input.readObject() as String;
            _y = input.readObject() as String;
            _z = input.readObject() as String;
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(_aisle);
            output.writeObject(_area);
            output.writeObject(_x);
            output.writeObject(_y);
            output.writeObject(_z);
        }
    }
}