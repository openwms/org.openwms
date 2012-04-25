package org.openwms.wms.domain.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.openwms.wms.domain.types.WMSTypes;

/**
 * A OrderPositionKey is an unique composite key of an
 * <code>OrderPosition</code> instance.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Embeddable
public class OrderPositionKey implements Serializable, Comparable<OrderPositionKey> {

    private static final long serialVersionUID = -2834093066793437930L;

    /**
     * The order id of the <code>AbstractOrder</code> instance, the
     * <code>OrderPosition</code> belongs to.
     */
    @Column(name = "C_ORDER_ID_K", length = WMSTypes.ORDER_ID_LENGTH, nullable = false)
    private String orderId;

    /**
     * The position number is a unique index within a single
     * <code>AbstractOrder</code> instance.
     */
    @Column(name = "C_POSITION_NO_K", length = WMSTypes.POSITION_NO_LENGTH, nullable = false)
    private String positionNo;

    /**
     * Used by the JPA Provider.
     */
    protected OrderPositionKey() {
        super();
    }

    /**
     * Create a new OrderPositionKey with orderId and positionNo.
     * 
     * @param ordId
     *            orderId
     * @param posNo
     *            position
     */
    public OrderPositionKey(String ordId, String posNo) {
        super();
        this.orderId = ordId;
        this.positionNo = posNo;
    }

    /**
     * Create a new OrderPositionKey with an order and a positionNo.
     * 
     * @param ord
     *            The order of this position
     * @param posNo
     *            The current position number within the order
     */
    public OrderPositionKey(AbstractOrder ord, String posNo) {
        super();
        this.positionNo = posNo;
        this.orderId = ord.getOrderId();
    }

    /**
     * Get the positionNo.
     * 
     * @return the positionNo
     */
    public String getPositionNo() {
        return this.positionNo;
    }

    /**
     * Get the orderId.
     * 
     * @return the orderId
     */
    public String getOrderId() {
        return this.orderId;
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(OrderPositionKey o) {
        if (this.getOrderId().equals(o.getOrderId()) && this.getPositionNo().equals(o.getPositionNo())) {
            return 0;
        }
        if (this.getOrderId().compareTo(o.getOrderId()) == 0) {
            return this.getPositionNo().compareTo(o.getPositionNo());
        }
        return this.getOrderId().compareTo(o.getOrderId());
    }

    /**
     * {@inheritDoc}
     * 
     * Use <code>orderId</code> and <code>positionNo</code> for calculation.
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
        result = prime * result + ((positionNo == null) ? 0 : positionNo.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * Use <code>orderId</code> and <code>positionNo</code> for comparison.
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        OrderPositionKey other = (OrderPositionKey) obj;
        if (orderId == null) {
            if (other.orderId != null) {
                return false;
            }
        } else if (!orderId.equals(other.orderId)) {
            return false;
        }
        if (positionNo == null) {
            if (other.positionNo != null) {
                return false;
            }
        } else if (!positionNo.equals(other.positionNo)) {
            return false;
        }
        return true;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.orderId + " / " + this.positionNo;
    }
}