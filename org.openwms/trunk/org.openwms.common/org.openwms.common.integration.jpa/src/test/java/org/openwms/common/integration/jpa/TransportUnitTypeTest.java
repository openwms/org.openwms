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
package org.openwms.common.integration.jpa;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.fail;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openwms.common.domain.LocationType;
import org.openwms.common.domain.TransportUnitType;
import org.openwms.common.domain.TypePlacingRule;
import org.openwms.core.test.AbstractJpaSpringContextTests;
import org.springframework.test.context.ContextConfiguration;

/**
 * A TransportUnitTypeTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@ContextConfiguration("classpath:common-jpa-test-context.xml")
public class TransportUnitTypeTest extends AbstractJpaSpringContextTests {

    private TransportUnitType transportUnitType;

    /**
     * Setup data.
     */
    @Before
    public void onBefore() {
        transportUnitType = new TransportUnitType("TUT1");
        entityManager.persist(transportUnitType);
        entityManager.flush();
        entityManager.clear();
    }

    /**
     * Test unique constraint on type.
     */
    @Test
    public final void testDuplicateTransportUnitType() {
        try {
            entityManager.persist(new TransportUnitType("TUT1"));
            entityManager.flush();
            fail("Expecting exception when persisting existing entity with same identifier!");
        } catch (PersistenceException pe) {
            logger.debug("OK:Exceptiuon must been thrown when persisting TUT with same identifier.");
        }
    }

    /**
     * Test removal of TUT.
     */
    @Test
    public final void testRemovalOfTransportUnitType() {
        transportUnitType = entityManager.merge(transportUnitType);
        transportUnitType = (TransportUnitType) entityManager.createNamedQuery(TransportUnitType.NQ_FIND_BY_NAME)
                .setParameter(1, "TUT1").getSingleResult();
        entityManager.remove(transportUnitType);
        try {
            transportUnitType = (TransportUnitType) entityManager.createNamedQuery(TransportUnitType.NQ_FIND_BY_NAME)
                    .setParameter(1, "TUT1").getSingleResult();
            assertNull("TransportUnitType should be REMOVED before", transportUnitType);
        } catch (NoResultException nre) {
            logger.debug("OK:No Entity found, it was removed.");
        }
    }

    /**
     * Test that removal a TUT with referenced Rules is not allowed.
     */
    @Test
    @Ignore
    public final void testCascadingTypePlacingRule() {
        LocationType locationType = new LocationType("JU_LOC_TYPE");
        entityManager.persist(locationType);

        TypePlacingRule typePlacingRule = new TypePlacingRule(transportUnitType, locationType, 1);
        transportUnitType.addTypePlacingRule(typePlacingRule);
        transportUnitType = entityManager.merge(transportUnitType);

        TypePlacingRule tpr = entityManager.find(TypePlacingRule.class, Long.valueOf(1));
        assertNotNull("TypePlacingRule should be cascade persisted", tpr);

        entityManager.remove(transportUnitType);
        try {
            transportUnitType = (TransportUnitType) entityManager.createNamedQuery(TransportUnitType.NQ_FIND_BY_NAME)
                    .setParameter(1, "TUT1").getSingleResult();
            assertNull("TransportUnitType is not allowed to be REMOVED before", transportUnitType);
        } catch (PersistenceException pe) {
            logger.debug("OK:No Entity found, it was removed.");
        }
    }
}
