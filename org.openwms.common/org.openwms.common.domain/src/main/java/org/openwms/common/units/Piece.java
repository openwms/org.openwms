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
package org.openwms.common.units;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.openwms.core.domain.values.Measurable;
import org.openwms.core.domain.values.UnitType;

/**
 * A Piece.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
public class Piece implements Measurable<BigDecimal, Piece, PieceUnit>, UnitType, Serializable {

    private static final long serialVersionUID = 5268725227649308401L;
    private static final BigDecimal SHIFTER = new BigDecimal(12);

    /** The unit of the <code>Piece</code>. */
    private PieceUnit unitType;
    /** The magnitude of the <code>Piece</code>. */
    private BigDecimal magnitude;
    /** Constant for a zero value. */
    public static final Piece ZERO = new Piece(0);

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    protected Piece() {
        super();
    }

    /**
     * @see org.openwms.core.domain.values.UnitType#getMeasurable()
     */
    @Override
    public Piece getMeasurable() {
        return this;
    }

    /**
     * Create a new <code>Piece</code>.
     * 
     * @param magnitude
     *            The magnitude of the <code>Piece</code>
     * @param unitType
     *            The unit of measure
     */
    public Piece(int magnitude, PieceUnit unitType) {
        this.magnitude = new BigDecimal(magnitude);
        this.unitType = unitType;
    }

    /**
     * Create a new <code>Piece</code>.
     * 
     * @param magnitude
     *            The magnitude of the <code>Piece</code> as int
     */
    public Piece(int magnitude) {
        this.magnitude = new BigDecimal(magnitude);
        this.unitType = PieceUnit.PC.getBaseUnit();
    }

    /**
     * Create a new <code>Piece</code>.
     * 
     * @param magnitude
     *            The magnitude of the <code>Piece</code>
     * @param unitType
     *            The unit of measure
     */
    public Piece(BigDecimal magnitude, PieceUnit unitType) {
        this.magnitude = magnitude;
        this.unitType = unitType;
    }

    /**
     * Create a new <code>Piece</code>.
     * 
     * @param magnitude
     *            The magnitude of the <code>Piece</code> as BigDecimal
     */
    public Piece(BigDecimal magnitude) {
        this.magnitude = magnitude;
        this.unitType = PieceUnit.PC.getBaseUnit();
    }

    /**
     * Returns the magnitude of the <code>Piece</code>.
     * 
     * @return The magnitude
     */
    @Override
    public BigDecimal getMagnitude() {
        return magnitude;
    }

    /**
     * Returns the unit of the <code>Piece</code>.
     * 
     * @return The unit
     */
    @Override
    public PieceUnit getUnitType() {
        return unitType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isZero() {
        return Piece.ZERO.equals(new Piece(this.getMagnitude(), PieceUnit.DOZ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNegative() {
        return this.getMagnitude().signum() == -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Piece convertTo(PieceUnit unt) {
        if (PieceUnit.PC == unt && this.getUnitType() == PieceUnit.DOZ) {
            return new Piece(this.getMagnitude().multiply(SHIFTER), PieceUnit.PC);
        } else if (PieceUnit.DOZ == unt && this.getUnitType() == PieceUnit.PC) {
            return new Piece(this.getMagnitude().divide(SHIFTER, 0, RoundingMode.DOWN), PieceUnit.DOZ);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Piece o) {
        if (null == o) {
            return -1;
        }
        if (o.getUnitType().ordinal() > this.getUnitType().ordinal()) {
            return compare(this.getMagnitude(), o.getMagnitude().multiply(SHIFTER));
        } else if (o.getUnitType().ordinal() < this.getUnitType().ordinal()) {
            return compare(this.getMagnitude().multiply(SHIFTER), o.getMagnitude());
        }
        return this.getMagnitude().compareTo(o.getMagnitude());
    }

    /**
     * {@inheritDoc}
     * 
     * Return a combination of amount and unit, e.g. 24 PC
     */
    @Override
    public String toString() {
        return getMagnitude() + " " + getUnitType();
    }

    /**
     * {@inheritDoc}
     * 
     * Uses magnitude and unitType for calculation.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((magnitude == null) ? 0 : magnitude.hashCode());
        result = prime * result + ((unitType == null) ? 0 : unitType.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * Uses magnitude and unitType for comparison.
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
        Piece other = (Piece) obj;
        if (magnitude == null) {
            if (other.magnitude != null) {
                return false;
            }
        }
        return this.compareTo(other) == 0 ? true : false;

    }

    private int compare(BigDecimal val1, BigDecimal val2) {
        if (val1 == val2) {
            return 0;
        }
        return val1.compareTo(val2);
    }
}