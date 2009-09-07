/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain.system.usermanagement {

    import flash.utils.ByteArray;
    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import flash.utils.IExternalizable;
    import org.granite.util.Enum;

    [Bindable]
    public class UserDetailsBase implements IExternalizable {

        private var _comment:String;
        private var _department:String;
        private var _description:String;
        private var _image:ByteArray;
        private var _office:String;
        private var _phoneNo:String;
        private var _sex:UserDetails$SEX;
        private var _skypeName:String;

        public function set comment(value:String):void {
            _comment = value;
        }
        public function get comment():String {
            return _comment;
        }

        public function set department(value:String):void {
            _department = value;
        }
        public function get department():String {
            return _department;
        }

        public function set description(value:String):void {
            _description = value;
        }
        public function get description():String {
            return _description;
        }

        public function set image(value:ByteArray):void {
            _image = value;
        }
        public function get image():ByteArray {
            return _image;
        }

        public function set office(value:String):void {
            _office = value;
        }
        public function get office():String {
            return _office;
        }

        public function set phoneNo(value:String):void {
            _phoneNo = value;
        }
        public function get phoneNo():String {
            return _phoneNo;
        }

        public function set sex(value:UserDetails$SEX):void {
            _sex = value;
        }
        public function get sex():UserDetails$SEX {
            return _sex;
        }

        public function set skypeName(value:String):void {
            _skypeName = value;
        }
        public function get skypeName():String {
            return _skypeName;
        }

        public function readExternal(input:IDataInput):void {
            _comment = input.readObject() as String;
            _department = input.readObject() as String;
            _description = input.readObject() as String;
            _image = input.readObject() as ByteArray;
            _office = input.readObject() as String;
            _phoneNo = input.readObject() as String;
            _sex = Enum.readEnum(input) as UserDetails$SEX;
            _skypeName = input.readObject() as String;
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(_comment);
            output.writeObject(_department);
            output.writeObject(_description);
            output.writeObject(_image);
            output.writeObject(_office);
            output.writeObject(_phoneNo);
            output.writeObject(_sex);
            output.writeObject(_skypeName);
        }
    }
}