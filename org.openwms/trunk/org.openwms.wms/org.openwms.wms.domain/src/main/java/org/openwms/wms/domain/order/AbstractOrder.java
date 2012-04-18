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
package org.openwms.wms.domain.order;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.openwms.common.domain.values.Problem;
import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.domain.DomainObject;
import org.openwms.wms.domain.types.WMSTypes;

/**
 * A AbstractOrder.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "C_TYPE")
@Table(name = "WMS_ORDER")
public abstract class AbstractOrder extends AbstractEntity implements DomainObject<Long> {

    private static final long serialVersionUID = 1618706966339343638L;

    /** Unique technical key. */
    @Id
    @Column(name = "C_ID")
    @GeneratedValue
    private Long id;

    /** Unique order id, business key. */
    @Column(name = "C_ORDER_ID", length = WMSTypes.ORDER_ID_LENGTH, unique = true, nullable = false)
    private String orderId;

    /** Number positions this order has. */
    @Column(name = "C_NO_POS")
    private int noPositions = -1;

    /** Number of reserved positions. */
    @Column(name = "C_NO_RESRV_POS")
    private int noReservedPositions = -1;

    /** Number of already allocated Positions. */
    @Column(name = "C_NO_ALLOC_POS")
    private int noAllocatedPositions = -1;

    /** Number of started positions; */
    @Column(name = "C_NO_STARTED_POS")
    private int noStartedPositions = -1;

    /** Number of executed positions. */
    @Column(name = "C_NO_EXEC_POS")
    private int noExecutedPositions = -1;

    /** Number of completed positions. */
    @Column(name = "C_NO_COMPL_POS")
    private int noCompletedPositions = -1;

    /** Current order state. */
    @Column(name = "C_ORDER_STATE")
    private int orderState = OrderState.UNDEFINED;

    /**
     * Property to lock/unlock an order.
     * <ul>
     * <li>true: locked</li>
     * <li>false: unlocked</li>
     * </ul>
     * Default is {@value}
     */
    @Column(name = "C_LOCKED")
    private boolean locked = false;

    /** Current priority of the order. */
    @Column(name = "C_PRIORITY")
    private int priority;

    /** Latest finish date of this order. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_LASTEST_DUE")
    private Date latestDueDate;

    /** Earliest date when the order has to be started. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_START_DATE")
    private Date startDate;

    /** Date when the order should be allocated. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_NEXT_ALLOC")
    private Date nextAllocationDate;

    /** Latest problem that is occurred on this order. */
    @Embedded
    private Problem problem;

    /** Version field. */
    @Version
    @Column(name = "C_VERSION")
    private long version;

    @OneToMany(mappedBy = "order")
    private Set<OrderPosition> positions = new HashSet<OrderPosition>();

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.domain.DomainObject#isNew()
     */
    @Override
    public boolean isNew() {
        return this.id == null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.domain.DomainObject#getVersion()
     */
    @Override
    public long getVersion() {
        return this.version;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.domain.DomainObject#getId()
     */
    @Override
    public Long getId() {
        return this.id;
    }

    /**
     * Get the orderId.
     * 
     * @return the orderId.
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Get the noPositions.
     * 
     * @return the noPositions.
     */
    public int getNoPositions() {
        return noPositions;
    }

    /**
     * Get the noReservedPositions.
     * 
     * @return the noReservedPositions.
     */
    public int getNoReservedPositions() {
        return noReservedPositions;
    }

    /**
     * Get the noAllocatedPositions.
     * 
     * @return the noAllocatedPositions.
     */
    public int getNoAllocatedPositions() {
        return noAllocatedPositions;
    }

    /**
     * Get the noStartedPositions.
     * 
     * @return the noStartedPositions.
     */
    public int getNoStartedPositions() {
        return noStartedPositions;
    }

    /**
     * Get the noExecutedPositions.
     * 
     * @return the noExecutedPositions.
     */
    public int getNoExecutedPositions() {
        return noExecutedPositions;
    }

    /**
     * Get the noCompletedPositions.
     * 
     * @return the noCompletedPositions.
     */
    public int getNoCompletedPositions() {
        return noCompletedPositions;
    }

    /**
     * Get the orderState.
     * 
     * @return the orderState.
     */
    public int getOrderState() {
        return orderState;
    }

    /**
     * Get the locked.
     * 
     * @return the locked.
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Get the priority.
     * 
     * @return the priority.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Get the latestDueDate.
     * 
     * @return the latestDueDate.
     */
    public Date getLatestDueDate() {
        return new Date(latestDueDate.getTime());
    }

    /**
     * Get the startDate.
     * 
     * @return the startDate.
     */
    public Date getStartDate() {
        return new Date(startDate.getTime());
    }

    /**
     * Get the nextAllocationDate.
     * 
     * @return the nextAllocationDate.
     */
    public Date getNextAllocationDate() {
        return new Date(nextAllocationDate.getTime());
    }

    /**
     * Get the problem.
     * 
     * @return the problem.
     */
    public Problem getProblem() {
        return problem;
    }

    /**
     * Get the positions.
     * 
     * @return the positions.
     */
    public Set<OrderPosition> getPositions() {
        return positions;
    }

}