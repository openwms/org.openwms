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
package org.openwms.core.configuration.file;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openwms.core.configuration.PropertyScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * An UserPreferenceTest.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserPreferenceIT {

    private static final String KNOWN_USER = "KNOWN_USER";
    @Autowired
    private TestEntityManager em;

    /**
     * Setup data.
     */
    @Before
    public void onSetup() {
        em.persist(new UserPreference(KNOWN_USER, "testKey"));
    }

    /**
     * Resolve the persisted UserPreference.
     */
    @Test
    public final void testSimplePersistAndGet() {
        UserPreference up = em.getEntityManager().createNamedQuery(UserPreference.NQ_FIND_BY_OWNER, UserPreference.class).setParameter("owner", KNOWN_USER).getSingleResult();
        assertThat(up)
                .isNotNull()
                .extracting("owner", "key", "type")
                .contains(KNOWN_USER, "testKey", PropertyScope.USER)
        ;
    }
}