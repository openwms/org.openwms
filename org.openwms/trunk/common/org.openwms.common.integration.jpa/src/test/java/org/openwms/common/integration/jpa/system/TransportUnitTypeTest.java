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
package org.openwms.common.integration.jpa.system;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.fail;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import org.junit.Ignore;
import org.junit.Test;
import org.openwms.common.domain.LocationType;
import org.openwms.common.domain.TransportUnitType;
import org.openwms.common.domain.TypePlacingRule;
import org.openwms.core.test.AbstractJpaSpringContextTests;
import org.springframework.transaction.annotation.Transactional;

/**
 * A TransportUnitTypeTest.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Transactional
public class TransportUnitTypeTest extends AbstractJpaSpringContextTests {
    // TODO [russelltina] : Check

    /**
     * Test unique constraint on type.
     */
    @Ignore
    @Test
    public final void testTransportUnitType() {
        TransportUnitType transportUnitType = new TransportUnitType("JU_TEST");
        TransportUnitType transportUnitType2 = new TransportUnitType("JU_TEST");

        entityManager.persist(transportUnitType);
        try {
            entityManager.persist(transportUnitType2);
            fail("Expecting exception when persisting existing entity with same identifier!");
        } catch (PersistenceException pe) {}
        TransportUnitType tt = null;
        try {
            tt = (TransportUnitType) entityManager.createNamedQuery(TransportUnitType.NQ_FIND_BY_NAME)
                    .setParameter(1, "JU_TEST").getSingleResult();
        } catch (EntityNotFoundException nre) {
            assertNotNull("TransportUnitType should be SAVED before", tt);
        }

        entityManager.remove(tt);

        try {
            tt = (TransportUnitType) entityManager.createNamedQuery(TransportUnitType.NQ_FIND_BY_NAME)
                    .setParameter(1, "JU_TEST").getSingleResult();
            assertNull("TransportUnitType should be REMOVED before", tt);
        } catch (EntityNotFoundException nre) {
            // okay
        }
    }

    @Ignore
    @Test
    public final void testCascadingTypePlacingRule() {
        TransportUnitType transportUnitType = new TransportUnitType("JU_TEST");
        LocationType locationType = new LocationType("JU_LOC_TYPE");
        TypePlacingRule typePlacingRule = new TypePlacingRule(transportUnitType, locationType, 1);

        transportUnitType.addTypePlacingRule(typePlacingRule);

        entityManager.persist(locationType);
        entityManager.persist(transportUnitType);

        TypePlacingRule tpr = entityManager.find(TypePlacingRule.class, Long.valueOf(1));
        assertNotNull("TypePlacingRule should be cascaded SAVED before", tpr);

        entityManager.remove(transportUnitType);

        try {
            transportUnitType = (TransportUnitType) entityManager.createNamedQuery(TransportUnitType.NQ_FIND_BY_NAME)
                    .setParameter(1, "JU_TEST").getSingleResult();
            assertNull("TransportUnitType should be REMOVED before", transportUnitType);
        } catch (EntityNotFoundException nre) {
            // okay here
        }
    }
}
