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

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

/**
 * A WeightTest.
 * 
 * @author <a href="mailto:scherrer@users.sourceforge.net">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.common.domain.values.Weight
 */
public class WeightTest {

    /**
     * Test creation of a Weight.
     */
    @Test
    public final void testWeight() {
        Weight w1 = new Weight(new BigDecimal(1), WeightUnit.KG);
        Weight w2 = new Weight(new BigDecimal(1), WeightUnit.T);
        w2.convertTo(WeightUnit.KG);
        w1.compareTo(w2);
    }

    /**
     * Test creation of a Weight and comparison.
     */
    @Test
    public final void testWeightComparison() {
        Weight w1 = new Weight(new BigDecimal(1), WeightUnit.G);
        Weight w2 = new Weight(new BigDecimal(1), WeightUnit.T);
        assertEquals("1G is less than 1T", 1, w1.compareTo(w2));
        assertEquals("1T is greater than 1G", -1, w2.compareTo(w1));

        Weight w3 = new Weight(new BigDecimal(2), WeightUnit.G);
        assertEquals("1G is less than 2G", 1, w1.compareTo(w3));
        assertEquals("2G is greater than 1G", -1, w3.compareTo(w1));

        Weight w4 = new Weight(new BigDecimal("0.000002"), WeightUnit.T);
        w3.convertTo(WeightUnit.T);
        assertEquals("2G are the same as 0.000002T", 0, w3.compareTo(w4));
    }
}
