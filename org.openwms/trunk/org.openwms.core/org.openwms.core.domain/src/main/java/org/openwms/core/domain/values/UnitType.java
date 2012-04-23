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
package org.openwms.core.domain.values;

import java.util.List;

/**
 * An UnitType is the type definition of an <code>Unit</code>. Each UnitType
 * defines a base <code>Unit</code> of it's character. For example a UnitType of
 * weights can define grams, kilograms or tons.
 * 
 * @GlossaryTerm
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public interface UnitType<T extends UnitType<T, V>, V> {

    /**
     * Return the <i>amount</i> of the base unit.
     * 
     * @return The <i>amount</i> of the base unit
     */
    V getBaseUnitValue();

    /**
     * Return all sub types of the <code>UnitType</code>.
     * 
     * @return a list of sub types
     */
    List<T> getAll();

    /**
     * Return the base unit type of the <code>UnitType</code>.
     * 
     * @return The base unit type
     */
    T getBaseUnit();
}