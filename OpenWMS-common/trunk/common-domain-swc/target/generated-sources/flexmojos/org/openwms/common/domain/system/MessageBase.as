/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain.system {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import flash.utils.IExternalizable;
    import org.granite.collections.IPersistentCollection;
    import org.granite.meta;

    use namespace meta;

    [Bindable]
    public class MessageBase implements IExternalizable {

        private var __initialized:Boolean = true;
        private var __detachedState:String = null;

        private var _created:Date;
        private var _id:Number;
        private var _messageNo:int;
        private var _messageText:String;

        meta function isInitialized(name:String = null):Boolean {
            if (!name)
                return __initialized;

            var property:* = this[name];
            return (
                (!(property is Message) || (property as Message).meta::isInitialized()) &&
                (!(property is IPersistentCollection) || (property as IPersistentCollection).isInitialized())
            );
        }

        public function get created():Date {
            return _created;
        }

        public function get id():Number {
            return _id;
        }

        public function set messageNo(value:int):void {
            _messageNo = value;
        }
        public function get messageNo():int {
            return _messageNo;
        }

        public function set messageText(value:String):void {
            _messageText = value;
        }
        public function get messageText():String {
            return _messageText;
        }

        public function readExternal(input:IDataInput):void {
            __initialized = input.readObject() as Boolean;
            __detachedState = input.readObject() as String;
            if (meta::isInitialized()) {
                _created = input.readObject() as Date;
                _id = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
                _messageNo = input.readObject() as int;
                _messageText = input.readObject() as String;
            }
            else {
                _id = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
            }
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(__initialized);
            output.writeObject(__detachedState);
            if (meta::isInitialized()) {
                output.writeObject(_created);
                output.writeObject(_id);
                output.writeObject(_messageNo);
                output.writeObject(_messageText);
            }
            else {
                output.writeObject(_id);
            }
        }
    }
}