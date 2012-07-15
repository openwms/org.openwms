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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.openwms.core.domain.values.BaseUnit;

/**
 * A PieceUnit.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
public enum PieceUnit implements BaseUnit<PieceUnit> {

    /**
     * A Piece.
     */
    PC(new BigDecimal(1)),

    /**
     * A Dozen.
     */
    DOZ(new BigDecimal(12));

    private BigDecimal magnitude;
    private static PieceUnit[] all = { PieceUnit.PC, PieceUnit.DOZ };

    /**
     * Get the magnitude of this <code>PieceUnit</code>.
     * 
     * @return the magnitude
     */
    public BigDecimal getMagnitude() {
        return this.magnitude;
    }

    /**
     * Create a new <code>PieceUnit</code>.
     * 
     * @param magnitude
     *            The base unit of the weight
     */
    PieceUnit(BigDecimal magnitude) {
        this.magnitude = magnitude;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PieceUnit> getAll() {
        return Arrays.asList(all);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PieceUnit getBaseUnit() {
        return PC;
    }
}