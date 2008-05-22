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

import org.openwms.domain.common.system.UnitError;
import org.openwms.domain.common.system.usermanagement.User;
import javax.persistence.JoinColumns;

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
	@JoinColumn(name = "ID", insertable = false, updatable = false)
	private Location actualLocation;

	/**
	 * The target Location of the TransportUnit.<br>
	 * This property should be set when starting a new TransportOrder.
	 */
	@ManyToOne
	@JoinColumn(name = "ID", insertable = false, updatable = false)
	private Location targetLocation;

	/**
	 * The <code>TransportUnitType</code> of this <code>TransportUnit</code>.
	 */
	@ManyToOne
	@JoinColumn(name = "TYPE")
	private TransportUnitType transportUnitType;

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
	private User inventoryUser;

	/**
	 * Child <code>TransportUnit</code>s.
	 */
	@OneToMany(mappedBy = "parent")
	private Set<TransportUnit> transportUnits;

	/**
	 * A set of occurred errors on this <code>TransportUnit</code>.
	 */
	@OneToMany(mappedBy = "id")
	private Map<Date, UnitError> errors;

	/* ----------------------------- methods ------------------- */
	public TransportUnit() {
		super();
		this.creationDate = new Date();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openwms.domain.common.ITransportUnit#getActualLocation()
	 */
	public Location getActualLocation() {
		return actualLocation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openwms.domain.common.ITransportUnit#setActualLocation(org.openwms.domain.common.ILocation)
	 */
	public void setActualLocation(Location actualLocation) {
		this.actualLocation = actualLocation;
		this.actualLocationDate = new Date();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openwms.domain.common.ITransportUnit#getTargetLocation()
	 */
	public Location getTargetLocation() {
		return targetLocation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openwms.domain.common.ITransportUnit#setTargetLocation(org.openwms.domain.common.ILocation)
	 */
	public void setTargetLocation(Location targetLocation) {
		this.targetLocation = targetLocation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openwms.domain.common.ITransportUnit#getId()
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
	public User getInventoryUser() {
		return this.inventoryUser;
	}

	/**
	 * Set the <code>User</code> who did the last inventory action on this
	 * <code>TransportUnit</code>.
	 * 
	 * @param inventoryUser
	 */
	public void setInventoryUser(User inventoryUser) {
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
	public Collection<UnitError> getErrors() {
		if (errors == null) {
			errors = new HashMap<Date, UnitError>();
		}
		return this.errors.values();
	}

	/**
	 * Add an error for this <code>TransportUnit</code>.
	 * 
	 * @param error
	 */
	public void addError(UnitError error) {
		if (errors == null) {
			errors = new HashMap<Date, UnitError>();
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
	public TransportUnitType getTransportUnitType() {
		return this.transportUnitType;
	}

	/**
	 * Set the <code>TransportUnitType</code> of this
	 * <code>TransportUnit</code>.
	 * 
	 * @param transportUnitType
	 */
	public void setTransportUnitType(TransportUnitType transportUnitType) {
		this.transportUnitType = transportUnitType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openwms.domain.common.ITransportUnit#getUnitId()
	 */
	public String getUnitId() {
		return unitId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openwms.domain.common.ITransportUnit#setUnitId(java.lang.String)
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
