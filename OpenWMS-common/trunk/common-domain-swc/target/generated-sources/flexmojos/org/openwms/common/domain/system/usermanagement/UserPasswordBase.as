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
    public class UserPasswordBase implements IExternalizable {

        private var __initialized:Boolean = true;
        private var __detachedState:String = null;

        private var _id:Number;
        private var _password:String;
        private var _user:User;

        meta function isInitialized(name:String = null):Boolean {
            if (!name)
                return __initialized;

            var property:* = this[name];
            return (
                (!(property is UserPassword) || (property as UserPassword).meta::isInitialized()) &&
                (!(property is IPersistentCollection) || (property as IPersistentCollection).isInitialized())
            );
        }

        public function set id(value:Number):void {
            _id = value;
        }
        public function get id():Number {
            return _id;
        }

        public function get password():String {
            return _password;
        }

        public function get user():User {
            return _user;
        }

        public function readExternal(input:IDataInput):void {
            __initialized = input.readObject() as Boolean;
            __detachedState = input.readObject() as String;
            if (meta::isInitialized()) {
                _id = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
                _password = input.readObject() as String;
                _user = input.readObject() as User;
            }
            else {
                _id = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
            }
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(__initialized);
            output.writeObject(__detachedState);
            if (meta::isInitialized()) {
                output.writeObject(_id);
                output.writeObject(_password);
                output.writeObject(_user);
            }
            else {
                output.writeObject(_id);
            }
        }
    }
}