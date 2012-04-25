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
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.domain.DomainObject;
import org.openwms.wms.domain.values.TransportOrderStartMode;

/**
 * A OrderPosition.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "WMS_ORDER_POSITION", uniqueConstraints = @UniqueConstraint(columnNames = { "C_ORDER_ID_K",
        "C_POSITION_NO_K" }))
public class OrderPosition extends AbstractEntity implements DomainObject<Long> {

    private static final long serialVersionUID = -5460243846848177680L;

    /** Unique technical key. */
    @Id
    @Column(name = "C_ID")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "C_ORDER_ID", insertable = false, updatable = false, nullable = false)
    private AbstractOrder order;

    /** Business key. */
    @Embedded
    private OrderPositionKey positionId;

    /** Current order state. Inherited from the order. Default is {@value} */
    @Enumerated(EnumType.STRING)
    @Column(name = "C_ORDER_STATE")
    private OrderState orderState = OrderState.UNDEFINED;

    /** Defines how a TransportOrder is started. Default is {@value} */
    @Enumerated(EnumType.STRING)
    @Column(name = "C_TO_START_MODE")
    private TransportOrderStartMode startMode = TransportOrderStartMode.AUTOMATIC;

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
    public OrderPosition(AbstractOrder ord, String posNo) {
        super();
        this.order = ord;
        this.order.addPostions(this);
        this.orderState = this.order.getOrderState();
        this.positionId = new OrderPositionKey(ord, posNo);
    }

    /**
     * After data was loaded, initialize some fields with transient values.
     */
    @PostLoad
    protected void postLoad() {}

    /**
     * @see org.openwms.core.domain.DomainObject#isNew()
     */
    @Override
    public boolean isNew() {
        return this.id == null;
    }

    /**
     * @see org.openwms.core.domain.DomainObject#getVersion()
     */
    @Override
    public long getVersion() {
        return this.version;
    }

    /**
     * @see org.openwms.core.domain.DomainObject#getId()
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
     * Get the startMode.
     * 
     * @return the startMode.
     */
    public TransportOrderStartMode getStartMode() {
        return startMode;
    }

    /**
     * Get the order.
     * 
     * @return the order.
     */
    public AbstractOrder getOrder() {
        return order;
    }
}