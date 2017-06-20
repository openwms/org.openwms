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
package org.openwms.core.values;

/**
 * A Measurable.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
public interface Measurable<V extends Number, E extends Measurable<V, E, T>, T extends BaseUnit<T>> extends
        Comparable<E> {

    /**
     * Returns the type of <code>Measurable</code>.
     * 
     * @return The <code>Measurable</code>'s type
     */
    T getUnitType();

    /**
     * Get the magnitude of this <code>Measurable</code>.
     * 
     * @return the magnitude
     */
    V getMagnitude();

    /**
     * Check whether the magnitude is 0.
     * 
     * @return <code>true</code> is magnitude is 0, otherwise <code>false</code>
     */
    boolean isZero();

    /**
     * Check whether the magnitude is of negative value.
     * 
     * @return <code>true</code> if the magnitude is of negative value, otherwise <code>false</code>
     */
    boolean isNegative();

    /**
     * Convert this <code>Measurable</code> into another <code>Measurable</code> .
     * 
     * @param unit
     *            The <code>BaseUnit</code> to convert to
     */
    E convertTo(T unit);
}