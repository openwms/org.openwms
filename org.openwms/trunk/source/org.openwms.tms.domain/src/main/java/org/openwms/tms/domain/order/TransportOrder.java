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
package org.openwms.tms.domain.order;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationGroup;
import org.openwms.common.domain.TransportUnit;
import org.openwms.common.domain.system.Message;
import org.openwms.common.domain.values.Problem;
import org.openwms.common.exception.InsufficientValueException;
import org.openwms.tms.domain.values.PriorityLevel;

/**
 * A TransportOrder.
 * <p>
 * Is used to move {@link TransportUnit}s from an actual {@link Location} to a
 * target {@link Location}. A {@link TransportOrder} can only be processed in a
 * specific state (STARTED).
 * </p>
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.common.domain.TransportUnit
 * @see org.openwms.common.domain.Location
 */
@Entity
@Table(name = "TRANSPORT_ORDER")
@NamedQueries( { @NamedQuery(name = TransportOrder.NQ_FIND_ALL, query = "select to from TransportOrder to") })
public class TransportOrder implements Serializable {

    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 4586898047981474230L;

    /**
     * Query to find all {@link TransportOrder}s.
     */
    public static final String NQ_FIND_ALL = "TransportOrder.findAll";

    /**
     * A TRANSPORT_ORDER_STATE - Each {@link TransportOrder} can be in one of
     * these states.
     * 
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public enum TRANSPORT_ORDER_STATE {

        /**
         * Status of new created TransportOrders.
         */
        CREATED,

        /**
         * Status of a full initialized TransportOrder, ready to be started.
         */
        INITIALIZED,

        /**
         * A started TransportOrder, active to be executed. Only one per
         * TransportUnit allowed.
         */
        STARTED,

        /**
         * Status to indicate that the TransportOrder is paused. Not active
         * anymore.
         */
        INTERRUPTED,

        /**
         * Status to indicate a failure on the TransportOrder. Not active
         * anymore.
         */
        ONFAILURE,

        /**
         * Status of a aborted TransportOrder. Not active anymore.
         */
        CANCELED,

        /**
         * Status to indicate that the TransportOrder completed successfull.
         */
        FINISHED
    }

    /**
     * Unique technical key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    /**
     * The {@link TransportUnit} to be moved by this {@link TransportOrder}.
     */
    @ManyToOne
    @JoinColumn(name = "TRANSPORT_UNIT", nullable = false)
    private TransportUnit transportUnit;

    /**
     * Last date when the {@link TransportOrder} was updated.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATED")
    private Date dateUpdated = new Date();

    /**
     * A priority level of the {@link TransportOrder}. The lower the value the
     * lower the priority.<br>
     * The priority level affects the execution of the {@link TransportOrder}.
     * An order with high priority will be processed faster than lower ones.
     */
    @Column(name = "PRIORITY")
    @Enumerated
    private PriorityLevel priority = PriorityLevel.NORMAL;

    /**
     * Timestamp when the {@link TransportOrder} was started.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DATE")
    private Date startDate;

    /**
     * Last problem on this {@link TransportOrder}.
     */
    @Column(name = "PROBLEM")
    private Problem problem;

    /**
     * Timestamp when the {@link TransportOrder} was created.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATION_DATE")
    private Date creationDate = new Date();

    /**
     * Timestamp when the {@link TransportOrder} ended.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE")
    private Date endDate;

    /**
     * State of this {@link TransportOrder}.
     */
    @Column(name = "STATE")
    private TRANSPORT_ORDER_STATE state = TRANSPORT_ORDER_STATE.CREATED;

    /**
     * The source {@link Location} of the {@link TransportOrder}.<br>
     * This property should be set before starting the {@link TransportOrder}.
     */
    @ManyToOne
    @JoinColumn(name = "SOURCE_LOCATION")
    private Location sourceLocation;

    /**
     * The target {@link Location} of the {@link TransportOrder}.<br>
     * This property should be set before starting the {@link TransportOrder}.
     */
    @ManyToOne
    @JoinColumn(name = "TARGET_LOCATION")
    private Location targetLocation;

    /**
     * A {@link LocationGroup} can also be set as target. When the
     * {@link TransportOrder} will be started, at least one target must be set.
     */
    @ManyToOne
    @JoinColumn(name = "TARGET_LOCATION_GROUP")
    private LocationGroup targetLocationGroup;

    /**
     * Version field.
     */
    @Version
    private long version;

    /* ----------------------------- methods ------------------- */
    /**
     * Create a new {@link TransportOrder}.
     */
    public TransportOrder() {
        this.creationDate = new Date();
        this.state = TRANSPORT_ORDER_STATE.CREATED;
    }

    /**
     * Returns the unique technical key.
     * 
     * @return The unique technical key
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Checks if the instance is transient.
     * 
     * @return true: Entity is not present on the persistent storage.<br>
     *         false : Entity already exists on the persistence storage
     */
    public boolean isNew() {
        return (this.id == null);
    }

    /**
     * Get the priority level of this {@link TransportOrder}.
     * 
     * @return The priority
     */
    public PriorityLevel getPriority() {
        return this.priority;
    }

    /**
     * Set the priority level of this {@link TransportOrder}.
     * 
     * @param priority
     *            The priority to set
     */
    public void setPriority(PriorityLevel priority) {
        this.priority = priority;
    }

    /**
     * Returns the date when the {@link TransportOrder} was started.
     * 
     * @return The date when started
     */
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * Set the date when the {@link TransportOrder} was started.
     * 
     * @param startDate
     *            The date when started
     */
    private void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Get the {@link TransportUnit} moved by this {@link TransportOrder}.
     * 
     * @return The transportUnit to be moved
     */
    public TransportUnit getTransportUnit() {
        return this.transportUnit;
    }

    /**
     * Set a {@link TransportUnit} to be moved by this {@link TransportOrder}.
     * 
     * @param transportUnit
     *            The transportUnit to be moved
     */
    public void setTransportUnit(TransportUnit transportUnit) {
        this.transportUnit = transportUnit;
    }

    /**
     * Returns the date when this {@link TransportOrder} was created.
     * 
     * @return The date when created
     */
    public Date getCreationDate() {
        return this.creationDate;
    }

    /**
     * Returns the state of this {@link TransportOrder}.
     * 
     * @return The state of the order
     */
    public TRANSPORT_ORDER_STATE getState() {
        return this.state;
    }

    private void validateInitializationCondition() {
        if (transportUnit == null || (targetLocation == null && targetLocationGroup == null)) {
            throw new InsufficientValueException("Not all properties are set to switch transportOrder in next state");
        }
    }

    private void validateStateChange(TRANSPORT_ORDER_STATE newState) {
        if (newState == null) {
            throw new IllegalStateException("transportState cannot be null");
        }
        if (getState().compareTo(newState) > 0) {
            // Don't allow to turn back the state!
            throw new IllegalStateException("Turning back state of transportOrder not allowed");
        }
        if (getState() == TRANSPORT_ORDER_STATE.CREATED) {
            if (newState != TRANSPORT_ORDER_STATE.INITIALIZED && newState != TRANSPORT_ORDER_STATE.CANCELED) {
                // Don't allow to except the initialization
                throw new IllegalStateException("TransportOrder must be initialized after creation");
            }
            validateInitializationCondition();
        }
    }

    /**
     * Set the state of this {@link TransportOrder}.
     * 
     * @param newState
     *            The new state to set
     * @throws IllegalStateException
     *             in case
     *             <li>the newState is <code>null</code> or</li>
     *             <li>the newState is less than the old state or</li>
     *             <li>the TransportOrder is in state CREATED and shall be
     *             manually turned into something else then INITIALIZED or CANCELED</li>
     * @throws InsufficientValueException
     *             in case the TransportOrder is CREATED and shall be turned
     *             into INITIALIZED but is incomplete.
     */
    public void setState(TRANSPORT_ORDER_STATE newState) {
        validateStateChange(newState);
        if (newState == TRANSPORT_ORDER_STATE.STARTED) {
            setStartDate(new Date());
        }
        state = newState;
    }

    /**
     * Get the target {@link Location} of this {@link TransportOrder}.
     * 
     * @return The targetLocation, if any
     */
    public Location getTargetLocation() {
        return this.targetLocation;
    }

    /**
     * Set the target {@link Location} of this {@link TransportOrder}.
     * 
     * @param targetLocation
     *            The location to move on
     */
    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }

    /**
     * Get the date when the {@link TransportOrder} was changed last time.
     * 
     * @return The date of last update
     */
    public Date getDateUpdated() {
        return dateUpdated;
    }

    /**
     * Get the targetLocationGroup.
     * 
     * @return The targetLocationGroup, if any
     */
    public LocationGroup getTargetLocationGroup() {
        return targetLocationGroup;
    }

    /**
     * Set the targetLocationGroup.
     * 
     * @param targetLocationGroup
     *            The targetLocationGroup to set.
     */
    public void setTargetLocationGroup(LocationGroup targetLocationGroup) {
        this.targetLocationGroup = targetLocationGroup;
    }

    /**
     * Get the last {@link Problem}.
     * 
     * @return The last problem.
     */
    public Problem getProblem() {
        return problem;
    }

    /**
     * Set the last {@link Problem}.
     * 
     * @param problem
     *            The {@link Problem} to set.
     */
    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    /**
     * Get the endDate.
     * 
     * @return The date the order ended
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Get the sourceLocation.
     * 
     * @return The sourceLocation
     */
    public Location getSourceLocation() {
        return sourceLocation;
    }

    /**
     * Set the sourceLocation.
     * 
     * @param sourceLocation
     *            The sourceLocation to set
     */
    public void setSourceLocation(Location sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    /**
     * JPA optimistic locking.
     * 
     * @return The version field
     */
    public long getVersion() {
        return this.version;
    }
}
