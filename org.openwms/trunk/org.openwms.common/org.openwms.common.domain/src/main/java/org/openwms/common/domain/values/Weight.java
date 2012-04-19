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
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import org.openwms.core.domain.values.Unit;

/**
 * A Weight represents a real world weight, that comes with an <code>Unit</code>
 * and a value.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Embeddable
public class Weight extends Unit<WeightUnit> implements Comparable<Weight>, Serializable {

    private static final long serialVersionUID = -8849107834046064278L;

    /** The unit of the <code>Weight</code>. */
    @Transient
    private WeightUnit unit;
    /** The amount of the <code>Weight</code>. */
    @Transient
    private BigDecimal value;

    public static final Weight ZERO = new Weight("0");

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    @SuppressWarnings("unused")
    private Weight() {
        super();
    }

    @PreUpdate
    @PrePersist
    void preUpdate() {
        setQuantity(this.value.toString() + " " + this.unit.toString());
    }

    @PostLoad
    void postLoad() {
        String val = getQuantity();
        this.value = new BigDecimal(val.substring(0, val.indexOf(" ")));
        this.unit = WeightUnit.valueOf(val.substring(val.indexOf(" "), val.length()));
    }

    /**
     * Create a new <code>Weight</code>.
     * 
     * @param value
     *            The value of the <code>Weight</code>
     * @param unit
     *            The unit of measure
     */
    public Weight(BigDecimal value, WeightUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    /**
     * Create a new <code>Weight</code>.
     * 
     * @param value
     *            The value of the <code>Weight</code> as String
     * @param unit
     *            The unit of measure
     */
    public Weight(String value, WeightUnit unit) {
        this.value = new BigDecimal(value);
        this.unit = unit;
    }

    /**
     * Create a new <code>Weight</code>.
     * 
     * @param value
     *            The value of the <code>Weight</code> as String
     */
    public Weight(String value) {
        this.value = new BigDecimal(value);
        this.unit = WeightUnit.T.getBaseUnit();
    }

    /**
     * Create a new <code>Weight</code>.
     * 
     * @param value
     *            The value of the <code>Weight</code> as double
     * @param unit
     *            The unit of measure
     */
    public Weight(double value, WeightUnit unit) {
        this.value = new BigDecimal(value);
        this.unit = unit;
    }

    /**
     * Returns the unit of the <code>Weight</code>.
     * 
     * @return The unit.
     */
    public WeightUnit getUnit() {
        return unit;
    }

    /**
     * Returns the value of the <code>Weight</code>.
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
    public void convertTo(WeightUnit unt) {
        value = value.scaleByPowerOfTen((this.getUnit().ordinal() - unt.ordinal()) * 3);
        this.unit = unt;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Weight o) {
        if (o.getUnit().ordinal() > this.getUnit().ordinal()) {
            return -1;
        } else if (o.getUnit().ordinal() < this.getUnit().ordinal()) {
            return 1;
        } else {
            return this.getValue().compareTo(o.getValue());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return value + " " + unit;
    }
}