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

    [Bindable]
    public class BarcodeBase implements IExternalizable {

        private var _id:String;

        public function get id():String {
            return _id;
        }

        public function readExternal(input:IDataInput):void {
            _id = input.readObject() as String;
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(_id);
        }
    }
}