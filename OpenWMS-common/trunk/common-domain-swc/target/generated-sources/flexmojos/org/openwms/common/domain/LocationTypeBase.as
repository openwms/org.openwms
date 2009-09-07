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
    public class LocationTypeBase implements IExternalizable {

        private var __initialized:Boolean = true;
        private var __detachedState:String = null;

        private var _description:String;
        private var _height:int;
        private var _length:int;
        private var _locations:ListCollectionView;
        private var _type:String;
        private var _version:Number;
        private var _width:int;

        meta function isInitialized(name:String = null):Boolean {
            if (!name)
                return __initialized;

            var property:* = this[name];
            return (
                (!(property is LocationType) || (property as LocationType).meta::isInitialized()) &&
                (!(property is IPersistentCollection) || (property as IPersistentCollection).isInitialized())
            );
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

        public function set locations(value:ListCollectionView):void {
            _locations = value;
        }
        public function get locations():ListCollectionView {
            return _locations;
        }

        public function get type():String {
            return _type;
        }

        public function get version():Number {
            return _version;
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
                _description = input.readObject() as String;
                _height = input.readObject() as int;
                _length = input.readObject() as int;
                _locations = input.readObject() as ListCollectionView;
                _type = input.readObject() as String;
                _version = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
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
                output.writeObject(_description);
                output.writeObject(_height);
                output.writeObject(_length);
                output.writeObject(_locations);
                output.writeObject(_type);
                output.writeObject(_version);
                output.writeObject(_width);
            }
            else {
                output.writeObject(_type);
            }
        }
    }
}