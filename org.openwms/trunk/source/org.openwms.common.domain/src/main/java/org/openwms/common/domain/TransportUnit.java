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
package org.openwms.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.openwms.common.domain.system.UnitError;
import org.openwms.common.domain.system.usermanagement.User;
import org.openwms.common.domain.values.Barcode;
import org.openwms.common.domain.values.TransportUnitState;

/**
 * A TransportUnit - Something like a box, toad, bin or palette that has to be
 * moved.
 * <p>
 * Used as a container to transport items and <code>LoadUnit</code>s. It can
 * be moved between {@link Location}s.
 * </p>
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Entity
@Table(name = "COR_TRANSPORT_UNIT", uniqueConstraints = @UniqueConstraint(columnNames = { "BARCODE" }))
@NamedQueries( {
        @NamedQuery(name = "TransportUnit.findAll", query = "select tu from TransportUnit tu"),
        @NamedQuery(name = "TransportUnit.findByBarcode", query = "select tu from TransportUnit tu where tu.barcode = ?1") })
public class TransportUnit extends AbstractEntity implements Serializable {

    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 4799247366681079321L;

    /**
     * Unique technical key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    /**
     * Unique natural key.
     */
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BARCODE", unique = true)
    private Barcode barcode;

    /**
     * Indicates whether the {@link TransportUnit} is empty or not.
     */
    @Column(name = "EMPTY")
    private Boolean empty;

    /**
     * Date when the {@link TransportUnit} has been created.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATION_DATE")
    private Date creationDate;

    /**
     * Date when this {@link TransportUnit} moved to the actual {@link Location} .
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ACTUAL_LOCATION_DATE")
    private Date actualLocationDate;

    /**
     * Date of last inventory check.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "INVENTORY_DATE")
    private Date inventoryDate;

    /**
     * Weight of this {@link TransportUnit}.
     */
    @Column(name = "WEIGHT")
    private BigDecimal weight = new BigDecimal(0);

    /**
     * State of this {@link TransportUnit}.
     */
    @Column(name = "STATE")
    @Enumerated(EnumType.STRING)
    private TransportUnitState state = TransportUnitState.AVAILABLE;

    /**
     * Version field.
     */
    @Version
    @Column(name = "C_VERSION")
    private long version;

    /* ------------------- collection mapping ------------------- */
    /**
     * The actual {@link Location} of the {@link TransportUnit}.
     */
    @ManyToOne
    @JoinColumn(name = "ACTUAL_LOCATION", nullable = false)
    private Location actualLocation;

    /**
     * The target {@link Location} of the {@link TransportUnit}.<br>
     * This property will be set when a <code>TransportOrder</code> is
     * started.
     */
    @ManyToOne
    @JoinColumn(name = "TARGET_LOCATION")
    private Location targetLocation;

    /**
     * The {@link TransportUnitType} of this {@link TransportUnit}.
     */
    @ManyToOne
    @JoinColumn(name = "TRANSPORT_UNIT_TYPE", nullable = false)
    private TransportUnitType transportUnitType;

    /**
     * Owning {@link TransportUnit}.
     */
    @ManyToOne
    @JoinColumn(name = "PARENT")
    private TransportUnit parent;

    /**
     * The <code>User</code> who performed the last inventory action on this
     * {@link TransportUnit}.
     */
    @ManyToOne
    @JoinColumn(name = "INVENTORY_USER")
    private User inventoryUser;

    /**
     * A Set of all child {@link TransportUnit}s, ordered by id.
     */
    @OneToMany(mappedBy = "parent", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @OrderBy("id DESC")
    private Set<TransportUnit> children = new HashSet<TransportUnit>();

    /**
     * A Map of errors occurred on this {@link TransportUnit}.
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "COR_TRANSPORT_UNIT_ERROR", joinColumns = @JoinColumn(name = "TRANSPORT_UNIT_ID"), inverseJoinColumns = @JoinColumn(name = "ERROR_ID"))
    private Map<Date, UnitError> errors = new HashMap<Date, UnitError>();

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    @SuppressWarnings("unused")
    private TransportUnit() {}

    /**
     * Create a new {@link TransportUnit} with a unique unitId. The unitId is
     * used to create a {@link Barcode}.
     * 
     * @param unitId
     *            The unique identifier of the {@link TransportUnit} as String
     */
    public TransportUnit(String unitId) {
        this.creationDate = new Date();
        this.barcode = new Barcode(unitId);
    }

    /**
     * Create a new {@link TransportUnit} with a unique {@link Barcode}.
     * 
     * @param barcode
     *            The unique identifier of this {@link TransportUnit} is the
     *            {@link Barcode}
     */
    public TransportUnit(Barcode barcode) {
        this.creationDate = new Date();
        this.barcode = barcode;
    }

    /**
     * Return the unique technical key.
     * 
     * @return id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Checks if the instance is transient.
     * 
     * @return - true: Entity is not present on the persistent storage.<br> -
     *         false : Entity already exists on the persistence storage
     */
    public boolean isNew() {
        return (this.id == null);
    }

    /**
     * Get the actual {@link Location} of this {@link TransportUnit}.
     * 
     * @return The {@link Location} where this {@link TransportUnit} is
     *         currently placed on
     */
    public Location getActualLocation() {
        return actualLocation;
    }

    /**
     * Set this {@link TransportUnit} to the actual {@link Location}.
     * 
     * @param actualLocation
     *            The {@link Location} where this {@link TransportUnit} shall be
     *            moved to
     */
    public void setActualLocation(Location actualLocation) {
        this.actualLocation = actualLocation;
        this.actualLocationDate = new Date();
    }

    /**
     * Get the target {@link Location} of this {@link TransportUnit}. This
     * property is not <tt>NULL</tt> when an active <tt>TransportOrder</tt>
     * exists.
     * 
     * @return Location.
     */
    public Location getTargetLocation() {
        return this.targetLocation;
    }

    /**
     * Set the target {@link Location} of this {@link TransportUnit}. Shall
     * only be set when an active <tt>TransportOder</tt> exist.
     * 
     * @param targetLocation
     *            The target {@link Location} where this {@link TransportUnit}
     *            shall be transported to
     */
    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }

    /**
     * Indicates whether the {@link TransportUnit} is empty or not.
     * 
     * @return true if empty, otherwise false
     */
    public Boolean isEmpty() {
        return this.empty;
    }

    /**
     * Sets this {@link TransportUnit} to be empty.
     * 
     * @param empty
     *            true to mark the {@link TransportUnit} as empty
     */
    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    /**
     * Returns the <code>User</code> who did the last inventory action on this
     * {@link TransportUnit}.
     * 
     * @return The user who did the last inventory check
     */
    public User getInventoryUser() {
        return this.inventoryUser;
    }

    /**
     * Set the <code>User</code> who did the last inventory action on this
     * {@link TransportUnit}.
     * 
     * @param inventoryUser
     *            The {@link User} who did the last inventory check
     */
    public void setInventoryUser(User inventoryUser) {
        this.inventoryUser = inventoryUser;
    }

    /**
     * Number of {@link TransportUnit}s belonging to this {@link TransportUnit}.
     * 
     * @return The number of all {@link TransportUnit}s belonging to this one
     */
    public int getNoTransportUnits() {
        return this.children.size();
    }

    /**
     * Returns the date when the {@link TransportUnit} was created.
     * 
     * @return The date when this {@link TransportUnit} was created
     */
    public Date getCreationDate() {
        return this.creationDate;
    }

    /**
     * Returns the date when this {@link TransportUnit} moved to the
     * actualLocation.
     * 
     * @return The timestamp when this {@link TransportUnit} moved the last time
     */
    public Date getActualLocationDate() {
        return this.actualLocationDate;
    }

    /**
     * Returns the timestamp of the last inventory check of this
     * {@link TransportUnit}.
     * 
     * @return The timestamp of the last inventory check of this
     *         {@link TransportUnit}.
     */
    public Date getInventoryDate() {
        return this.inventoryDate;
    }

    /**
     * Set the timestamp of the last inventory action of this
     * {@link TransportUnit}.
     * 
     * @param inventoryDate
     *            The timestamp of the last inventory check
     */
    public void setInventoryDate(Date inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    /**
     * Returns the current weight of this {@link TransportUnit}.
     * 
     * @return The current weight of this {@link TransportUnit}
     */
    public BigDecimal getWeight() {
        return this.weight;
    }

    /**
     * Sets the current weight of this {@link TransportUnit}.
     * 
     * @param weight
     *            The current weight of this {@link TransportUnit}
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * Get all errors that occurred on this {@link TransportUnit}.
     * 
     * @return A Map of all occurred {@link UnitError}s on this
     *         {@link TransportUnit}
     */
    public Map<Date, UnitError> getErrors() {
        return Collections.unmodifiableMap(errors);
    }

    /**
     * Add an error for this {@link TransportUnit}.
     * 
     * @param error
     *            An {@link UnitError} to add
     * @return The key, or null in case the {@link UnitError} wasn't put into
     *         the Map
     */
    public UnitError addError(UnitError error) {
        if (error == null) {
            throw new IllegalArgumentException("Error may not be null!");
        }
        return errors.put(new Date(), error);
    }

    /**
     * Get the state of this {@link TransportUnit}.
     * 
     * @return The current state of this {@link TransportUnit}
     */
    public TransportUnitState getState() {
        return this.state;
    }

    /**
     * Set the state of this {@link TransportUnit}.
     * 
     * @param state
     *            The state to set on this {@link TransportUnit}
     */
    public void setState(TransportUnitState state) {
        this.state = state;
    }

    /**
     * Get the {@link TransportUnitType} of this {@link TransportUnit}.
     * 
     * @return The {@link TransportUnitType} this {@link TransportUnit} belongs
     *         to
     */
    public TransportUnitType getTransportUnitType() {
        return this.transportUnitType;
    }

    /**
     * Set the {@link TransportUnitType} of this {@link TransportUnit}.
     * 
     * @param transportUnitType
     *            The type to which this {@link TransportUnit} belongs to
     */
    public void setTransportUnitType(TransportUnitType transportUnitType) {
        this.transportUnitType = transportUnitType;
    }

    /**
     * Return the {@link Barcode} of the {@link TransportUnit}.
     * 
     * @return Barcode
     */
    public Barcode getBarcode() {
        return barcode;
    }

    /**
     * Set the {@link Barcode} of the {@link TransportUnit}.
     * 
     * @param barcode
     *            The {@link Barcode} to set for this {@link TransportUnit}
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
     * Get all child {@link TransportUnit}s.
     * 
     * @return the transportUnits.
     */
    public Set<TransportUnit> getChildren() {
        return Collections.unmodifiableSet(children);
    }

    /**
     * Add a {@link TransportUnit} to children.
     * 
     * @param transportUnit
     *            The {@link TransportUnit} to add to the list of children
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
     * Remove a {@link TransportUnit} from the collection of children.
     * 
     * @param transportUnit
     *            The {@link TransportUnit} to be removed from the list of
     *            children
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
     * JPA optimistic locking.
     * 
     * @return The version field.
     */
    public long getVersion() {
        return this.version;
    }

    /**
     * Return the {@link Barcode} as String.
     * 
     * @see java.lang.Object#toString()
     * @return String
     */
    @Override
    public String toString() {
        return this.barcode.toString();
    }
}
