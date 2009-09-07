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
    public class RoleBase implements IExternalizable {

        private var __initialized:Boolean = true;
        private var __detachedState:String = null;

        private var _description:String;
        private var _id:Number;
        private var _preferences:ListCollectionView;
        private var _rolename:String;
        private var _users:ListCollectionView;
        private var _version:Number;

        meta function isInitialized(name:String = null):Boolean {
            if (!name)
                return __initialized;

            var property:* = this[name];
            return (
                (!(property is Role) || (property as Role).meta::isInitialized()) &&
                (!(property is IPersistentCollection) || (property as IPersistentCollection).isInitialized())
            );
        }

        public function set description(value:String):void {
            _description = value;
        }
        public function get description():String {
            return _description;
        }

        public function get id():Number {
            return _id;
        }

        public function set preferences(value:ListCollectionView):void {
            _preferences = value;
        }
        public function get preferences():ListCollectionView {
            return _preferences;
        }

        public function get rolename():String {
            return _rolename;
        }

        public function set users(value:ListCollectionView):void {
            _users = value;
        }
        public function get users():ListCollectionView {
            return _users;
        }

        public function get version():Number {
            return _version;
        }

        public function readExternal(input:IDataInput):void {
            __initialized = input.readObject() as Boolean;
            __detachedState = input.readObject() as String;
            if (meta::isInitialized()) {
                _description = input.readObject() as String;
                _id = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
                _preferences = input.readObject() as ListCollectionView;
                _rolename = input.readObject() as String;
                _users = input.readObject() as ListCollectionView;
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
                output.writeObject(_description);
                output.writeObject(_id);
                output.writeObject(_preferences);
                output.writeObject(_rolename);
                output.writeObject(_users);
                output.writeObject(_version);
            }
            else {
                output.writeObject(_id);
            }
        }
    }
}