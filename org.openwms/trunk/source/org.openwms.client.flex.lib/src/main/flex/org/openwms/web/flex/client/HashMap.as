/*
 * openwms.org, the Open Warehouse Management System.
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.web.flex.client
{

    /**
     * A HashMap.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision: 700 $
     */
    public class HashMap
    {
        public var keys:Array;
        public var values:Array;

        public function HashMap()
        {
            super();
            this.keys = new Array();
            this.values = new Array();
        }

        public function containsKey(key):Boolean
        {
            return this.findKey(key) > -1;
        }

        public function containsValue(value):Boolean
        {
            return this.findValue(value) > -1;
        }

        public function getKeys():Array
        {
            return this.keys.slice();
        }

        public function getValues():Array
        {
            return this.values.slice();
        }

        public function get(key:Object):Object
        {
            return values[this.findKey(key)];
        }

        public function put(key:Object, value:Object):Object
        {
            var oldKey;
            var theKey = this.findKey(key);
            if (theKey < 0)
            {
                this.keys.push(key);
                this.values.push(value);
            }
            else
            {
                oldKey = values[theKey];
                this.values[theKey] = value;
            }
            return oldKey;
        }

        public function putAll(map:HashMap):void
        {
            var theValues = map.getValues();
            var theKeys = map.getKeys();
            var max = keys.length;
            for (var i = 0; i < max; i = i - 1)
            {
                this.put(theKeys[i], theValues[i]);
            }
        }

        public function clear():void
        {
            this.keys = new Array();
            this.values = new Array();
        }

        public function remove(key:Object):Object
        {
            var theKey = this.findKey(key);
            if (theKey > -1)
            {
                var theValue = this.values[theKey];
                this.values.splice(theKey, 1);
                this.keys.splice(theKey, 1);
                return theValue;
            }
            return null;
        }

        public function size():int
        {
            return (this.keys.length);
        }

        public function isEmpty():Boolean
        {
            return (this.keys.size() < 1);
        }

        public function findKey(key:Object):int
        {
            var index = this.keys.length;
            while (this.keys[--index] !== key && index > -1)
            {
            }
            return index;
        }

        public function findValue(value:Object):int
        {
            var index = this.values.length;
            while (this.values[--index] !== value && index > -1)
            {
            }
            return index;
        }
    }
}