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

    use namespace meta;

    [Bindable]
    public class LocationBase implements IExternalizable {

        private var __initialized:Boolean = true;
        private var __detachedState:String = null;

        private var _checkState:String;
        private var _consideredInAllocation:Boolean;
        private var _countingActive:Boolean;
        private var _description:String;
        private var _id:Number;
        private var _incomingActive:Boolean;
        private var _lastAccess:Date;
        private var _locationGroup:LocationGroup;
        private var _locationGroupCountingActive:Boolean;
        private var _locationId:LocationPK;
        private var _locationType:LocationType;
        private var _maximumWeight:Number;
        private var _messages:ListCollectionView;
        private var _noMaxTransportUnits:int;
        private var _outgoingActive:Boolean;
        private var _plcState:int;
        private var _version:Number;

        meta function isInitialized(name:String = null):Boolean {
            if (!name)
                return __initialized;

            var property:* = this[name];
            return (
                (!(property is Location) || (property as Location).meta::isInitialized()) &&
                (!(property is IPersistentCollection) || (property as IPersistentCollection).isInitialized())
            );
        }

        public function set checkState(value:String):void {
            _checkState = value;
        }
        public function get checkState():String {
            return _checkState;
        }

        public function set consideredInAllocation(value:Boolean):void {
            _consideredInAllocation = value;
        }
        public function get consideredInAllocation():Boolean {
            return _consideredInAllocation;
        }

        public function set countingActive(value:Boolean):void {
            _countingActive = value;
        }
        public function get countingActive():Boolean {
            return _countingActive;
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

        public function set incomingActive(value:Boolean):void {
            _incomingActive = value;
        }
        public function get incomingActive():Boolean {
            return _incomingActive;
        }

        public function set lastAccess(value:Date):void {
            _lastAccess = value;
        }
        public function get lastAccess():Date {
            return _lastAccess;
        }

        public function set locationGroup(value:LocationGroup):void {
            _locationGroup = value;
        }
        public function get locationGroup():LocationGroup {
            return _locationGroup;
        }

        public function set locationGroupCountingActive(value:Boolean):void {
            _locationGroupCountingActive = value;
        }
        public function get locationGroupCountingActive():Boolean {
            return _locationGroupCountingActive;
        }

        public function get locationId():LocationPK {
            return _locationId;
        }

        public function set locationType(value:LocationType):void {
            _locationType = value;
        }
        public function get locationType():LocationType {
            return _locationType;
        }

        public function set maximumWeight(value:Number):void {
            _maximumWeight = value;
        }
        public function get maximumWeight():Number {
            return _maximumWeight;
        }

        public function get messages():ListCollectionView {
            return _messages;
        }

        public function set noMaxTransportUnits(value:int):void {
            _noMaxTransportUnits = value;
        }
        public function get noMaxTransportUnits():int {
            return _noMaxTransportUnits;
        }

        public function set outgoingActive(value:Boolean):void {
            _outgoingActive = value;
        }
        public function get outgoingActive():Boolean {
            return _outgoingActive;
        }

        public function set plcState(value:int):void {
            _plcState = value;
        }
        public function get plcState():int {
            return _plcState;
        }

        public function get version():Number {
            return _version;
        }

        public function readExternal(input:IDataInput):void {
            __initialized = input.readObject() as Boolean;
            __detachedState = input.readObject() as String;
            if (meta::isInitialized()) {
                _checkState = input.readObject() as String;
                _consideredInAllocation = input.readObject() as Boolean;
                _countingActive = input.readObject() as Boolean;
                _description = input.readObject() as String;
                _id = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
                _incomingActive = input.readObject() as Boolean;
                _lastAccess = input.readObject() as Date;
                _locationGroup = input.readObject() as LocationGroup;
                _locationGroupCountingActive = input.readObject() as Boolean;
                _locationId = input.readObject() as LocationPK;
                _locationType = input.readObject() as LocationType;
                _maximumWeight = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
                _messages = input.readObject() as ListCollectionView;
                _noMaxTransportUnits = input.readObject() as int;
                _outgoingActive = input.readObject() as Boolean;
                _plcState = input.readObject() as int;
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
                output.writeObject(_checkState);
                output.writeObject(_consideredInAllocation);
                output.writeObject(_countingActive);
                output.writeObject(_description);
                output.writeObject(_id);
                output.writeObject(_incomingActive);
                output.writeObject(_lastAccess);
                output.writeObject(_locationGroup);
                output.writeObject(_locationGroupCountingActive);
                output.writeObject(_locationId);
                output.writeObject(_locationType);
                output.writeObject(_maximumWeight);
                output.writeObject(_messages);
                output.writeObject(_noMaxTransportUnits);
                output.writeObject(_outgoingActive);
                output.writeObject(_plcState);
                output.writeObject(_version);
            }
            else {
                output.writeObject(_id);
            }
        }
    }
}