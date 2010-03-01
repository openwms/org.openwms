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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.junit.Test;
import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.TransportUnit;
import org.openwms.common.domain.TransportUnitType;
import org.openwms.common.domain.system.UnitError;
import org.openwms.common.domain.values.Barcode;
import org.openwms.common.domain.values.Barcode.BARCODE_ALIGN;
import org.openwms.common.test.AbstractJpaSpringContextTests;
import org.springframework.transaction.annotation.Transactional;

/**
 * A TransportUnitTest.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Transactional
public class TransportUnitTest extends AbstractJpaSpringContextTests {

    /**
     * Try to persist TransportUnit without TransportUnitType.
     */
    @Test
    public final void testTUwithoutType() {
        TransportUnit transportUnit = new TransportUnit("NEVER_PERSISTED");
        try {
            entityManager.persist(transportUnit);
            fail("Persisting without TransportUnitType not allowed!");
        } catch (PersistenceException pe) {
            // okay
            logger.debug("OK:Execption while persisting TransportUnit without TransportUnitType.");
        }
    }

    /**
     * Try to instantiate TransportUnit with unknown TransportUnitType.
     */
    @Test
    public final void testTUwithUnknownType() {
        TransportUnit transportUnit = new TransportUnit("NEVER_PERSISTED");
        TransportUnitType transportUnitType = new TransportUnitType("UNKNOWN_TUT");
        transportUnit.setTransportUnitType(transportUnitType);
        try {
            entityManager.persist(transportUnit);
            fail("Persisting with unknown TransportUnitType not allowed!");
        } catch (PersistenceException pe) {
            // okay
            logger.debug("OK:Exception while persisting TransportUnit with unknown TransportUnitType.");
        }
    }

    /**
     * Try to persist TransportUnit with an unknown actualLocation.
     */
    @Test
    public final void testTUwithUnknownLocations() {
        TransportUnit transportUnit = new TransportUnit("NEVER_PERSISTED");
        TransportUnitType transportUnitType = new TransportUnitType("WELL_KNOWN_TUT");
        Location actualLocation = new Location(new LocationPK("UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN"));

        entityManager.persist(transportUnitType);

        transportUnit.setTransportUnitType(transportUnitType);
        transportUnit.setActualLocation(actualLocation);
        try {
            entityManager.persist(transportUnit);
            fail("Persisting with unknown actualLocation && targetLocation not allowed!");
        } catch (Exception pe) {
            // okay
            logger.debug("OK:Execption while persisting TransportUnit with unknown actualLocation and targetLocation.");
        }
    }

    /**
     * Try to persist TransportUnit with known TransportUnitType and a known
     * actualLocation.
     */
    @Test
    public final void testTUwithKnownLocation() {
        TransportUnit transportUnit = new TransportUnit("TEST_TU");
        TransportUnitType transportUnitType = new TransportUnitType("WELL_KNOWN_TUT_2");
        Location location = new Location(new LocationPK("KNOWN4", "KNOWN4", "KNOWN4", "KNOWN4", "KNOWN4"));

        entityManager.persist(transportUnitType);
        entityManager.persist(location);

        transportUnit.setTransportUnitType(transportUnitType);
        transportUnit.setActualLocation(location);

        try {
            entityManager.merge(transportUnit);
            logger.debug("Also without targetLocation must be okay.");
        } catch (PersistenceException pe) {
            logger
                    .debug("NOT OK:Execption while persisting TransportUnit with known actualLocation and transportUnitType!");
            fail("Persisting transportUnit with known actualLocation and transportUnitType not committed!");
        }
    }

    /**
     * Test cascading UnitErrors with TransportUnits.
     */
    @Test
    public final void testTUwithErrors() {
        Barcode.setPadder('0');
        Barcode.setLength(20);
        Barcode.setAlignment(BARCODE_ALIGN.RIGHT);
        Barcode.setPadded(true);
        TransportUnit transportUnit = new TransportUnit(new Barcode("TEST_TU3"));
        TransportUnitType transportUnitType = new TransportUnitType("WELL_KNOWN_TUT_4");
        Location location = new Location(new LocationPK("KNOWN3", "KNOWN3", "KNOWN3", "KNOWN3", "KNOWN3"));

        entityManager.persist(transportUnitType);
        entityManager.persist(location);

        transportUnit.setTransportUnitType(transportUnitType);
        transportUnit.setActualLocation(location);
        transportUnit.setTargetLocation(location);

        transportUnit.addError(new UnitError());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            logger.error("Error", e);
        }
        transportUnit.addError(new UnitError());
        try {
            entityManager.persist(transportUnit);
        } catch (Exception pe) {
            fail("Persisting with well known Location and TransportUnitType fails!");
        }

        Query query = entityManager.createQuery("select count(ue) from UnitError ue");
        Long cnt = (Long) query.getSingleResult();
        assertEquals("Expected 2 persisted UnitErrors", 2, cnt.intValue());

        entityManager.remove(transportUnit);

        cnt = (Long) query.getSingleResult();
        assertEquals("Expected 0 persisted UnitErrors", 0, cnt.intValue());
    }

    /**
     * Try to persist a TransportUnit with well known actualLocation and a well
     * known TransportUnitType. The targetLocation is unknown.
     */
    @Test
    public final void testTUwithKnownLocations() {
        TransportUnit transportUnit = new TransportUnit("TEST_TU2");
        TransportUnitType transportUnitType = new TransportUnitType("WELL_KNOWN_TUT_3");
        Location actualLocation = new Location(new LocationPK("KNOWN2", "KNOWN2", "KNOWN2", "KNOWN2", "KNOWN2"));
        Location targetLocation = new Location(new LocationPK("UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN"));

        entityManager.persist(transportUnitType);
        entityManager.persist(actualLocation);

        transportUnit.setTransportUnitType(transportUnitType);
        transportUnit.setActualLocation(actualLocation);
        transportUnit.setTargetLocation(targetLocation);
        try {
            entityManager.persist(transportUnit);
            // FIXME [scherrer] :
            // fail("Persisting with unknown targetLocation must fail!");
        } catch (Exception pe) {
            // okay
            logger.debug("OK:Execption while persisting TransportUnit with unknown targetLocation.");
        }
    }
}