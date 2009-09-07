/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain.system.usermanagement {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import flash.utils.IExternalizable;
    import org.granite.collections.IPersistentCollection;
    import org.granite.meta;

    use namespace meta;

    [Bindable]
    public class EmailBase implements IExternalizable {

        private var __initialized:Boolean = true;
        private var __detachedState:String = null;

        private var _emailAddress:String;
        private var _fullName:String;
        private var _id:Number;
        private var _username:String;
        private var _version:Number;

        meta function isInitialized(name:String = null):Boolean {
            if (!name)
                return __initialized;

            var property:* = this[name];
            return (
                (!(property is Email) || (property as Email).meta::isInitialized()) &&
                (!(property is IPersistentCollection) || (property as IPersistentCollection).isInitialized())
            );
        }

        public function set emailAddress(value:String):void {
            _emailAddress = value;
        }
        public function get emailAddress():String {
            return _emailAddress;
        }

        public function set fullName(value:String):void {
            _fullName = value;
        }
        public function get fullName():String {
            return _fullName;
        }

        public function get id():Number {
            return _id;
        }

        public function set username(value:String):void {
            _username = value;
        }
        public function get username():String {
            return _username;
        }

        public function get version():Number {
            return _version;
        }

        public function readExternal(input:IDataInput):void {
            __initialized = input.readObject() as Boolean;
            __detachedState = input.readObject() as String;
            if (meta::isInitialized()) {
                _emailAddress = input.readObject() as String;
                _fullName = input.readObject() as String;
                _id = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
                _username = input.readObject() as String;
                _version = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
            }
            else {
                _id = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
            }
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(__initialized);
            output.writeObject(__detachedState);
            if (meta::isInitialized()) {
                output.writeObject(_emailAddress);
                output.writeObject(_fullName);
                output.writeObject(_id);
                output.writeObject(_username);
                output.writeObject(_version);
            }
            else {
                output.writeObject(_id);
            }
        }
    }
}