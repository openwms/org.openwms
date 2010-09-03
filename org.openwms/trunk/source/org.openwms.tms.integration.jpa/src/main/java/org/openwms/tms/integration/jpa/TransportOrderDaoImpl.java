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
package org.openwms.tms.integration.jpa;

import java.util.List;

import org.openwms.common.domain.LocationGroup;
import org.openwms.common.integration.jpa.AbstractGenericJpaDao;
import org.openwms.tms.domain.order.TransportOrder;
import org.openwms.tms.integration.TransportOrderDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * A TransportOrderDaoImpl.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.common.integration.jpa.AbstractGenericJpaDao
 * @see org.openwms.tms.integration.TransportOrderDao
 */
@Transactional
@Repository
public class TransportOrderDaoImpl extends AbstractGenericJpaDao<TransportOrder, Long> implements TransportOrderDao {

    /**
     * {@inheritDoc}
     * 
     * @return Name of the query
     * @see org.openwms.common.integration.jpa.AbstractGenericJpaDao#getFindAllQuery()
     */
    @Override
    protected String getFindAllQuery() {
        return TransportOrder.NQ_FIND_ALL;
    }

    /**
     * {@inheritDoc}
     * 
     * @return Name of the query
     * @see org.openwms.common.integration.jpa.AbstractGenericJpaDao#getFindByUniqueIdQuery()
     */
    @Override
    protected String getFindByUniqueIdQuery() {
        throw new UnsupportedOperationException("Not allowed to query for TransportOrders by an unique business key!");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.tms.integration.TransportOrderDao#getNumberOfTransportOrders(org.openwms.common.domain.LocationGroup)
     */
    @Override
    public int getNumberOfTransportOrders(final LocationGroup locationGroup) {
        return (Integer) getEm().createNativeQuery(
                "select count(*) from TransportOrder to where to.targetLocationGroup = :locationGroup", Integer.class)
                .setParameter("locationGroup", locationGroup).getSingleResult();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.tms.integration.TransportOrderDao#findByIds(java.util.List)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<TransportOrder> findByIds(List<Long> ids) {
        return getEm().createQuery("select to from TransportOrder to where to.id in (:ids)").setParameter("ids", ids)
                .getResultList();
    }
}
