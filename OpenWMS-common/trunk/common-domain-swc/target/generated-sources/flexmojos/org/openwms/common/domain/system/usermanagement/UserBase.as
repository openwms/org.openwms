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
    import mx.collections.ListCollectionView;
    import org.granite.collections.IPersistentCollection;
    import org.granite.meta;

    use namespace meta;

    [Bindable]
    public class UserBase implements IExternalizable {

        private var __initialized:Boolean = true;
        private var __detachedState:String = null;

        private var _enabled:Boolean;
        private var _expirationDate:Date;
        private var _extern:Boolean;
        private var _fullname:String;
        private var _id:Number;
        private var _lastPasswordChange:Date;
        private var _locked:Boolean;
        private var _password:String;
        private var _passwords:ListCollectionView;
        private var _preferences:ListCollectionView;
        private var _roles:ListCollectionView;
        private var _userDetails:UserDetails;
        private var _username:String;

        meta function isInitialized(name:String = null):Boolean {
            if (!name)
                return __initialized;

            var property:* = this[name];
            return (
                (!(property is User) || (property as User).meta::isInitialized()) &&
                (!(property is IPersistentCollection) || (property as IPersistentCollection).isInitialized())
            );
        }

        public function set enabled(value:Boolean):void {
            _enabled = value;
        }
        public function get enabled():Boolean {
            return _enabled;
        }

        public function set expirationDate(value:Date):void {
            _expirationDate = value;
        }
        public function get expirationDate():Date {
            return _expirationDate;
        }

        public function set extern(value:Boolean):void {
            _extern = value;
        }
        public function get extern():Boolean {
            return _extern;
        }

        public function set fullname(value:String):void {
            _fullname = value;
        }
        public function get fullname():String {
            return _fullname;
        }

        public function get id():Number {
            return _id;
        }

        public function set lastPasswordChange(value:Date):void {
            _lastPasswordChange = value;
        }
        public function get lastPasswordChange():Date {
            return _lastPasswordChange;
        }

        public function set locked(value:Boolean):void {
            _locked = value;
        }
        public function get locked():Boolean {
            return _locked;
        }

        public function set password(value:String):void {
            _password = value;
        }
        public function get password():String {
            return _password;
        }

        public function set passwords(value:ListCollectionView):void {
            _passwords = value;
        }
        public function get passwords():ListCollectionView {
            return _passwords;
        }

        public function set preferences(value:ListCollectionView):void {
            _preferences = value;
        }
        public function get preferences():ListCollectionView {
            return _preferences;
        }

        public function set roles(value:ListCollectionView):void {
            _roles = value;
        }
        public function get roles():ListCollectionView {
            return _roles;
        }

        public function set userDetails(value:UserDetails):void {
            _userDetails = value;
        }
        public function get userDetails():UserDetails {
            return _userDetails;
        }

        public function set username(value:String):void {
            _username = value;
        }
        public function get username():String {
            return _username;
        }

        public function readExternal(input:IDataInput):void {
            __initialized = input.readObject() as Boolean;
            __detachedState = input.readObject() as String;
            if (meta::isInitialized()) {
                _enabled = input.readObject() as Boolean;
                _expirationDate = input.readObject() as Date;
                _extern = input.readObject() as Boolean;
                _fullname = input.readObject() as String;
                _id = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
                _lastPasswordChange = input.readObject() as Date;
                _locked = input.readObject() as Boolean;
                _password = input.readObject() as String;
                _passwords = input.readObject() as ListCollectionView;
                _preferences = input.readObject() as ListCollectionView;
                _roles = input.readObject() as ListCollectionView;
                _userDetails = input.readObject() as UserDetails;
                _username = input.readObject() as String;
            }
            else {
                _id = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
            }
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(__initialized);
            output.writeObject(__detachedState);
            if (meta::isInitialized()) {
                output.writeObject(_enabled);
                output.writeObject(_expirationDate);
                output.writeObject(_extern);
                output.writeObject(_fullname);
                output.writeObject(_id);
                output.writeObject(_lastPasswordChange);
                output.writeObject(_locked);
                output.writeObject(_password);
                output.writeObject(_passwords);
                output.writeObject(_preferences);
                output.writeObject(_roles);
                output.writeObject(_userDetails);
                output.writeObject(_username);
            }
            else {
                output.writeObject(_id);
            }
        }
    }
}