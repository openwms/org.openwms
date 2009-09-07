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
    import mx.collections.ListCollectionView;
    import org.granite.collections.IMap;
    import org.granite.collections.IPersistentCollection;
    import org.granite.meta;
    import org.granite.util.Enum;
    import org.openwms.common.domain.system.usermanagement.User;
    import org.openwms.common.domain.values.Barcode;

    use namespace meta;

    [Bindable]
    public class TransportUnitBase implements IExternalizable {

        private var __initialized:Boolean = true;
        private var __detachedState:String = null;

        private var _actualLocation:Location;
        private var _actualLocationDate:Date;
        private var _barcode:Barcode;
        private var _children:ListCollectionView;
        private var _creationDate:Date;
        private var _empty:Boolean;
        private var _errors:IMap;
        private var _id:Number;
        private var _inventoryDate:Date;
        private var _inventoryUser:User;
        private var _parent:TransportUnit;
        private var _state:TransportUnit$TU_STATE;
        private var _targetLocation:Location;
        private var _transportUnitType:TransportUnitType;
        private var _version:Number;
        private var _weight:Number;

        meta function isInitialized(name:String = null):Boolean {
            if (!name)
                return __initialized;

            var property:* = this[name];
            return (
                (!(property is TransportUnit) || (property as TransportUnit).meta::isInitialized()) &&
                (!(property is IPersistentCollection) || (property as IPersistentCollection).isInitialized())
            );
        }

        public function set actualLocation(value:Location):void {
            _actualLocation = value;
        }
        public function get actualLocation():Location {
            return _actualLocation;
        }

        public function set actualLocationDate(value:Date):void {
            _actualLocationDate = value;
        }
        public function get actualLocationDate():Date {
            return _actualLocationDate;
        }

        public function set barcode(value:Barcode):void {
            _barcode = value;
        }
        public function get barcode():Barcode {
            return _barcode;
        }

        public function get children():ListCollectionView {
            return _children;
        }

        public function get creationDate():Date {
            return _creationDate;
        }

        public function set empty(value:Boolean):void {
            _empty = value;
        }

        public function get errors():IMap {
            return _errors;
        }

        public function get id():Number {
            return _id;
        }

        public function set inventoryDate(value:Date):void {
            _inventoryDate = value;
        }
        public function get inventoryDate():Date {
            return _inventoryDate;
        }

        public function set inventoryUser(value:User):void {
            _inventoryUser = value;
        }
        public function get inventoryUser():User {
            return _inventoryUser;
        }

        public function set parent(value:TransportUnit):void {
            _parent = value;
        }
        public function get parent():TransportUnit {
            return _parent;
        }

        public function set state(value:TransportUnit$TU_STATE):void {
            _state = value;
        }
        public function get state():TransportUnit$TU_STATE {
            return _state;
        }

        public function set targetLocation(value:Location):void {
            _targetLocation = value;
        }
        public function get targetLocation():Location {
            return _targetLocation;
        }

        public function set transportUnitType(value:TransportUnitType):void {
            _transportUnitType = value;
        }
        public function get transportUnitType():TransportUnitType {
            return _transportUnitType;
        }

        public function get version():Number {
            return _version;
        }

        public function set weight(value:Number):void {
            _weight = value;
        }
        public function get weight():Number {
            return _weight;
        }

        public function readExternal(input:IDataInput):void {
            __initialized = input.readObject() as Boolean;
            __detachedState = input.readObject() as String;
            if (meta::isInitialized()) {
                _actualLocation = input.readObject() as Location;
                _actualLocationDate = input.readObject() as Date;
                _barcode = input.readObject() as Barcode;
                _children = input.readObject() as ListCollectionView;
                _creationDate = input.readObject() as Date;
                _empty = input.readObject() as Boolean;
                _errors = input.readObject() as IMap;
                _id = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
                _inventoryDate = input.readObject() as Date;
                _inventoryUser = input.readObject() as User;
                _parent = input.readObject() as TransportUnit;
                _state = Enum.readEnum(input) as TransportUnit$TU_STATE;
                _targetLocation = input.readObject() as Location;
                _transportUnitType = input.readObject() as TransportUnitType;
                _version = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
                _weight = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
            }
            else {
                _id = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
            }
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(__initialized);
            output.writeObject(__detachedState);
            if (meta::isInitialized()) {
                output.writeObject(_actualLocation);
                output.writeObject(_actualLocationDate);
                output.writeObject(_barcode);
                output.writeObject(_children);
                output.writeObject(_creationDate);
                output.writeObject(_empty);
                output.writeObject(_errors);
                output.writeObject(_id);
                output.writeObject(_inventoryDate);
                output.writeObject(_inventoryUser);
                output.writeObject(_parent);
                output.writeObject(_state);
                output.writeObject(_targetLocation);
                output.writeObject(_transportUnitType);
                output.writeObject(_version);
                output.writeObject(_weight);
            }
            else {
                output.writeObject(_id);
            }
        }
    }
}