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
    import org.granite.collections.IPersistentCollection;
    import org.granite.meta;
    import org.granite.util.Enum;

    use namespace meta;

    [Bindable]
    public class LocationGroupBase implements IExternalizable {

        private var __initialized:Boolean = true;
        private var __detachedState:String = null;

        private var _description:String;
        private var _groupStateIn:LocationGroup$STATE;
        private var _groupStateOut:LocationGroup$STATE;
        private var _groupType:String;
        private var _id:Number;
        private var _lastUpdated:Date;
        private var _locationGroupCountingActive:Boolean;
        private var _locationGroups:ListCollectionView;
        private var _locations:ListCollectionView;
        private var _maxFillLevel:Number;
        private var _name:String;
        private var _noLocations:int;
        private var _parent:LocationGroup;
        private var _systemCode:String;
        private var _version:Number;

        meta function isInitialized(name:String = null):Boolean {
            if (!name)
                return __initialized;

            var property:* = this[name];
            return (
                (!(property is LocationGroup) || (property as LocationGroup).meta::isInitialized()) &&
                (!(property is IPersistentCollection) || (property as IPersistentCollection).isInitialized())
            );
        }

        public function set description(value:String):void {
            _description = value;
        }
        public function get description():String {
            return _description;
        }

        public function set groupStateIn(value:LocationGroup$STATE):void {
            _groupStateIn = value;
        }
        public function get groupStateIn():LocationGroup$STATE {
            return _groupStateIn;
        }

        public function set groupStateOut(value:LocationGroup$STATE):void {
            _groupStateOut = value;
        }
        public function get groupStateOut():LocationGroup$STATE {
            return _groupStateOut;
        }

        public function set groupType(value:String):void {
            _groupType = value;
        }
        public function get groupType():String {
            return _groupType;
        }

        public function get id():Number {
            return _id;
        }

        public function set lastUpdated(value:Date):void {
            _lastUpdated = value;
        }
        public function get lastUpdated():Date {
            return _lastUpdated;
        }

        public function set locationGroupCountingActive(value:Boolean):void {
            _locationGroupCountingActive = value;
        }
        public function get locationGroupCountingActive():Boolean {
            return _locationGroupCountingActive;
        }

        public function get locationGroups():ListCollectionView {
            return _locationGroups;
        }

        public function get locations():ListCollectionView {
            return _locations;
        }

        public function set maxFillLevel(value:Number):void {
            _maxFillLevel = value;
        }
        public function get maxFillLevel():Number {
            return _maxFillLevel;
        }

        public function set name(value:String):void {
            _name = value;
        }
        public function get name():String {
            return _name;
        }

        public function get noLocations():int {
            return _noLocations;
        }

        public function set parent(value:LocationGroup):void {
            _parent = value;
        }
        public function get parent():LocationGroup {
            return _parent;
        }

        public function set systemCode(value:String):void {
            _systemCode = value;
        }
        public function get systemCode():String {
            return _systemCode;
        }

        public function get version():Number {
            return _version;
        }

        public function readExternal(input:IDataInput):void {
            __initialized = input.readObject() as Boolean;
            __detachedState = input.readObject() as String;
            if (meta::isInitialized()) {
                _description = input.readObject() as String;
                _groupStateIn = Enum.readEnum(input) as LocationGroup$STATE;
                _groupStateOut = Enum.readEnum(input) as LocationGroup$STATE;
                _groupType = input.readObject() as String;
                _id = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
                _lastUpdated = input.readObject() as Date;
                _locationGroupCountingActive = input.readObject() as Boolean;
                _locationGroups = input.readObject() as ListCollectionView;
                _locations = input.readObject() as ListCollectionView;
                _maxFillLevel = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
                _name = input.readObject() as String;
                _noLocations = input.readObject() as int;
                _parent = input.readObject() as LocationGroup;
                _systemCode = input.readObject() as String;
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
                output.writeObject(_groupStateIn);
                output.writeObject(_groupStateOut);
                output.writeObject(_groupType);
                output.writeObject(_id);
                output.writeObject(_lastUpdated);
                output.writeObject(_locationGroupCountingActive);
                output.writeObject(_locationGroups);
                output.writeObject(_locations);
                output.writeObject(_maxFillLevel);
                output.writeObject(_name);
                output.writeObject(_noLocations);
                output.writeObject(_parent);
                output.writeObject(_systemCode);
                output.writeObject(_version);
            }
            else {
                output.writeObject(_id);
            }
        }
    }
}