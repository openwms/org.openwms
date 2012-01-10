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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openwms.common.domain.LocationGroup;
import org.openwms.common.domain.TransportUnit;
import org.openwms.core.integration.jpa.AbstractGenericJpaDao;
import org.openwms.tms.domain.order.TransportOrder;
import org.openwms.tms.domain.values.TransportOrderState;
import org.openwms.tms.integration.TransportOrderDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * A TransportOrderDaoImpl.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.integration.jpa.AbstractGenericJpaDao
 * @see org.openwms.tms.integration.TransportOrderDao
 */
@Transactional
@Repository
public class TransportOrderDaoImpl extends AbstractGenericJpaDao<TransportOrder, Long> implements TransportOrderDao {

    /**
     * {@inheritDoc}
     * 
     * @return Name of the query
     * @see org.openwms.core.integration.jpa.AbstractGenericJpaDao#getFindAllQuery()
     */
    @Override
    protected String getFindAllQuery() {
        return TransportOrder.NQ_FIND_ALL;
    }

    /**
     * {@inheritDoc}
     * 
     * @return Name of the query
     * @see org.openwms.core.integration.jpa.AbstractGenericJpaDao#getFindByUniqueIdQuery()
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
        return (Integer) getEm()
                .createNativeQuery(
                        "select count(*) from TransportOrder to where to.targetLocationGroup = :locationGroup",
                        Integer.class).setParameter("locationGroup", locationGroup).getSingleResult();
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

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.tms.integration.TransportOrderDao#findForTUinState(org.openwms.common.domain.TransportUnit,
     *      org.openwms.tms.domain.values.TransportOrderState[])
     */
    @Override
    public List<TransportOrder> findForTUinState(TransportUnit transportUnit, TransportOrderState... states) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("transportUnit", transportUnit);
        params.put("states", Arrays.asList(states));
        List<TransportOrder> others = super.findByNamedParameters(TransportOrder.NQ_FIND_FOR_TU_IN_STATE, params);
        if (others == null || others.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("No TransportOrders for TransportUnit [" + transportUnit + "] in on of the states "
                        + Arrays.toString(states) + " found");
            }
            return Collections.emptyList();
        }
        if (logger.isDebugEnabled()) {
            logger.debug("TransportOrders for TransportUnit [" + transportUnit + "] in state "
                    + Arrays.toString(states) + " found");
        }
        return others;
    }

}
