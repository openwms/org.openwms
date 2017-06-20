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

import org.ameba.integration.jpa.BaseEntity;
import org.openwms.common.values.Problem;
import org.openwms.wms.WMSTypes;
import org.springframework.util.Assert;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * A AbstractOrder.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "C_TYPE")
@Table(name = "WMS_ORDER")
@NamedQueries({
        @NamedQuery(name = AbstractOrder.NQ_FIND_ALL, query = "select ao from AbstractOrder ao order by ao.orderId"),
        @NamedQuery(name = AbstractOrder.NQ_FIND_WITH_STATE, query = "select ao from AbstractOrder ao where ao.orderState in (:"
                + AbstractOrder.QP_FIND_WITH_STATE_STATE + ") order by ao.priority asc, ao.latestDueDate asc"),
        @NamedQuery(name = AbstractOrder.NQ_FIND_WITH_ORDERID, query = "select ao from AbstractOrder ao where ao.orderId = :"
                + AbstractOrder.QP_FIND_WITH_ORDERID_ORDERID) })
public abstract class AbstractOrder<T extends AbstractOrder<T, U>, U extends OrderPosition<T, U>> extends
        BaseEntity {

    /** Unique order id, business key. */
    @Column(name = "C_ORDER_ID", length = WMSTypes.ORDER_ID_LENGTH, unique = true, nullable = false)
    private String orderId;

    /** Number positions, this Order has. */
    @Column(name = "C_NO_POS")
    private int noPositions = 0;

    /** Number of reserved positions. */
    @Column(name = "C_NO_RESRV_POS")
    private int noReservedPositions = 0;

    /** Number of already allocated Positions. */
    @Column(name = "C_NO_ALLOC_POS")
    private int noAllocatedPositions = 0;

    /** Number of started positions; */
    @Column(name = "C_NO_STARTED_POS")
    private int noStartedPositions = 0;

    /** Number of executed positions. */
    @Column(name = "C_NO_EXEC_POS")
    private int noExecutedPositions = 0;

    /** Number of completed positions. */
    @Column(name = "C_NO_COMPL_POS")
    private int noCompletedPositions = 0;

    /** Current state of this Order. */
    @Enumerated(EnumType.STRING)
    @Column(name = "C_ORDER_STATE")
    private OrderState orderState = OrderState.UNDEFINED;

    /**
     * Property to lock/unlock an Order.
     * <ul>
     * <li>true: locked</li>
     * <li>false: unlocked</li>
     * </ul>
     * Default is {@value}
     */
    @Column(name = "C_LOCKED")
    private boolean locked = false;

    /** Current priority of the Order. */
    @Column(name = "C_PRIORITY")
    private int priority;

    /** Latest finish date of this Order. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_LASTEST_DUE")
    private Date latestDueDate;

    /** Earliest date when the Order has to be started. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_START_DATE")
    private Date startDate;

    /** Date when the Order should be allocated. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_NEXT_ALLOC")
    private Date nextAllocationDate;

    /** Latest problem that is occurred on this Order. */
    @Embedded
    private Problem problem;

    /** All ShippingOrderPositions of this Order. */
    @OneToMany(mappedBy = "order", targetEntity = OrderPosition.class, cascade = CascadeType.PERSIST)
    private Set<U> positions = new HashSet<U>();

    /**
     * Query to find all <code>AbstractOrder</code>s.<br />
     * Query name is {@value} .
     */
    public static final String NQ_FIND_ALL = "AbstractOrder.findAll";

    /**
     * Query to find an <code>AbstractOrder</code>s by it's <code>orderId</code>
     * . <li>
     * Query parameter name <strong>{@value #QP_FIND_WITH_ORDERID_ORDERID}
     * </strong> : The orderId of the <code>AbstractOrder</code> to search for.</li>
     * <br />
     * Query name is {@value} .
     */
    public static final String NQ_FIND_WITH_ORDERID = "AbstractOrder.findWithOrderId";
    public static final String QP_FIND_WITH_ORDERID_ORDERID = "orderId";

    /**
     * Query to find <code>AbstractOrder</code>s by their
     * <code>orderState</code> . <li>
     * Query parameter name <strong>{@value #QP_FIND_WITH_STATE_STATE} </strong>
     * : The state of the <code>AbstractOrder</code> to search for.</li> <br />
     * Query name is {@value} .
     */
    public static final String NQ_FIND_WITH_STATE = "AbstractOrder.findWithState";
    public static final String QP_FIND_WITH_STATE_STATE = "states";

    /** Only for the JPA provider and subclasses. */
    protected AbstractOrder() {
    }

    /**
     * Define a constructor with the orderId as argument.
     */
    public AbstractOrder(String ordId) {
        Assert.hasText(ordId, "Not allowed to create an order with an empty order id");
        this.orderId = ordId;
    }

    /**
     * Before the entity is persisted:
     * <ul>
     * <li>the orderState is set</li>
     * </ul>
     */
    @PrePersist
    protected void prePersist() {
        this.orderState = OrderState.CREATED;
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
        return this.noPositions;
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
    public OrderState getOrderState() {
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
     * Lock/unlock the Order and all OrderPositions.
     * 
     * @param locked
     *            <code>true</code> to lock the Order, <code>false</code> to
     *            unlock it
     */
    public void setLocked(boolean locked) {
        lockPositions(locked);
        this.locked = locked;
    }

    /**
     * Lock this Order.
     */
    public void lock() {
        lockPositions(true);
        this.locked = true;
    }

    /**
     * Unlock this Order.
     */
    public void unlock() {
        lockPositions(false);
        this.locked = false;
    }

    private void lockPositions(boolean locked) {
        if (this.getPositions() != null && !this.getPositions().isEmpty()) {
            for (U pos : positions) {
                pos.setLocked(locked);
            }
        }
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
     * Set the priority.
     * 
     * @param prio
     *            The priority to set.
     */
    public void setPriority(int prio) {
        // FIXME [scherrer] : Assure that all splits are updated too!!
        this.priority = prio < 0 ? 0 : prio;
    }

    /**
     * Get the latestDueDate.
     * 
     * @return the latestDueDate.
     */
    public Date getLatestDueDate() {
        return latestDueDate;
    }

    /**
     * Set the latestDueDate.
     * 
     * @param latestDueDate
     *            The latestDueDate to set.
     */
    public void setLatestDueDate(Date latestDueDate) {
        this.latestDueDate = latestDueDate;
    }

    /**
     * Get the startDate.
     * 
     * @return the startDate.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Set the startDate.
     * 
     * @param startDate
     *            The startDate to set.
     */
    void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Get the nextAllocationDate.
     * 
     * @return the nextAllocationDate.
     */
    public Date getNextAllocationDate() {
        return nextAllocationDate;
    }

    /**
     * Set the nextAllocationDate.
     * 
     * @param nextAllocationDate
     *            The nextAllocationDate to set.
     */
    void setNextAllocationDate(Date nextAllocationDate) {
        this.nextAllocationDate = nextAllocationDate;
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
     * Set the problem.
     * 
     * @param problem
     *            The problem to set.
     */
    void setProblem(Problem problem) {
        this.problem = problem;
    }

    /**
     * Get the positions.
     * 
     * @return the positions.
     */
    public Set<U> getPositions() {
        return Collections.unmodifiableSet(positions);
    }

    /**
     * Add one or more {@link OrderPosition}s to this AbstractOrder.
     * 
     * @param pos
     *            {@link OrderPosition}s to add
     * @return <code>true</code> if this set changed as a result of the call
     */
    @SafeVarargs
    public final boolean addPostions(U... pos) {
        boolean res = this.positions.addAll(Arrays.asList(pos));
        if (res) {
            // FIXME [scherrer] : write a test
            this.noPositions += pos.length;
        }
        return res;
    }

    /**
     * Remove one or more {@link OrderPosition}s from this AbstractOrder.
     * 
     * @param pos
     *            The {@link OrderPosition}s to remove.
     */
    @SafeVarargs
    public final boolean removePositions(U... pos) {
        boolean res = this.positions.removeAll(Arrays.asList(pos));
        if (res) {
            // FIXME [scherrer] : write a test
            this.noPositions -= pos.length;
        }
        return res;
    }

    /**
     * {@inheritDoc}
     * 
     * Return the orderId.
     */
    @Override
    public String toString() {
        return this.orderId;
    }
}