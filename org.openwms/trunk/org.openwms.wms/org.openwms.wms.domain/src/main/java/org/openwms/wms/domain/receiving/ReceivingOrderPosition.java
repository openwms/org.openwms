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
package org.openwms.wms.domain.receiving;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.openwms.core.domain.values.CoreTypeDefinitions;
import org.openwms.core.domain.values.Unit;
import org.openwms.wms.domain.order.AbstractOrder;
import org.openwms.wms.domain.order.OrderPosition;

/**
 * A ReceivingOrderPosition.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Entity
@Table(name = "WMS_RCV_ORDER_POSITION")
@NamedQueries({
        @NamedQuery(name = ReceivingOrderPosition.NQ_FIND_ALL, query = "select rop from ReceivingOrderPosition rop"),
        @NamedQuery(name = ReceivingOrderPosition.NQ_FIND_POSITION_KEY, query = "select rop from ReceivingOrderPosition rop where rop.positionId = :orderPositionKey order by rop.positionId") })
public class ReceivingOrderPosition extends OrderPosition {

    private static final long serialVersionUID = -9027230827826634833L;
    @Embedded
    @AttributeOverride(name = "quantity", column = @Column(name = "C_QTY_EXPECTED", length = CoreTypeDefinitions.QUANTITY_LENGTH, nullable = false))
    private Unit<?, ?> qtyExpected;

    /**
     * Query to find all <code>ReceivingOrderPosition</code>s.<br />
     * Query name is {@value} .
     */
    public static final String NQ_FIND_ALL = "ReceivingOrderPosition.findAll";

    /**
     * Query to find <strong>one</strong> <code>ReceivingOrderPosition</code> by
     * its orderPositionKey. <li>
     * Query parameter name <strong>orderPositionKey</strong> : The unique
     * orderPositionKey of the <code>ReceivingOrderPosition</code> to search
     * for.</li><br />
     * Query name is {@value} .
     */
    public static final String NQ_FIND_POSITION_KEY = "ReceivingOrderPosition.findByPosKey";

    /**
     * Used by the JPA provider.
     */
    protected ReceivingOrderPosition() {}

    /**
     * Create a new ReceivingOrderPosition.
     * 
     * @param ord
     *            The order
     * @param posNo
     *            The position number
     * @param qtyExp
     *            The expected quantity
     */
    public ReceivingOrderPosition(AbstractOrder ord, String posNo, Unit<?, ?> qtyExp) {
        super(ord, posNo);
        this.qtyExpected = qtyExp;
    }

    /**
     * Get the qtyExpected.
     * 
     * @return the qtyExpected.
     */
    public Unit<?, ?> getQtyExpected() {
        return qtyExpected;
    }

    /**
     * Set the qtyExpected.
     * 
     * @param qtyExpected
     *            The qtyExpected to set.
     */
    public void setQtyExpected(Unit<?, ?> qtyExpected) {
        this.qtyExpected = qtyExpected;
    }
}
