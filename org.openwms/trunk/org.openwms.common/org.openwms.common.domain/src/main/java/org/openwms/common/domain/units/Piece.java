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
import java.math.RoundingMode;

import org.openwms.core.domain.values.Unit;

/**
 * A Piece.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
public class Piece extends Unit<Piece, PieceUnit> implements Comparable<Piece>, Serializable {

    private static final long serialVersionUID = 5268725227649308401L;
    private static final BigDecimal SHIFTER = new BigDecimal(12);

    /** The unit of the <code>Piece</code>. */
    private PieceUnit unitType;
    /** The amount of the <code>Piece</code>. */
    private BigDecimal amount;
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
     * Create a new <code>Piece</code>.
     * 
     * @param amount
     *            The amount of the <code>Piece</code>
     * @param unitType
     *            The unit of measure
     */
    public Piece(int amount, PieceUnit unitType) {
        this.amount = new BigDecimal(amount);
        this.unitType = unitType;
        // prePersist();
    }

    /**
     * Create a new <code>Piece</code>.
     * 
     * @param amount
     *            The amount of the <code>Piece</code> as int
     */
    public Piece(int amount) {
        this.amount = new BigDecimal(amount);
        this.unitType = PieceUnit.PC.getBaseUnit();
        // prePersist();
    }

    /**
     * Create a new <code>Piece</code>.
     * 
     * @param amount
     *            The amount of the <code>Piece</code>
     * @param unitType
     *            The unit of measure
     */
    public Piece(BigDecimal amount, PieceUnit unitType) {
        this.amount = amount;
        this.unitType = unitType;
        // prePersist();
    }

    /**
     * Create a new <code>Piece</code>.
     * 
     * @param amount
     *            The amount of the <code>Piece</code> as BigDecimal
     */
    public Piece(BigDecimal amount) {
        this.amount = amount;
        this.unitType = PieceUnit.PC.getBaseUnit();
        // prePersist();
    }

    /**
     * Returns the amount of the <code>Piece</code>.
     * 
     * @return The amount
     */
    public BigDecimal getAmount() {
        if (this.unitType == null) {
            // postLoad();
        }
        return amount;
    }

    /**
     * Returns the unit of the <code>Piece</code>.
     * 
     * @param <T>
     * 
     * @return The unit
     */
    @Override
    public PieceUnit getUnitType() {
        if (this.unitType == null) {
            // postLoad();
        }
        return unitType;
    }

    /**
     * @see org.openwms.core.domain.values.Unit#isZero()
     */
    @Override
    public boolean isZero() {
        return Piece.ZERO.equals(new Piece(this.getAmount(), PieceUnit.DOZ));
    }

    /**
     * @see org.openwms.core.domain.values.Unit#isNegative()
     */
    @Override
    public boolean isNegative() {
        return this.getAmount().signum() == -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Piece convertTo(PieceUnit unt) {
        if (PieceUnit.PC == unt && this.getUnitType() == PieceUnit.DOZ) {
            return new Piece(this.getAmount().multiply(SHIFTER), PieceUnit.PC);
        } else if (PieceUnit.DOZ == unt && this.getUnitType() == PieceUnit.PC) {
            return new Piece(this.getAmount().divide(SHIFTER, 0, RoundingMode.DOWN), PieceUnit.DOZ);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Piece o) {
        if (o.getUnitType().ordinal() > this.getUnitType().ordinal()) {
            return compare(this.getAmount(), o.getAmount().multiply(SHIFTER));
        } else if (o.getUnitType().ordinal() < this.getUnitType().ordinal()) {
            return compare(this.getAmount().multiply(SHIFTER), o.getAmount());
        }
        return this.getAmount().compareTo(o.getAmount());
    }

    /**
     * {@inheritDoc}
     * 
     * Return a combination of amount and unit, e.g. 24 PC
     */
    @Override
    public String toString() {
        return getAmount() + " " + getUnitType();
    }

    // INFO [scherrer] : JPA Lifecycle methods do not work in JPA1.0
    /*
     * private void prePersist() { setQuantity(String.valueOf(this.amount) + " "
     * + this.unitType.toString()); }
     * 
     * private void postLoad() { String val = getQuantity(); this.amount = new
     * BigDecimal(val.substring(0, val.indexOf(" "))); this.unitType =
     * PieceUnit.valueOf(val.substring(val.indexOf(" ") + 1, val.length())); }
     */

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((amount == null) ? 0 : amount.hashCode());
        result = prime * result + ((unitType == null) ? 0 : unitType.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
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
        if (amount == null) {
            if (other.amount != null) {
                return false;
            }
        }
        return this.compareTo(other) == 0 ? true : false;

    }

    private int compare(BigDecimal val1, BigDecimal val2) {
        if (val1 == val2) {
            return 0;
        };
        return val1.compareTo(val2);
    }
}