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

/**
 * A definition of any kind of unit used in the application. In general, Units
 * are defined by a particular type of <code>UnitType</code> and an amount. For
 * example <code>42 grams</code> is a <code>Weight</code>, whereas
 * <code>Weight</code> is the <code>Unit</code>, 42 is the amount and grams is
 * the <code>UnitType</code>.
 * 
 * @GlossaryTerm
 * @param <U>
 *            Concrete unit
 * @param <T>
 *            Type of Unit, an extension of <code>UnitType</code>
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public abstract class Unit<U extends Unit<U, T>, T extends UnitType<T, ?>> {

    /**
     * Convert this <code>Unit</code> into another <code>Unit</code>.
     * 
     * @param unit
     *            The <code>Unit</code> to convert to
     */
    public abstract U convertTo(T unit);

    /**
     * Returns the type of <code>Unit</code>.
     * 
     * @return The <code>Unit</code>'s type
     */
    public abstract T getUnitType();

    /**
     * Check whether the amount is set to 0.
     * 
     * @return <code>true</code> is amount is 0, otherwise <code>false</code>
     */
    public abstract boolean isZero();

    /**
     * Check whether the amount is of negative value.
     * 
     * @return <code>true</code> if the amount is of negative value, otherwise
     *         <code>false</code>
     */
    public abstract boolean isNegative();
}