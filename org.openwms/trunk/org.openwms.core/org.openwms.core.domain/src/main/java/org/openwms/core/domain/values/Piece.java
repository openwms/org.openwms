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

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

/**
 * A Piece.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
public class Piece extends Unit<PieceUnit> implements Comparable<Piece>, Serializable {
    private static final long serialVersionUID = 5268725227649308401L;

    /** The unit of the <code>Piece</code>. */
    @Transient
    private PieceUnit unit;
    /** The amount of the <code>Piece</code>. */
    @Transient
    private int value;

    public static final Piece ZERO = new Piece(0);

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    @SuppressWarnings("unused")
    private Piece() {
        super();
    }

    @PreUpdate
    @PrePersist
    void preUpdate() {
        setQuantity(String.valueOf(this.value) + " " + this.unit.toString());
    }

    @PostLoad
    void postLoad() {
        String val = getQuantity();
        this.value = new Integer(val.substring(0, val.indexOf(" ")));
        this.unit = PieceUnit.valueOf(val.substring(val.indexOf(" "), val.length()));
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
        this.value = amount;
        this.unit = unit;
    }

    /**
     * Create a new <code>Piece</code>.
     * 
     * @param amount
     *            The amount of the <code>Piece</code> as int
     */
    public Piece(int amount) {
        this.value = amount;
        this.unit = PieceUnit.PC.getBaseUnit();
    }

    /**
     * Returns the unit of the <code>Piece</code>.
     * 
     * @return The unit
     */
    public PieceUnit getUnit() {
        return unit;
    }

    /**
     * Returns the amount of the <code>Piece</code>.
     * 
     * @return The amount
     */
    public int getAmount() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void convertTo(PieceUnit unt) {
        if (PieceUnit.PC == unt && this.getUnit() == PieceUnit.DOZ) {
            this.value = this.value * 12;
            this.unit = PieceUnit.PC;
        } else if (PieceUnit.DOZ == unt && this.getUnit() == PieceUnit.PC) {
            this.value = this.value / 12;
            this.unit = PieceUnit.DOZ;
        }
    }

    private int compare(int val1, int val2) {
        if (val1 == val2) {
            return 0;
        };
        return val1 < val2 ? -1 : 1;
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
     */
    @Override
    public String toString() {
        return value + " " + unit;
    }
}