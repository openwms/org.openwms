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

import java.util.Arrays;
import java.util.List;

/**
 * A PieceUnit.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
public enum PieceUnit implements UnitType<PieceUnit, Long> {

    /**
     * A Piece.
     */
    PC(1),

    /**
     * A Dozen.
     */
    DOZ(12);

    private Long baseUnit;
    private static PieceUnit[] all = { PieceUnit.PC, PieceUnit.DOZ };

    /**
     * Create a new <code>PieceUnit</code>.
     * 
     * @param baseUnit
     *            The base unit of the weight
     */
    PieceUnit(long baseUnit) {
        this.baseUnit = baseUnit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getBaseUnitValue() {
        return this.baseUnit;
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