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

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * A definition of any kind of unit used in the application. In general, Units
 * are defined by a particular type of <code>UnitType</code> and a value. For
 * example 42 grams is a weight, whereas weight is the Unit.
 * 
 * @GlossaryTerm
 * @param <T>
 *            Type of Unit
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@MappedSuperclass
public abstract class Unit<T extends UnitType<T>> {

    @Column(name = "C_QUANTITY", length = CoreTypeDefinitions.QUANTITY_LENGTH, nullable = false)
    private String quantity;

    /**
     * Convert this unit into another <code>Unit</code>.
     * 
     * @param unit
     *            The unit to convert to
     */
    public abstract Unit<T> convertTo(T unit);

    /**
     * Returns the unit of the <code>Piece</code>.
     * 
     * @return The unit
     */
    public abstract UnitType<T> getUnit();

    /**
     * Get the quantity.
     * 
     * @return the quantity.
     */
    protected String getQuantity() {
        return quantity;
    }

    /**
     * Set the quantity.
     * 
     * @param quantity
     *            The quantity to set.
     */
    protected void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}