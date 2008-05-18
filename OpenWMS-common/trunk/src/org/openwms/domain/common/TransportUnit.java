/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.openwms.domain.common.system.IUnitError;
import org.openwms.domain.common.system.usermanagement.IUser;

/**
 * 
 * A TransportUnit.
 * <p>
 * Used as a container to transport items and <code>LoadUnit</code>s.<br>
 * It can be moved between <code>Location</code>s.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
@Entity
@Table(name = "TRANSPORT_UNIT", uniqueConstraints = @UniqueConstraint(columnNames = ("UNIT_ID")))
public class TransportUnit implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Primary key.
	 */
	@Id
	@Column(name = "ID")
	private long id;

	/**
	 * Natural key.
	 */
	@Column(name = "UNIT_ID", unique = true)
	private String unitId;

	/**
	 * Indicates whether the <code>TransportUnit</code> is empty or not.
	 */
	@Column(name = "EMPTY")
	private boolean empty;

	/**
	 * Timestamp when the <code>TransportUnit</code> has been created.
	 */
	@Column(name = "CREATION_DATE")
	private Date creationDate;

	/**
	 * Timestamp when this <code>TransportUnit</code> moved to the
	 * actualLocation.
	 */
	@Column(name = "ACTUAL_LOCATION_DATE")
	private Date actualLocationDate;

	/**
	 * Timestamp of last inventory.
	 */
	@Column(name = "INVENTORY_DATE")
	private Date inventoryDate;

	/**
	 * Weight of this <code>TransportUnit</code>.
	 */
	@Column(name = "WEIGHT")
	private BigDecimal weight;

	/**
	 * State of this <code>TransportUnit</code>.
	 */
	@Column(name = "STATE")
	private short state;

	/**
	 * Version field
	 */
	@Version
	private long version;

	/* ------------------- collection mapping ------------------- */
	/**
	 * The actual Location of the TransportUnit.
	 */
	@ManyToOne
	@JoinColumn(name = "ID")
	private ILocation actualLocation;

	/**
	 * The target Location of the TransportUnit.<br>
	 * This property should be set when starting a new TransportOrder.
	 */
	@ManyToOne
	@JoinColumn(name = "ID")
	private ILocation targetLocation;

	/**
	 * The <code>TransportUnitType</code> of this <code>TransportUnit</code>.
	 */
	@ManyToOne
	@JoinColumn(name = "TYPE")
	private ITransportUnitType transportUnitType;

	/**
	 * Owning <code>TransportUnit</code>.
	 */
	@ManyToOne
	private TransportUnit parent;

	/**
	 * The <code>User</code> who did the last inventory action on this
	 * <code>TransportUnit</code>.
	 */
	@ManyToOne
	@JoinColumn(name = "USERNAME")
	private IUser inventoryUser;

	/**
	 * Child <code>TransportUnit</code>s.
	 */
	@OneToMany(mappedBy = "parent")
	private Set<TransportUnit> transportUnits;

	/**
	 * A set of occurred errors on this <code>TransportUnit</code>.
	 */
	@OneToMany(mappedBy = "id")
	private Map<Date, IUnitError> errors;

	/* ----------------------------- methods ------------------- */
	public TransportUnit() {
		super();
		this.creationDate = new Date();
	}

	/**
	 * Get the actualLocation.
	 * 
	 * @return the actualLocation.
	 */
	public ILocation getActualLocation() {
		return actualLocation;
	}

	/**
	 * Set the actualLocation.
	 * 
	 * @param actualLocation
	 *            The actualLocation to set.
	 */
	public void setActualLocation(ILocation actualLocation) {
		this.actualLocation = actualLocation;
		this.actualLocationDate = new Date();
	}

	/**
	 * Get the targetLocation.
	 * 
	 * @return the targetLocation.
	 */
	public ILocation getTargetLocation() {
		return targetLocation;
	}

	/**
	 * Set the targetLocation.
	 * 
	 * @param targetLocation
	 *            The targetLocation to set.
	 */
	public void setTargetLocation(ILocation targetLocation) {
		this.targetLocation = targetLocation;
	}

	/**
	 * Get the id.
	 * 
	 * @return the id.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Indicates whether the <code>TransportUnit</code> is empty or not.
	 * 
	 * @return<br> - true if empty <br> - false if not empty.
	 */
	public boolean isEmpty() {
		return this.empty;
	}

	/**
	 * Sets this <code>TransportUnit</code> to be empty.
	 * 
	 * @param empty
	 */
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	/**
	 * Returns the <code>User</code> who did the last inventory action on this
	 * <code>TransportUnit</code>.
	 * 
	 * @return
	 */
	public IUser getInventoryUser() {
		return this.inventoryUser;
	}

	/**
	 * Set the <code>User</code> who did the last inventory action on this
	 * <code>TransportUnit</code>.
	 * 
	 * @param inventoryUser
	 */
	public void setInventoryUser(IUser inventoryUser) {
		this.inventoryUser = inventoryUser;
	}

	/**
	 * Number of <code>TransportUnit</code>s belonging to this
	 * <code>TransportUnit</code>.
	 */
	public int getNoTransportUnits() {
		return this.transportUnits.size();
	}

	/**
	 * Returns the date when the <code>TransportUnit</code> was been created.
	 * 
	 * @return
	 */
	public Date getCreationDate() {
		return this.creationDate;
	}

	/**
	 * Returns the timestamp when this <code>TransportUnit</code> moved to the
	 * actualLocation.
	 * 
	 * @return
	 */
	public Date getActualLocationDate() {
		return this.actualLocationDate;
	}

	/**
	 * Returns the timestamp of the last inventory action of this
	 * <code>TransportUnit</code>.
	 * 
	 * @return
	 */
	public Date getInventoryDate() {
		return this.inventoryDate;
	}

	/**
	 * Set the timestamp of the last inventory action of this
	 * <code>TransportUnit</code>.
	 * 
	 * @param inventoryDate
	 */
	public void setInventoryDate(Date inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	/**
	 * Returns the actual weight of this <code>TransportUnit</code>.
	 * 
	 * @return
	 */
	public BigDecimal getWeight() {
		return this.weight;
	}

	/**
	 * Sets the actual weight of this <code>TransportUnit</code>.
	 * 
	 * @param weight
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * Get all errors that occurred on this <code>TransportUnit</code>.
	 * 
	 * @return
	 */
	public Collection<IUnitError> getErrors() {
		if (errors == null) {
			errors = new HashMap<Date, IUnitError>();
		}
		return this.errors.values();
	}

	/**
	 * Add an error for this <code>TransportUnit</code>.
	 * 
	 * @param error
	 */
	public void addError(IUnitError error) {
		if (errors == null) {
			errors = new HashMap<Date, IUnitError>();
		}
		this.errors.put(new Date(), error);
	}

	/**
	 * Get the state of this <code>TransportUnit</code>.
	 * 
	 * @return
	 */
	public short getState() {
		return this.state;
	}

	/**
	 * Set the state of this <code>TransportUnit</code>.
	 * 
	 * @param state
	 */
	public void setState(short state) {
		this.state = state;
	}

	/**
	 * Get the <code>TransportUnitType</code> of this
	 * <code>TransportUnit</code>.
	 * 
	 * @return
	 */
	public ITransportUnitType getTransportUnitType() {
		return this.transportUnitType;
	}

	/**
	 * Set the <code>TransportUnitType</code> of this
	 * <code>TransportUnit</code>.
	 * 
	 * @param transportUnitType
	 */
	public void setTransportUnitType(ITransportUnitType transportUnitType) {
		this.transportUnitType = transportUnitType;
	}

	/**
	 * Get the unitId of this <code>TransportUnit</code>.
	 * 
	 * @return the unitId.
	 */
	public String getUnitId() {
		return unitId;
	}

	/**
	 * Set the unitId of this <code>TransportUnit</code>.
	 * 
	 * @param unitId
	 *            The unitId to set.
	 */
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	/**
	 * Get the parent.
	 * 
	 * @return the parent.
	 */
	public TransportUnit getParent() {
		return parent;
	}

	/**
	 * Set the parent.
	 * 
	 * @param parent
	 *            The parent to set.
	 */
	public void setParent(TransportUnit parent) {
		this.parent = parent;
	}

	/**
	 * Get the transportUnits.
	 * 
	 * @return the transportUnits.
	 */
	public Set<TransportUnit> getTransportUnits() {
		return transportUnits;
	}

	/**
	 * Set the transportUnits.
	 * 
	 * @param transportUnits
	 *            The transportUnits to set.
	 */
	public void setTransportUnits(Set<TransportUnit> transportUnits) {
		this.transportUnits = transportUnits;
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
