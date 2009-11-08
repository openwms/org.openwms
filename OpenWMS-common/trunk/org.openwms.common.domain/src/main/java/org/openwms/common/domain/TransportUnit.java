/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.openwms.common.domain.system.UnitError;
import org.openwms.common.domain.system.usermanagement.User;
import org.openwms.common.domain.values.Barcode;

/**
 * A TransportUnit.
 * <p>
 * Used as a container to transport items and <code>LoadUnit</code>s.<br>
 * It can be moved between <code>Location</code>s.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 */
@Entity
@Table(name = "TRANSPORT_UNIT", uniqueConstraints = @UniqueConstraint(columnNames = { "BARCODE" }))
@NamedQueries( {
		@NamedQuery(name = "TransportUnit.findAll", query = "select tu from TransportUnit tu"),
		@NamedQuery(name = "TransportUnit.findByBarcode", query = "select tu from TransportUnit tu where tu.barcode = ?1") })
public class TransportUnit implements Serializable {

	private static final long serialVersionUID = 1L;

	public static enum TU_STATE {
		AVAILABLE, OK, NOT_OK
	}

	/**
	 * Primary key.
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;

	/**
	 * Natural key.
	 */
	@Column(name = "BARCODE", unique = true)
	private Barcode barcode;

	/**
	 * Indicates whether the <code>TransportUnit</code> is empty or not.
	 */
	@Column(name = "EMPTY")
	private Boolean empty;

	/**
	 * Timestamp when the <code>TransportUnit</code> has been created.
	 */
	@Column(name = "CREATION_DATE")
	private Date creationDate;

	/**
	 * Timestamp when this <code>TransportUnit</code> moved to the actualLocation.
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
	private TU_STATE state;

	/**
	 * Version field
	 */
	@Version
	private long version;

	/* ------------------- collection mapping ------------------- */
	/**
	 * The actual <tt>Location</tt> of the <tt>TransportUnit</tt>.
	 */
	@ManyToOne
	@JoinColumn(name = "ACTUAL_LOCATION", nullable = false)
	private Location actualLocation;

	/**
	 * The target <tt>Location</tt> of the <tt>TransportUnit</tt>.<br>
	 * This property will be set when a <tt>TransportOrder</tt> is started.
	 */
	@ManyToOne
	@JoinColumn(name = "TARGET_LOCATION")
	private Location targetLocation;

	/**
	 * The <code>TransportUnitType</code> of this <code>TransportUnit</code>.
	 */
	@ManyToOne
	@JoinColumn(name = "TRANSPORT_UNIT_TYPE", nullable = false)
	private TransportUnitType transportUnitType;

	/**
	 * Owning <code>TransportUnit</code>.
	 */
	@ManyToOne
	@JoinColumn(name = "PARENT")
	private TransportUnit parent;

	/**
	 * The <code>User</code> who did the last inventory action on this <code>TransportUnit</code>.
	 */
	@ManyToOne
	@JoinColumn(name = "INVENTORY_USER")
	private User inventoryUser;

	/**
	 * Child <code>TransportUnit</code>s.
	 */
	@OneToMany(mappedBy = "parent", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@OrderBy("id DESC")
	private Set<TransportUnit> children = new HashSet<TransportUnit>();

	/**
	 * A set of occurred errors on this <code>TransportUnit</code>.
	 */
	@OneToMany(cascade = CascadeType.ALL)
	private Map<Date, UnitError> errors = new HashMap<Date, UnitError>();

	/* ----------------------------- methods ------------------- */
	/**
	 * Accessed by persistence provider.
	 */
	@SuppressWarnings("unused")
	private TransportUnit() {}

	/**
	 * Create a new <code>TransportUnit</code> with a unique unitId.
	 */
	public TransportUnit(String unitId) {
		this.creationDate = new Date();
		this.barcode = new Barcode(unitId);
	}

	/**
	 * Create a new <code>TransportUnit</code> with a unique barcode.
	 */
	public TransportUnit(Barcode barcode) {
		this.creationDate = new Date();
		this.barcode = barcode;
	}

	/**
	 * Return the Primary Key.
	 * 
	 * @return id.
	 */
	public Long getId() {
		return id;
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
	 * Get the actual <tt>Location</tt> of this <tt>TransportUnit</tt>.
	 * 
	 * @return
	 */
	public Location getActualLocation() {
		return actualLocation;
	}

	/**
	 * Set the actual <tt>Location</tt> of this <tt>TransportUnit</tt>.
	 * 
	 * @param actualLocation
	 */
	public void setActualLocation(Location actualLocation) {
		this.actualLocation = actualLocation;
		this.actualLocationDate = new Date();
	}

	/**
	 * Get the target <tt>Location</tt> of this <tt>TransportUnit</tt>. This property is only set when a started
	 * <tt>TransportOrder</tt> exists.
	 * 
	 * @return Location.
	 */
	public Location getTargetLocation() {
		return this.targetLocation;
	}

	/**
	 * Set the target <tt>Location</tt> of this <tt>TransportUnit</tt>. Shall only be set when a started
	 * <tt>TransportOder</tt> exist.
	 * 
	 * @param targetLocation
	 */
	public void setTargetLocation(Location targetLocation) {
		this.targetLocation = targetLocation;
	}

	/**
	 * Indicates whether the <code>TransportUnit</code> is empty or not.
	 * 
	 * @return<br> - true if empty <br>
	 *             - false if not empty.
	 */
	public Boolean isEmpty() {
		return this.empty;
	}

	/**
	 * Sets this <code>TransportUnit</code> to be empty.
	 * 
	 * @param empty
	 */
	public void setEmpty(Boolean empty) {
		this.empty = empty;
	}

	/**
	 * Returns the <code>User</code> who did the last inventory action on this <code>TransportUnit</code>.
	 * 
	 * @return
	 */
	public User getInventoryUser() {
		return this.inventoryUser;
	}

	/**
	 * Set the <code>User</code> who did the last inventory action on this <code>TransportUnit</code>.
	 * 
	 * @param inventoryUser
	 */
	public void setInventoryUser(User inventoryUser) {
		this.inventoryUser = inventoryUser;
	}

	/**
	 * Number of <code>TransportUnit</code>s belonging to this <code>TransportUnit</code>.
	 */
	public int getNoTransportUnits() {
		return this.children.size();
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
	 * Returns the timestamp when this <code>TransportUnit</code> moved to the actualLocation.
	 * 
	 * @return
	 */
	public Date getActualLocationDate() {
		return this.actualLocationDate;
	}

	/**
	 * Returns the timestamp of the last inventory action of this <code>TransportUnit</code>.
	 * 
	 * @return
	 */
	public Date getInventoryDate() {
		return this.inventoryDate;
	}

	/**
	 * Set the timestamp of the last inventory action of this <code>TransportUnit</code>.
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
	public Map<Date, UnitError> getErrors() {
		return Collections.unmodifiableMap(errors);
	}

	/**
	 * Add an error for this <code>TransportUnit</code>.
	 * 
	 * @param error
	 */
	public UnitError addError(UnitError error) {
		if (error == null) {
			throw new IllegalArgumentException("Error may not be null!");
		}
		return errors.put(new Date(), error);
	}

	/**
	 * Get the state of this <code>TransportUnit</code>.
	 * 
	 * @return
	 */
	public TU_STATE getState() {
		return this.state;
	}

	/**
	 * Set the state of this <code>TransportUnit</code>.
	 * 
	 * @param state
	 */
	public void setState(TU_STATE state) {
		this.state = state;
	}

	/**
	 * Get the <code>TransportUnitType</code> of this <code>TransportUnit</code>.
	 * 
	 * @return
	 */
	public TransportUnitType getTransportUnitType() {
		return this.transportUnitType;
	}

	/**
	 * Set the <code>TransportUnitType</code> of this <code>TransportUnit</code>.
	 * 
	 * @param transportUnitType
	 */
	public void setTransportUnitType(TransportUnitType transportUnitType) {
		this.transportUnitType = transportUnitType;
	}

	/**
	 * Return the <tt>Barcode</tt> of the <tt>TransportUnit</tt>.
	 * 
	 * @return - Barcode
	 */
	public Barcode getBarcode() {
		return barcode;
	}

	/**
	 * Set the <tt>Barcode</tt> of the <tt>TransportUnit</tt>.
	 * 
	 * @param barcode
	 */
	public void setBarcode(Barcode barcode) {
		this.barcode = barcode;
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
	public Set<TransportUnit> getChildren() {
		return Collections.unmodifiableSet(children);
	}

	/**
	 * Add to children.
	 * 
	 * @param transportUnit
	 */
	public void addChild(TransportUnit transportUnit) {
		if (transportUnit == null) {
			throw new IllegalArgumentException("Child transportUnit is null!");
		}

		if (transportUnit.getParent() != null) {
			if (transportUnit.getParent().equals(this)) {
				// if this instance is already the parent, we just return
				return;
			} else {
				// disconnect post from it's current relationship
				transportUnit.getParent().children.remove(this);
			}
		}

		// make this instance the new parent
		transportUnit.setParent(this);
		children.add(transportUnit);
	}

	/**
	 * Remove from children.
	 * 
	 * @param transportUnit
	 */
	public void removeChild(TransportUnit transportUnit) {
		if (transportUnit == null) {
			throw new IllegalArgumentException("Child transportUnit is null!");
		}

		// make sure we are the parent before we break the relationship
		if (transportUnit.parent != null && transportUnit.getParent().equals(this)) {
			transportUnit.setParent(null);
			children.remove(transportUnit);
		} else {
			throw new IllegalArgumentException("Child transportUnit not associated with this instance");
		}
	}

	/**
	 * Set the actualLocationDate.
	 * 
	 * @param actualLocationDate
	 *            The actualLocationDate to set.
	 */
	public void setActualLocationDate(Date actualLocationDate) {
		this.actualLocationDate = actualLocationDate;
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
