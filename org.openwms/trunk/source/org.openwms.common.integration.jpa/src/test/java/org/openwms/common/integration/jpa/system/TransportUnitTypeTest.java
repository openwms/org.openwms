/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.integration.jpa.system;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.fail;

import javax.persistence.PersistenceException;

import org.junit.Test;
import org.openwms.common.domain.LocationType;
import org.openwms.common.domain.TransportUnitType;
import org.openwms.common.domain.TypePlacingRule;
import org.openwms.common.test.AbstractJpaSpringContextTests;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * A TransportUnitTypeTest.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 447 $
 */
@Transactional
public class TransportUnitTypeTest extends AbstractJpaSpringContextTests {

    /**
     * Test unique constraint on type.
     */
    @Test
    public final void testTransportUnitType() {
        TransportUnitType transportUnitType = new TransportUnitType("JU_TEST");
        TransportUnitType transportUnitType2 = new TransportUnitType("JU_TEST");

        entityManager.persist(transportUnitType);
        try {
            entityManager.persist(transportUnitType2);
            fail("Expecting exception when persisting existing entity with same identifier!");
        }
        catch (PersistenceException pe) {}

        TransportUnitType tt = entityManager.find(TransportUnitType.class, "JU_TEST");
        assertNotNull("TransportUnitType should be SAVED before", tt);

        entityManager.remove(tt);

        tt = entityManager.find(TransportUnitType.class, "JU_TEST");
        assertNull("TransportUnitType should be REMOVED before", tt);
    }

    @Test
    public final void testCascadingTypePlacingRule() {
        TransportUnitType transportUnitType = new TransportUnitType("JU_TEST");
        LocationType locationType = new LocationType("JU_LOC_TYPE");
        TypePlacingRule typePlacingRule = new TypePlacingRule(1, locationType);

        transportUnitType.addTypePlacingRule(typePlacingRule);

        entityManager.persist(locationType);
        entityManager.persist(transportUnitType);

        TypePlacingRule tpr = entityManager.find(TypePlacingRule.class, Long.valueOf(1));
        assertNotNull("TypePlacingRule should be cascaded SAVED before", tpr);

        entityManager.remove(transportUnitType);

        transportUnitType = entityManager.find(TransportUnitType.class, "JU_TEST");
        assertNull("TransportUnitType should be REMOVED before", transportUnitType);

    }

}
