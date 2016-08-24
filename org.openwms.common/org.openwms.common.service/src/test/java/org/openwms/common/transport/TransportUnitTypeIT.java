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

import java.util.List;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.openwms.common.location.LocationType;
import org.openwms.core.test.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * A TransportUnitTypeTest.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 0.1
 */
@RunWith(SpringRunner.class)
@IntegrationTest
public class TransportUnitTypeIT {

    @org.junit.Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private TransportUnitTypeRepository repository;

    public final
    @Test
    void testUniqueConstraint() {
        repository.save(ObjectFactory.createTransportUnitType("TUT1"));
        thrown.expect(DataIntegrityViolationException.class);
        repository.save(ObjectFactory.createTransportUnitType("TUT1"));
    }

    public final
    @Test
    void testCascadingTypePlacingRuleWithOrphan() {
        LocationType locationType = new LocationType("conveyor");
        entityManager.persist(locationType);
        entityManager.flush();

        TransportUnitType cartonType = ObjectFactory.createTransportUnitType("Carton Type");
        TypePlacingRule typePlacingRule = new TypePlacingRule(cartonType, locationType, 1);
        cartonType.addTypePlacingRule(typePlacingRule);
        cartonType = entityManager.merge(cartonType);

        List<TypePlacingRule> tpr = entityManager.getEntityManager().createQuery("select tpr from TypePlacingRule tpr", TypePlacingRule.class).getResultList();
        assertThat(tpr).hasSize(1);

        cartonType.removeTypePlacingRule(typePlacingRule);
        entityManager.merge(cartonType);
        tpr = entityManager.getEntityManager().createQuery("select tpr from TypePlacingRule tpr", TypePlacingRule.class).getResultList();
        assertThat(tpr).hasSize(0);
    }

    public final
    @Test
    void testCascadingTypePlacingRule() {
        LocationType locationType = new LocationType("conveyor");
        entityManager.persist(locationType);
        entityManager.flush();

        TransportUnitType cartonType = ObjectFactory.createTransportUnitType("Carton Type");
        TypePlacingRule typePlacingRule = new TypePlacingRule(cartonType, locationType, 1);
        cartonType.addTypePlacingRule(typePlacingRule);
        cartonType = entityManager.merge(cartonType);

        List<TypePlacingRule> tpr = entityManager.getEntityManager().createQuery("select tpr from TypePlacingRule tpr", TypePlacingRule.class).getResultList();
        assertThat(tpr).hasSize(1);

        entityManager.remove(cartonType);
        tpr = entityManager.getEntityManager().createQuery("select tpr from TypePlacingRule tpr", TypePlacingRule.class).getResultList();
        assertThat(tpr).hasSize(0);
    }
}
