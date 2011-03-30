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
package org.openwms.core.service;

import java.util.List;

import org.openwms.core.domain.system.usermanagement.Preference;
import org.openwms.core.domain.values.Unit;

/**
 * A ConfigurationService is responsible to handle all application properties.
 * Whereby properties have particular defined scopes, e.g. some properties have
 * a global scope which means Application Scope and some others are only valid
 * for a certain Module.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.domain.system.PropertyScope
 */
public interface ConfigurationService {

    /**
     * Find and return all properties in Application Scope.
     * 
     * @return a list of these properties
     */
    List<Preference> findApplicationProperties();

    /**
     * Find and return all properties belonging to this Module.
     * 
     * @return a list of these properties
     */
    List<Preference> findModuleProperties();

    /**
     * Get all unit types supported by this Module.
     * 
     * @return A list of these units
     */
    List<? extends Unit> getAllUnits();
}
