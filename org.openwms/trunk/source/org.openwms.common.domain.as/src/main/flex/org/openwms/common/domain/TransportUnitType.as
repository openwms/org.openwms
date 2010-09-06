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
package org.openwms.common.domain {

    import mx.collections.ArrayCollection;

    [Bindable]
    [RemoteClass(alias="org.openwms.common.domain.TransportUnitType")]
    public class TransportUnitType extends TransportUnitTypeBase {
    	
    	public function TransportUnitType():void
    	{
    		if (this._typePlacingRules == null)
    		{
    			this._typePlacingRules = new ArrayCollection();
    		}
            if (this._typeStackingRules == null)
            {
                this._typeStackingRules = new ArrayCollection();
            }
    	}

        public function toString():String
        {
            return this._type;
        }
        
        public function deepCopy():TransportUnitType
        {
        	var copy:TransportUnitType = new TransportUnitType();
        	copy._type = this._type;
        	copy._description = this._description;
        	copy._compatibility = this._compatibility;
        	copy._height = this._height;
        	copy._length = this._length;
        	copy._payload = this._payload;
        	copy._typePlacingRules = this._typePlacingRules;
        	copy._typeStackingRules = this._typeStackingRules;
        	copy._version = this._version;
        	copy._weightMax = this._weightMax;
        	copy._weightTare = this._weightTare;
        	copy._width = this._width;
        	return copy;
        }
    }
}