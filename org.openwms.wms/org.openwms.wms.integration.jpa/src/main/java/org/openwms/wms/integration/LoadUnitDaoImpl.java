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
package org.openwms.wms.integration;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.openwms.common.domain.values.Barcode;
import org.openwms.wms.domain.LoadUnit;
import org.openwms.wms.integration.LoadUnitDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * A LoadUnitDaoImpl.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Transactional(propagation = Propagation.MANDATORY)
@Repository(LoadUnitDaoImpl.COMPONENT_NAME)
public class LoadUnitDaoImpl implements LoadUnitDao {

    /** Springs component name. */
    public static final String COMPONENT_NAME = "loadUnitDao";

    @PersistenceContext
    private EntityManager em;

    /**
     * {@inheritDoc}
     * 
     * Never returns <code>null</code>.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<LoadUnit> findAllOnTransportUnit(Barcode barcode) {
        Query query = em.createNamedQuery(LoadUnit.NQ_FIND_WITH_BARCODE);
        query.setParameter(LoadUnit.QP_FIND_WITH_BARCODE_BARCODE, barcode);
        List<LoadUnit> result = query.getResultList();
        return null == result ? Collections.<LoadUnit> emptyList() : result;
    }
}