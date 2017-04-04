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
package org.openwms.wms.order;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * A WMSOrderDaoImpl.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Transactional(propagation = Propagation.MANDATORY)
@Repository(WMSOrderDaoImpl.COMPONENT_NAME)
public class WMSOrderDaoImpl<T extends AbstractOrder<T, U>, U extends OrderPosition<T, U>> implements WMSOrderDao<T, U> {

    /** Springs component name. */
    public static final String COMPONENT_NAME = "wmsOrderDao";

    @PersistenceContext
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public T findByOrderId(String orderId) {
        return (T) em.createNamedQuery(AbstractOrder.NQ_FIND_WITH_ORDERID)
                .setParameter(AbstractOrder.QP_FIND_WITH_ORDERID_ORDERID, orderId).getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T createOrder(T order) {
        em.persist(order);
        return em.merge(order);
    }

    /**
     * {@inheritDoc}
     * 
     * We expect that the order already exists.
     */
    @Override
    public U createOrderPosition(U orderPos) {
        em.persist(orderPos);
        return em.merge(orderPos);
    }
}