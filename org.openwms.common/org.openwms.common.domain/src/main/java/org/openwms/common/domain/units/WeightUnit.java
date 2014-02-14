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
package org.openwms.common.domain.units;

import java.util.Arrays;
import java.util.List;

import org.openwms.core.domain.values.BaseUnit;

/**
 * A WeightUnit is a concrete set of all possible weights.
 * <p>
 * In SI format.
 * </p>
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public enum WeightUnit implements BaseUnit<WeightUnit> {

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

    private Long magnitude;
    private static WeightUnit[] all = { WeightUnit.MG, WeightUnit.G, WeightUnit.KG, WeightUnit.T };

    /**
     * Create a new <code>WeightUnit</code>.
     * 
     * @param magnitude
     *            The base unit of the weight
     */
    WeightUnit(long magnitude) {
        this.magnitude = magnitude;
    }

    /**
     * Get the magnitude of this <code>PieceUnit</code>.
     * 
     * @return the magnitude
     */
    public Long getMagnitude() {
        return this.magnitude;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<WeightUnit> getAll() {
        return Arrays.asList(all);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WeightUnit getBaseUnit() {
        return G;
    }
}