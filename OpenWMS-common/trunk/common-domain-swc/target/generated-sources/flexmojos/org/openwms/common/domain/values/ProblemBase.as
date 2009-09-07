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
    public class ProblemBase implements IExternalizable {

        private var _message:String;
        private var _messageNo:int;
        private var _occured:Date;

        public function set message(value:String):void {
            _message = value;
        }
        public function get message():String {
            return _message;
        }

        public function set messageNo(value:int):void {
            _messageNo = value;
        }
        public function get messageNo():int {
            return _messageNo;
        }

        public function set occured(value:Date):void {
            _occured = value;
        }
        public function get occured():Date {
            return _occured;
        }

        public function readExternal(input:IDataInput):void {
            _message = input.readObject() as String;
            _messageNo = input.readObject() as int;
            _occured = input.readObject() as Date;
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(_message);
            output.writeObject(_messageNo);
            output.writeObject(_occured);
        }
    }
}