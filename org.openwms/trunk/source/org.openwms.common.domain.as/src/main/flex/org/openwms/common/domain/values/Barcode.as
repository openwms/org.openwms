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
package org.openwms.common.domain.values {

	/**
	 * A Barcode defines an unique label.
	 * 
	 * @version $Revision$
	 * @since 0.1
	 */
    [Bindable]
    [RemoteClass(alias="org.openwms.common.domain.values.Barcode")]
    public class Barcode extends BarcodeBase {
    	
    	/**
    	 * Create a new Barcode with a value.
    	 *
    	 * @param The new value
    	 */
    	public function Barcode(value:String = null):void {
    		super();
            if (value != null) {
                _value = value;
            }
    	}
    	
    	/**
    	 * Returns the value.
    	 *
    	 * @return the value
    	 */
        public function toString():String {
            return this.value;
        }
        
        /**
         * Check if the Barcode is null or empty.
         *
         * @return <code>true</code> if null or empty, otherwise <code>false</code>
         */
        public function isEmpty():Boolean {
        	return ((this.value == null||this.value.length == 0) ? true : false);
        }
    }
}