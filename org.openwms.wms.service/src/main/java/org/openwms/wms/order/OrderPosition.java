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

import org.openwms.common.values.Problem;
import org.openwms.core.AbstractEntity;
import org.openwms.core.DomainObject;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import java.util.Date;

/**
 * An OrderPosition.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "WMS_ORDER_POSITION", uniqueConstraints = @UniqueConstraint(columnNames = { "C_ORDER_ID_K",
        "C_POSITION_NO_K" }))
public abstract class OrderPosition<T extends AbstractOrder<T, U>, U extends OrderPosition<T, U>> extends
        AbstractEntity<Long> {

    /** Unique technical key. */
    @Id
    @Column(name = "C_ID")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ManyToOne(optional = false, targetEntity = AbstractOrder.class)
    @JoinColumn(name = "C_ORDER_ID")
    private T order;

    /** Business key. */
    @Embedded
    private OrderPositionKey positionId;

    /** Current order state. Inherited from the order. Default is {@value} */
    @Enumerated(EnumType.STRING)
    @Column(name = "C_ORDER_STATE")
    private OrderState orderState = OrderState.UNDEFINED;

    /**
     * Property to lock/unlock an OrderPosition. This is usually done via the
     * Order.
     * <ul>
     * <li>true: locked</li>
     * <li>false: unlocked</li>
     * </ul>
     * Default is {@value}
     */
    @Column(name = "C_LOCKED")
    private boolean locked = false;

    /** Current priority of the OrderPosition. */
    @Column(name = "C_PRIORITY")
    private int priority;

    /** Latest finish date of this order. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_LASTEST_DUE")
    private Date latestDueDate;

    /** Latest problem that is occurred on this OrderPosition. */
    @Embedded
    private Problem problem;

    /** Date when the OrderPosition should be started earliest. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_START_EARLIEST")
    private Date earliestStartDate;

    /** Date when the OrderPosition was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_CREATION_DATE")
    private Date creationDate;

    /** Version field. */
    @Version
    @Column(name = "C_VERSION")
    private long version;

    /**
     * Used by the JPA provider.
     */
    protected OrderPosition() {
        super();
    }

    /**
     * Create a new OrderPosition with an order and a positionNo.
     * 
     * @param ord
     *            The order of this position
     * @param posNo
     *            The current position number within the order
     */
    @SuppressWarnings("unchecked")
    public OrderPosition(T ord, Integer posNo) {
        super();
        Assert.notNull(ord);
        Assert.notNull(posNo);
        this.order = ord;
        this.order.addPostions((U) this);
        this.positionId = new OrderPositionKey(ord, posNo);
    }

    /**
     * Before the entity is persisted:
     * <ul>
     * <li>the orderState is set</li>
     * </ul>
     * 
     */
    @PrePersist
    protected void prePersist() {
        this.orderState = OrderState.CREATED;
    }

    /**
     * After the entity is persisted:
     * <ul>
     * <li>set the creationDate</li>
     * </ul>
     */
    @PostPersist
    protected void postPersist() {
        this.creationDate = new Date();
    }

    /**
     * @see DomainObject#isNew()
     */
    @Override
    public boolean isNew() {
        return this.id == null;
    }

    /**
     * @see DomainObject#getVersion()
     */
    @Override
    public long getVersion() {
        return this.version;
    }

    /**
     * @see DomainObject#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Get the positionId.
     * 
     * @return the positionId.
     */
    public OrderPositionKey getPositionId() {
        return positionId;
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
     * Set the locked.
     * 
     * @param locked
     *            The locked to set.
     */
    void setLocked(boolean locked) {
        this.locked = locked;
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
     * @param priority
     *            The priority to set.
     */
    public void setPriority(int priority) {
        this.priority = priority;
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
     * Get the order.
     * 
     * @return the order.
     */
    public T getOrder() {
        return order;
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
     * Get the earliestStartDate.
     * 
     * @return the earliestStartDate.
     */
    public Date getEarliestStartDate() {
        return earliestStartDate;
    }

    /**
     * Get the creationDate.
     * 
     * @return the creationDate.
     */
    public Date getCreationDate() {
        return creationDate;
    }
}