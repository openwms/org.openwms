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
    public class PreferenceBase implements IExternalizable {

        private var __initialized:Boolean = true;
        private var __detachedState:String = null;

        private var _description:String;
        private var _floatValue:Number;
        private var _id:Number;
        private var _key:String;
        private var _maximum:int;
        private var _minimum:int;
        private var _roles:ListCollectionView;
        private var _type:String;
        private var _value:String;
        private var _version:Number;

        meta function isInitialized(name:String = null):Boolean {
            if (!name)
                return __initialized;

            var property:* = this[name];
            return (
                (!(property is Preference) || (property as Preference).meta::isInitialized()) &&
                (!(property is IPersistentCollection) || (property as IPersistentCollection).isInitialized())
            );
        }

        public function set description(value:String):void {
            _description = value;
        }
        public function get description():String {
            return _description;
        }

        public function set floatValue(value:Number):void {
            _floatValue = value;
        }
        public function get floatValue():Number {
            return _floatValue;
        }

        public function get id():Number {
            return _id;
        }

        public function set key(value:String):void {
            _key = value;
        }
        public function get key():String {
            return _key;
        }

        public function set maximum(value:int):void {
            _maximum = value;
        }
        public function get maximum():int {
            return _maximum;
        }

        public function set minimum(value:int):void {
            _minimum = value;
        }
        public function get minimum():int {
            return _minimum;
        }

        public function set roles(value:ListCollectionView):void {
            _roles = value;
        }
        public function get roles():ListCollectionView {
            return _roles;
        }

        public function set type(value:String):void {
            _type = value;
        }
        public function get type():String {
            return _type;
        }

        public function set value(value:String):void {
            _value = value;
        }
        public function get value():String {
            return _value;
        }

        public function get version():Number {
            return _version;
        }

        public function readExternal(input:IDataInput):void {
            __initialized = input.readObject() as Boolean;
            __detachedState = input.readObject() as String;
            if (meta::isInitialized()) {
                _description = input.readObject() as String;
                _floatValue = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
                _id = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
                _key = input.readObject() as String;
                _maximum = input.readObject() as int;
                _minimum = input.readObject() as int;
                _roles = input.readObject() as ListCollectionView;
                _type = input.readObject() as String;
                _value = input.readObject() as String;
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
                output.writeObject(_floatValue);
                output.writeObject(_id);
                output.writeObject(_key);
                output.writeObject(_maximum);
                output.writeObject(_minimum);
                output.writeObject(_roles);
                output.writeObject(_type);
                output.writeObject(_value);
                output.writeObject(_version);
            }
            else {
                output.writeObject(_id);
            }
        }
    }
}