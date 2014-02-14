/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.common.domain {

    import mx.collections.ArrayCollection;

    [Bindable]
    [RemoteClass(alias="org.openwms.common.domain.TransportUnitType")]
    /**
     * A TransportUnitType defines a type for <code>TransportUnit</code>s.
     * <p>
     * Typically stores static attributes of a <code>TransportUnit</code> such
     * as the length, the height, etc. So it's possible to group 
     * <code>TransportUnit</code>s with same characteristics,
     * </p>
     * 
     * @version $Revision$
     * @since 0.1
     * @see org.openwms.common.domain.TransportUnit
     */
    public class TransportUnitType extends TransportUnitTypeBase {
    	
        /**
         * Create a new <code>TransportUnitType</code> and initialize
         * collections.
         */
    	public function TransportUnitType():void {
    		if (this._typePlacingRules == null) {
    			this._typePlacingRules = new ArrayCollection();
    		}
            if (this._typeStackingRules == null) {
                this._typeStackingRules = new ArrayCollection();
            }
    	}

    	/**
    	 * Returns the type.
    	 *
    	 * @return The type
    	 */
        public function toString():String {
            return this._type;
        }
        
        /**
         * Does a deep copy and creates a new instance.
         *
         * @return a new instance of the <code>TransportUnitType</code>
         */
        public function deepCopy():TransportUnitType {
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