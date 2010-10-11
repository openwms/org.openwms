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

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;

/**
 * A Weight - Describes a weight in real world, always including a unit and a
 * value.
 * 
 * @author <a href="mailto:scherrer@users.sourceforge.net">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Embeddable
public class Weight implements Comparable<Weight>, Unit<WeightUnit>, Serializable {

    /**
     * The serialVersionUID.
     */
    private static final long serialVersionUID = -8849107834046064278L;

    /**
     * Weights unit.
     */
    private WeightUnit unit;

    /**
     * Weight amount.
     */
    private BigDecimal value;

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    @SuppressWarnings("unused")
    private Weight() {}

    /**
     * Create a new Weight.
     * 
     * @param value
     *            The value of the weight
     * @param unit
     *            The unit of measure
     */
    public Weight(BigDecimal value, WeightUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    /**
     * Create a new Weight.
     * 
     * @param value
     *            The value of the weight as int
     * @param unit
     *            The unit of measure
     */
    public Weight(int value, WeightUnit unit) {
        this.value = new BigDecimal(value);
        this.unit = unit;
    }

    /**
     * Create a new Weight.
     * 
     * @param value
     *            The value of the weight as double
     * @param unit
     *            The unit of measure
     */
    public Weight(double value, WeightUnit unit) {
        this.value = new BigDecimal(value);
        this.unit = unit;
    }

    /**
     * Get the unit.
     * 
     * @return The unit.
     */
    public WeightUnit getUnit() {
        return unit;
    }

    /**
     * Get the value.
     * 
     * @return The value.
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void convertTo(WeightUnit unit) {
        value = value.scaleByPowerOfTen((this.getUnit().ordinal() - unit.ordinal()) * 3);
        this.unit = unit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Weight o) {
        if (o.getUnit().ordinal() > this.getUnit().ordinal()) {
            return 1;
        } else if (o.getUnit().ordinal() < this.getUnit().ordinal()) {
            return -1;
        } else {
            return o.getValue().compareTo(this.getValue());
        }
    }
}