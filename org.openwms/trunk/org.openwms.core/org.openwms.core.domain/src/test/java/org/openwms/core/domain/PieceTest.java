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
package org.openwms.core.domain;

import org.junit.Assert;
import org.junit.Test;
import org.openwms.core.domain.values.Piece;
import org.openwms.core.domain.values.PieceUnit;

/**
 * A PieceTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
public class PieceTest {

    /**
     * Test method for
     * {@link org.openwms.core.domain.values.Piece#compareTo(org.openwms.core.domain.values.Piece)}
     * .
     */
    @Test
    public final void testCompareTo() {
        Piece p30 = new Piece(30);
        Piece p50 = new Piece(50, PieceUnit.PC);
        Assert.assertEquals(1, p50.compareTo(p30));
        Assert.assertEquals(-1, p30.compareTo(p50));
    }

    /**
     * Test method for
     * {@link org.openwms.core.domain.values.Piece#convertTo(org.openwms.core.domain.values.PieceUnit)}
     * .
     */
    @Test
    public final void testConvertToPieceUnit() {
        Piece p30 = new Piece(30);
        Piece p50 = new Piece(50, PieceUnit.PC);
        p50.convertTo(PieceUnit.DOZ);
        Assert.assertEquals(1, p50.compareTo(p30));
        Assert.assertEquals(-1, p30.compareTo(p50));

        Piece p5doz = new Piece(5, PieceUnit.DOZ);
        Assert.assertEquals(1, p5doz.compareTo(p50));
        Assert.assertEquals(1, p5doz.compareTo(p30));
        Assert.assertEquals(-1, p50.compareTo(p5doz));
        Assert.assertEquals(-1, p30.compareTo(p5doz));

        Piece p60 = new Piece(60, PieceUnit.PC);
        Assert.assertEquals(0, p5doz.compareTo(p60));
        Assert.assertEquals(0, p60.compareTo(p5doz));
    }
}