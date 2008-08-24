/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain.values;

import java.math.BigDecimal;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * A WeightTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
public class WeightTest extends TestCase {

	@Test
	public void testWeight() {
		Weight w1 = new Weight(new BigDecimal(1), WeightUnit.KG);
		Weight w2 = new Weight(new BigDecimal(1), WeightUnit.T);
		w2.convertTo(WeightUnit.KG);
		w1.compareTo(w2);
	}

	@Test
	public void testWeightComparison() {
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
