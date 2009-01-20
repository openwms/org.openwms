/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.tms.domain.order;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationGroup;
import org.openwms.common.domain.TransportUnit;
import org.openwms.tms.domain.IllegalStateException;
import org.openwms.tms.domain.InsufficientValueException;
import org.openwms.tms.domain.TransportManagementException;
import org.openwms.tms.domain.values.Problem;

/**
 * 
 * A TransportOrder.
 * <p>
 * Is used to move <tt>TransportUnit</tt>s from an actual <tt>Location</tt> to a target <tt>Location</tt>. A
 * <tt>TransportOrder</tt> can only be processed in a specific state (STARTED).
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 40 $
 */
@Entity
@Table(name = "TRANSPORT_ORDER")
public class TransportOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum TRANSPORT_ORDER_STATE {
	CREATED, INITIALIZED, STARTED, INTERRUPTED, ONFAILURE, FINISHED
    }

    /**
     * Primary key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    /**
     * The <tt>TransportUnit</tt> to be moved by this <tt>TransportOrder</tt>.
     */
    @ManyToOne
    @JoinColumn(name = "TRANSPORT_UNIT")
    private TransportUnit transportUnit;

    /**
     * Date when the <tt>TransportOrder</tt> was last updated.
     */
    @Column(name = "DATE_UPDATED")
    private Date dateUpdated;

    /**
     * A priority level of the <tt>TransportOrder</tt>. The lower the value the lower the priority.<br>
     * The priority level affects the execution of the <tt>TransportOrder</tt>. A high priored order will be processed
     * faster than lower ones.
     */
    @Column(name = "PRIORITY")
    private short priority = 0;

    /**
     * Timestamp when the <tt>TransportOrder</tt> was started.
     */
    @Column(name = "START_DATE")
    private Date startDate;

    /**
     * Last problem on this <tt>TransportOrder</tt>.
     */
    @Column(name = "PROBLEM")
    private Problem problem;

    /**
     * Timestamp when the <tt>TransportOrder</tt> was created.
     */
    @Column(name = "CREATION_DATE")
    private Date creationDate;

    /**
     * Timestamp when the <tt>TransportOrder</tt> ended.
     */
    @Column(name = "END_DATE")
    private Date endDate;

    /**
     * State of this <code>TransportOrder</code>.
     */
    @Column(name = "STATE")
    private TRANSPORT_ORDER_STATE state;

    /**
     * The source <tt>Location</tt> of the <tt>TransportOrder</tt>.<br>
     * This property should be set before starting the <tt>TransportOrder</tt>.
     */
    @ManyToOne
    @JoinColumn(name = "SOURCE_LOCATION")
    private Location sourceLocation;

    /**
     * The target <tt>Location</tt> of the <tt>TransportOrder</tt>.<br>
     * This property should be set before starting the <tt>TransportOrder</tt>.
     */
    @ManyToOne
    @JoinColumn(name = "TARGET_LOCATION")
    private Location targetLocation;

    /**
     * A <tt>LocationGroup</tt> can also set as target. When the <tt>TransportOrder</tt> will be started, a target must
     * be set.
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
     * Create a new <code>TransportOrder</code>.
     */
    public TransportOrder() {
	this.creationDate = new Date();
	this.state = TRANSPORT_ORDER_STATE.CREATED;
    }

    /**
     * Returns the Primary Key.
     * 
     * @return id.
     */
    public Long getId() {
	return this.id;
    }

    /**
     * Check whether the instance is transient.
     * 
     * @return
     */
    public boolean isNew() {
	return (this.id == null);
    }

    /**
     * Get the priority level of this <tt>TransportOrder</tt>.
     * 
     * @return priority.
     */
    public short getPriority() {
	return this.priority;
    }

    /**
     * Set the priority level of this <tt>TransportOrder</tt>.The lower the value the lower the priority.
     * 
     * @param priority
     */
    public void setPriority(short priority) {
	this.priority = priority;
    }

    /**
     * Gets the date when the <tt>TransportOrder</tt> was started.
     * 
     * @return startDate
     */
    public Date getStartDate() {
	return this.startDate;
    }

    /**
     * Set the date when the <tt>TransportOrder</tt> was started.
     * 
     * @param startDate
     */
    private void setStartDate(Date startDate) {
	this.startDate = startDate;
    }

    /**
     * Get the <tt>TransportUnit</tt> belonging to this <tt>TransportOrder</tt>.
     * 
     * @return transportUnit.
     */
    public TransportUnit getTransportUnit() {
	return this.transportUnit;
    }

    /**
     * Set a <tt>TransportUnit</tt> to this <tt>TransportOrder</tt>.
     * 
     * @param transportUnit
     */
    public void setTransportUnit(TransportUnit transportUnit) {
	this.transportUnit = transportUnit;
    }

    /**
     * Get the date when this <tt>TransportOrder</tt> was created.
     * 
     * @return
     */
    public Date getCreationDate() {
	return this.creationDate;
    }

    /**
     * Get the state of this <tt>TransportOrder</tt>.
     * 
     * @return
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
	    if (newState != TRANSPORT_ORDER_STATE.INITIALIZED) {
		// Don't allow to except the initialization
		throw new IllegalStateException("TransportOrder must be initialized after creation");
	    }
	    validateInitializationCondition();
	}
    }

    /**
     * Set the state of this <tt>TransportOrder</tt>.
     * 
     * @param newState
     */
    public void setState(TRANSPORT_ORDER_STATE newState) throws TransportManagementException {
	validateStateChange(newState);
	if (newState == TRANSPORT_ORDER_STATE.STARTED) {
	    setStartDate(new Date());
	}
	state = newState;
    }

    /**
     * Get the target <tt>Location</tt> of this <tt>TransportOrder</tt>.
     * 
     * @return targetLocation
     */
    public Location getTargetLocation() {
	return this.targetLocation;
    }

    /**
     * Set the target <tt>Location</tt> of this <tt>TransportOrder</tt>.
     * 
     * @param targetLocation
     */
    public void setTargetLocation(Location targetLocation) {
	this.targetLocation = targetLocation;
    }

    /**
     * Get the date when the <tt>TransportOrder</tt> was last updated.
     * 
     * @return the dateUpdated.
     */
    public Date getDateUpdated() {
	return dateUpdated;
    }

    /**
     * Get the targetLocationGroup.
     * 
     * @return the targetLocationGroup.
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
     * Get the last <tt>Problem</tt>.
     * 
     * @return the problem.
     */
    public Problem getProblem() {
	return problem;
    }

    /**
     * Set the last <tt>Problem</tt>.
     * 
     * @param problem
     *            The <tt>Problem</tt> to set.
     */
    public void setProblem(Problem problem) {
	this.problem = problem;
    }

    /**
     * Get the endDate.
     * 
     * @return the endDate.
     */
    public Date getEndDate() {
	return endDate;
    }

    /**
     * Get the sourceLocation.
     * 
     * @return the sourceLocation.
     */
    public Location getSourceLocation() {
	return sourceLocation;
    }

    /**
     * Set the sourceLocation.
     * 
     * @param sourceLocation
     *            The sourceLocation to set.
     */
    public void setSourceLocation(Location sourceLocation) {
	this.sourceLocation = sourceLocation;
    }

    /**
     * JPA optimistic locking: Returns version field.
     * 
     * @return
     */
    public long getVersion() {
	return this.version;
    }
}
