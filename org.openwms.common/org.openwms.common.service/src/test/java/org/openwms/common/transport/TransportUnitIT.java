/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.common.transport;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.openwms.common.location.Location;
import org.openwms.common.location.LocationPK;
import org.openwms.core.test.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * A TransportUnitIT.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@IntegrationTest
public class TransportUnitIT {

    @org.junit.Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private TransportUnitRepository repository;
    @Autowired
    private TransportUnitTypeRepository typeRepository;

    private TransportUnitType knownType;
    private Location knownLocation1;

    @Before
    public void onBefore() {
        repository.deleteAll();
        typeRepository.deleteAll();
        entityManager.flush();
        knownType = ObjectFactory.createTransportUnitType("Carton");
        knownLocation1 = Location.create(new LocationPK("KNO4", "KNO4", "KNO4", "KNO4", "KNO4"));
        entityManager.persist(knownType);
        entityManager.persist(knownLocation1);
        entityManager.flush();
    }

    public final
    @Test
    void testCreation() {
        TransportUnit transportUnit = ObjectFactory.createTransportUnit("NEVER_PERSISTED");
        transportUnit.setTransportUnitType(knownType);
        transportUnit.setActualLocation(knownLocation1);
        repository.save(transportUnit);
    }

    public final
    @Test
    void testCreateTUWithUnknownType() {
        TransportUnit transportUnit = ObjectFactory.createTransportUnit("NEVER_PERSISTED");
        TransportUnitType tut = ObjectFactory.createTransportUnitType("UNKNOWN_TUT");
        transportUnit.setTransportUnitType(tut);
        thrown.expect(DataAccessException.class);
        repository.save(transportUnit);
    }


    public final
    @Test
    void testCreateTUWithUnknownActualLocation() {
        TransportUnit transportUnit = ObjectFactory.createTransportUnit("NEVER_PERSISTED");
        transportUnit.setTransportUnitType(knownType);
        transportUnit.setActualLocation(Location.create(new LocationPK("UNKN", "UNKN", "UNKN", "UNKN", "UNKN")));
        thrown.expect(DataAccessException.class);
        repository.save(transportUnit);
    }

    public final
    @Test
    void testCreateTUWithUnknownTargetLocation() {
        TransportUnit transportUnit = ObjectFactory.createTransportUnit("NEVER_PERSISTED");
        transportUnit.setTransportUnitType(knownType);
        transportUnit.setActualLocation(knownLocation1);
        transportUnit.setTargetLocation(Location.create(new LocationPK("UNKN", "UNKN", "UNKN", "UNKN", "UNKN")));
        thrown.expect(IllegalStateException.class);
        entityManager.persistAndFlush(transportUnit);
    }

    public final
    @Test
    void testSaveTUWithKnownActualLocation() {
        TransportUnit transportUnit = ObjectFactory.createTransportUnit("4711");

        transportUnit.setTransportUnitType(knownType);
        transportUnit.setActualLocation(knownLocation1);

        transportUnit = repository.save(transportUnit);
        assertThat(transportUnit.isNew()).isFalse();
        assertThat(transportUnit.getActualLocation()).isNotNull();
    }

    public final
    @Test
    void testSaveTUwithKnownTargetLocation() {
        TransportUnit transportUnit = ObjectFactory.createTransportUnit("4711");

        transportUnit.setTransportUnitType(knownType);
        transportUnit.setActualLocation(knownLocation1);
        transportUnit.setTargetLocation(knownLocation1);

        transportUnit = repository.save(transportUnit);
        assertThat(transportUnit.isNew()).isFalse();
        assertThat(transportUnit.getActualLocation()).isNotNull();
    }


    public final
    @Test
    void testTUwithErrors() throws Exception {
        TransportUnit transportUnit = new TransportUnit(new Barcode("4711"));

        transportUnit.setTransportUnitType(knownType);
        transportUnit.setActualLocation(knownLocation1);
        transportUnit.setTargetLocation(knownLocation1);

        transportUnit.addError(new UnitError());
        Thread.sleep(100);
        transportUnit.addError(new UnitError());
        entityManager.persist(transportUnit);

        Query query = entityManager.getEntityManager().createQuery("select count(ue) from UnitError ue", Long.class);

        Long cnt = (Long) query.getSingleResult();
        assertThat(cnt).isEqualTo(2);

        entityManager.remove(transportUnit);

        cnt = (Long) query.getSingleResult();
        assertThat(cnt).isEqualTo(0);
    }
}
