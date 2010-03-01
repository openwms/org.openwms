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
package org.openwms.common.domain.values;

/**
 * A WeightUnit.
 * <p>
 * In SI format.
 * </p>
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public enum WeightUnit {

    /**
     * Milligram.
     */
    MG(1000000000),
    /**
     * Gram.
     */
    G(1000000),
    /**
     * Kilogram.
     */
    KG(1000),
    /**
     * Tons.
     */
    T(1);

    private Long baseUnit;

    /**
     * Create a new WeightUnit.
     * 
     * @param baseUnit
     *            The base unit of the weight
     */
    WeightUnit(long baseUnit) {
        this.baseUnit = baseUnit;
    }

    /**
     * Return the base unit.
     * 
     * @return The base unit of the weight
     */
    public long getBaseUnit() {
        return this.baseUnit;
    }
}
