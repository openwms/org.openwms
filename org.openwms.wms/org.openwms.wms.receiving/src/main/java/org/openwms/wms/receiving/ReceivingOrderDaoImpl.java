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
package org.openwms.wms.receiving;

import org.openwms.wms.order.OrderPositionKey;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

/**
 * A ReceivingOrderDaoImpl.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Transactional(propagation = Propagation.MANDATORY)
@Repository(ReceivingOrderDaoImpl.COMPONENT_NAME)
public class ReceivingOrderDaoImpl implements ReceivingOrderDao {

    /** Springs component name. */
    public static final String COMPONENT_NAME = "receivingOrderDao";

    @PersistenceContext
    private EntityManager em;

    /**
     * {@inheritDoc}
     * 
     * Implementation is null-safe and always returns a List.
     */
    @Override
    public List<ReceivingOrder> findAll() {
        @SuppressWarnings("unchecked")
        List<ReceivingOrder> orders = em.createNamedQuery(ReceivingOrder.NQ_FIND_ALL).getResultList();
        return orders == null ? Collections.<ReceivingOrder> emptyList() : orders;
    }

    /**
     * {@inheritDoc}
     * 
     * When no <code>ReceivingOrder</code> with the unique id
     * <code>orderId</code> was found, <code>null</code> is returned.
     */
    @Override
    public ReceivingOrder findByOrderId(String orderId) {
        try {
            return (ReceivingOrder) em.createNamedQuery(ReceivingOrder.NQ_FIND_WITH_ORDERID)
                    .setParameter(ReceivingOrder.QP_FIND_WITH_ORDERID_ORDERID, orderId).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReceivingOrderPosition findByKey(OrderPositionKey key) {
        Query query = em.createNamedQuery(ReceivingOrderPosition.NQ_FIND_POSITION_KEY);
        query.setParameter(ReceivingOrderPosition.QP_FIND_WITH_POSITION_KEY, key);
        return (ReceivingOrderPosition) query.getSingleResult();
    }
}