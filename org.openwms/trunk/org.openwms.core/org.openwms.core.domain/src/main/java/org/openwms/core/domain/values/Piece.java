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

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * A Piece.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
@Embeddable
public class Piece extends Unit<PieceUnit> implements Comparable<Piece>, Serializable {
    private static final long serialVersionUID = 5268725227649308401L;

    /** The unit of the <code>Piece</code>. */
    @Transient
    private PieceUnit unit;
    /** The amount of the <code>Piece</code>. */
    @Transient
    private int amount;
    /** Constant for a zero value. */
    @Transient
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
     * @param unit
     *            The unit of measure
     */
    public Piece(int amount, PieceUnit unit) {
        this.amount = amount;
        this.unit = unit;
        prePersist();
    }

    /**
     * Create a new <code>Piece</code>.
     * 
     * @param amount
     *            The amount of the <code>Piece</code> as int
     */
    public Piece(int amount) {
        this.amount = amount;
        this.unit = PieceUnit.PC.getBaseUnit();
        prePersist();
    }

    /**
     * Returns the unit of the <code>Piece</code>.
     * 
     * @return The unit
     */
    @Override
    public PieceUnit getUnit() {
        if (this.unit == null) {
            postLoad();
        }
        return unit;
    }

    /**
     * Returns the amount of the <code>Piece</code>.
     * 
     * @return The amount
     */
    public int getAmount() {
        if (this.unit == null) {
            postLoad();
        }
        return amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Piece convertTo(PieceUnit unt) {
        if (PieceUnit.PC == unt && this.getUnit() == PieceUnit.DOZ) {
            return new Piece(this.getAmount() * 12, PieceUnit.PC);
        } else if (PieceUnit.DOZ == unt && this.getUnit() == PieceUnit.PC) {
            return new Piece(this.getAmount() / 12, PieceUnit.DOZ);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Piece o) {
        if (o.getUnit().ordinal() > this.getUnit().ordinal()) {
            return compare(this.getAmount(), o.getAmount() * 12);
        } else if (o.getUnit().ordinal() < this.getUnit().ordinal()) {
            return compare(this.getAmount() * 12, o.getAmount());
        } else if (this.getAmount() == o.getAmount()) {
            return 0;
        } else if (this.getAmount() < o.getAmount()) {
            return -1;
        }
        return 1;
    }

    /**
     * {@inheritDoc}
     * 
     * Return a combination of amount and unit, e.g. 24 PC
     */
    @Override
    public String toString() {
        return getAmount() + " " + getUnit();
    }

    // INFO [scherrer] : JPA Lifecycle methods do not work in JPA1.0
    private void prePersist() {
        setQuantity(String.valueOf(this.amount) + " " + this.unit.toString());
    }

    // INFO [scherrer] : JPA Lifecycle methods do not work in JPA1.0
    private void postLoad() {
        String val = getQuantity();
        this.amount = new Integer(val.substring(0, val.indexOf(" ")));
        this.unit = PieceUnit.valueOf(val.substring(val.indexOf(" ") + 1, val.length()));
    }

    private int compare(int val1, int val2) {
        if (val1 == val2) {
            return 0;
        };
        return val1 < val2 ? -1 : 1;
    }
}