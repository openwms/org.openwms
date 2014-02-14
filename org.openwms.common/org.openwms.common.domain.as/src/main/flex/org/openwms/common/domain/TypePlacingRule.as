/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
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
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.common.domain {

    [Bindable]
    [RemoteClass(alias="org.openwms.common.domain.TypePlacingRule")]
    /**
     * A TypePlacingRule defines which <code>TransportUnitType</code>s can be
     * located on which <code>LocationType</code>s.
     * <p>
     * A privilegeLevel defines the order of all allowed <code>LocationType</code>s.
     * </p>
     * 
     * @version $Revision$
     * @since 0.1
     * @see org.openwms.common.domain.TransportUnitType
     */
    public class TypePlacingRule extends TypePlacingRuleBase {
    	
        /**
         * Add a <code>TransportUnitType</code> to this rule.
         *
         * @param transportUnitType The type to add
         */
    	public function withTransportUnitType(transportUnitType:TransportUnitType):void {
    		this._transportUnitType = transportUnitType;
    	}
    	
        /**
         * Add a <code>LocationType</code> to this rule.
         *
         * @param locationType The type to add
         */
    	public function withLocationType(locationType:LocationType):void {
    		this._allowedLocationType = locationType;
    	}
    }
}