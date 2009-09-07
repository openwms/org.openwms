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
    public class TransportUnitTypeBase implements IExternalizable {

        private var __initialized:Boolean = true;
        private var __detachedState:String = null;

        private var _compatibility:String;
        private var _description:String;
        private var _height:int;
        private var _length:int;
        private var _payload:Number;
        private var _transportUnits:ListCollectionView;
        private var _type:String;
        private var _typePlacingRules:ListCollectionView;
        private var _typeStackingRules:ListCollectionView;
        private var _version:Number;
        private var _weightMax:Number;
        private var _weightTare:Number;
        private var _width:int;

        meta function isInitialized(name:String = null):Boolean {
            if (!name)
                return __initialized;

            var property:* = this[name];
            return (
                (!(property is TransportUnitType) || (property as TransportUnitType).meta::isInitialized()) &&
                (!(property is IPersistentCollection) || (property as IPersistentCollection).isInitialized())
            );
        }

        public function set compatibility(value:String):void {
            _compatibility = value;
        }
        public function get compatibility():String {
            return _compatibility;
        }

        public function set description(value:String):void {
            _description = value;
        }
        public function get description():String {
            return _description;
        }

        public function set height(value:int):void {
            _height = value;
        }
        public function get height():int {
            return _height;
        }

        public function set length(value:int):void {
            _length = value;
        }
        public function get length():int {
            return _length;
        }

        public function set payload(value:Number):void {
            _payload = value;
        }
        public function get payload():Number {
            return _payload;
        }

        public function set transportUnits(value:ListCollectionView):void {
            _transportUnits = value;
        }
        public function get transportUnits():ListCollectionView {
            return _transportUnits;
        }

        public function set type(value:String):void {
            _type = value;
        }
        public function get type():String {
            return _type;
        }

        public function set typePlacingRules(value:ListCollectionView):void {
            _typePlacingRules = value;
        }
        public function get typePlacingRules():ListCollectionView {
            return _typePlacingRules;
        }

        public function set typeStackingRules(value:ListCollectionView):void {
            _typeStackingRules = value;
        }
        public function get typeStackingRules():ListCollectionView {
            return _typeStackingRules;
        }

        public function get version():Number {
            return _version;
        }

        public function set weightMax(value:Number):void {
            _weightMax = value;
        }
        public function get weightMax():Number {
            return _weightMax;
        }

        public function set weightTare(value:Number):void {
            _weightTare = value;
        }
        public function get weightTare():Number {
            return _weightTare;
        }

        public function set width(value:int):void {
            _width = value;
        }
        public function get width():int {
            return _width;
        }

        public function readExternal(input:IDataInput):void {
            __initialized = input.readObject() as Boolean;
            __detachedState = input.readObject() as String;
            if (meta::isInitialized()) {
                _compatibility = input.readObject() as String;
                _description = input.readObject() as String;
                _height = input.readObject() as int;
                _length = input.readObject() as int;
                _payload = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
                _transportUnits = input.readObject() as ListCollectionView;
                _type = input.readObject() as String;
                _typePlacingRules = input.readObject() as ListCollectionView;
                _typeStackingRules = input.readObject() as ListCollectionView;
                _version = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
                _weightMax = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
                _weightTare = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
                _width = input.readObject() as int;
            }
            else {
                _type = input.readObject() as String;
            }
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(__initialized);
            output.writeObject(__detachedState);
            if (meta::isInitialized()) {
                output.writeObject(_compatibility);
                output.writeObject(_description);
                output.writeObject(_height);
                output.writeObject(_length);
                output.writeObject(_payload);
                output.writeObject(_transportUnits);
                output.writeObject(_type);
                output.writeObject(_typePlacingRules);
                output.writeObject(_typeStackingRules);
                output.writeObject(_version);
                output.writeObject(_weightMax);
                output.writeObject(_weightTare);
                output.writeObject(_width);
            }
            else {
                output.writeObject(_type);
            }
        }
    }
}