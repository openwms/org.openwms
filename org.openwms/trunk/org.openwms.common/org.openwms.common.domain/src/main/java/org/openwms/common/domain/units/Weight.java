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
package org.openwms.common.domain.units;

import java.io.Serializable;
import java.math.BigDecimal;

import org.openwms.core.domain.values.Unit;

/**
 * A Weight represents a real world weight, that comes with an <code>Unit</code>
 * and a value.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public class Weight extends Unit<Weight, WeightUnit> implements Comparable<Weight>, Serializable {
    private static final long serialVersionUID = -8849107834046064278L;

    /** The unit of the <code>Weight</code>. */
    private WeightUnit unit;
    /** The amount of the <code>Weight</code>. */
    private BigDecimal amount;
    /** Constant for a zero value. */
    public static final Weight ZERO = new Weight("0");

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    protected Weight() {
        super();
    }

    /**
     * Create a new <code>Weight</code>.
     * 
     * @param amount
     *            The amount of the <code>Weight</code>
     * @param unit
     *            The unit of measure
     */
    public Weight(BigDecimal amount, WeightUnit unit) {
        this.amount = amount;
        this.unit = unit;
    }

    /**
     * Create a new <code>Weight</code>.
     * 
     * @param amount
     *            The amount of the <code>Weight</code> as String
     * @param unit
     *            The unit of measure
     */
    public Weight(String amount, WeightUnit unit) {
        this.amount = new BigDecimal(amount);
        this.unit = unit;
    }

    /**
     * Create a new <code>Weight</code>.
     * 
     * @param amount
     *            The amount of the <code>Weight</code>
     */
    public Weight(BigDecimal amount) {
        this.amount = amount;
        this.unit = WeightUnit.T.getBaseUnit();
    }

    /**
     * Create a new <code>Weight</code>.
     * 
     * @param amount
     *            The amount of the <code>Weight</code> as String
     */
    public Weight(String amount) {
        this.amount = new BigDecimal(amount);
        this.unit = WeightUnit.T.getBaseUnit();
    }

    /**
     * Returns the amount of the <code>Weight</code>.
     * 
     * @return The amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Returns the unit of the <code>Weight</code>.
     * 
     * @return The unit
     */
    @Override
    public WeightUnit getUnitType() {
        return unit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isZero() {
        return this.getAmount().equals(BigDecimal.ZERO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNegative() {
        return this.getAmount().signum() == -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Weight convertTo(WeightUnit unt) {
        return new Weight(getAmount().scaleByPowerOfTen((this.getUnitType().ordinal() - unt.ordinal()) * 3), unt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Weight o) {
        if (o.getUnitType().ordinal() > this.getUnitType().ordinal()) {
            return -1;
        } else if (o.getUnitType().ordinal() < this.getUnitType().ordinal()) {
            return 1;
        } else {
            return this.getAmount().compareTo(o.getAmount());
        }
    }

    /**
     * Use amount and unit for calculation.
     * 
     * @return The hashCode
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getUnitType() == null) ? 0 : getUnitType().hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * Use amount and unit for comparison.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Weight other = (Weight) obj;
        if (getAmount() == null) {
            if (other.getAmount() != null) {
                return false;
            }
        } else if (!getAmount().equals(other.getAmount())) {
            return false;
        }
        if (getUnitType() != other.getUnitType()) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getAmount() + " " + getUnitType();
    }
}