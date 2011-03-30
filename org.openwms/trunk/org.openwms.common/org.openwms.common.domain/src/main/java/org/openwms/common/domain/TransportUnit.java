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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import org.openwms.common.domain.values.Barcode;
import org.openwms.common.domain.values.TransportUnitState;
import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.domain.DomainObject;
import org.openwms.core.domain.system.usermanagement.User;

/**
 * A TransportUnit is something like a box, a toad, a bin or a palette that has
 * to be moved around.
 * <p>
 * Used as a container to transport items and <code>LoadUnit</code>s. It can be
 * moved between {@link Location}s.
 * </p>
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Entity
@Table(name = "COM_TRANSPORT_UNIT", uniqueConstraints = @UniqueConstraint(columnNames = { "BARCODE" }))
@NamedQueries({
        @NamedQuery(name = TransportUnit.NQ_FIND_ALL, query = "select tu from TransportUnit tu"),
        @NamedQuery(name = TransportUnit.NQ_FIND_BY_UNIQUE_QUERY, query = "select tu from TransportUnit tu where tu.barcode = ?1") })
public class TransportUnit extends AbstractEntity implements DomainObject<Long>, Serializable {

    private static final long serialVersionUID = 4799247366681079321L;

    /**
     * Name of the <code>NamedQuery</code> to find all
     * <code>TransportUnit</code> Entities.
     */
    public static final String NQ_FIND_ALL = "TransportUnit.findAll";

    /**
     * Query to find <strong>one</strong> <code>TransportUnit</code> by its
     * natural key.
     * <ul>
     * <li>Query parameter index <strong>1</strong> : The name of the
     * <code>TransportUnit</code> to search for.</li>
     * </ul>
     */
    public static final String NQ_FIND_BY_UNIQUE_QUERY = "TransportUnit.findByBarcode";

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
    @Column(name = "BARCODE")
    @OrderBy
    private Barcode barcode;

    /**
     * Indicates whether the <code>TransportUnit</code> is empty or not
     * (nullable).
     */
    @Column(name = "EMPTY")
    private Boolean empty;

    /**
     * Date when the <code>TransportUnit</code> has been created.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATION_DATE")
    private Date creationDate = new Date();

    /**
     * Date when the <code>TransportUnit</code> has been moved to the current
     * {@link Location}.
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
     * Weight of the <code>TransportUnit</code>.
     */
    @Column(name = "WEIGHT")
    private BigDecimal weight = new BigDecimal(0);

    /**
     * State of the <code>TransportUnit</code>. Default: {@value} .
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
     * The current {@link Location} of the <code>TransportUnit</code>.
     */
    @ManyToOne
    @JoinColumn(name = "ACTUAL_LOCATION", nullable = false)
    private Location actualLocation;

    /**
     * The target {@link Location} of the <code>TransportUnit</code>.<br>
     * This property will be set when a <code>TransportOrder</code> is started.
     */
    @ManyToOne
    @JoinColumn(name = "TARGET_LOCATION")
    private Location targetLocation;

    /**
     * The {@link TransportUnitType} of the <code>TransportUnit</code>.
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
     * The <code>User</code> who performed the last inventory action on the
     * <code>TransportUnit</code>.
     */
    @ManyToOne
    @JoinColumn(name = "INVENTORY_USER")
    private User inventoryUser;

    /**
     * A set of all child <code>TransportUnit</code>s, ordered by id.
     */
    @OneToMany(mappedBy = "parent", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @OrderBy("id DESC")
    private Set<TransportUnit> children = new HashSet<TransportUnit>();

    /**
     * A Map of errors occurred on the <code>TransportUnit</code>.
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "COM_TRANSPORT_UNIT_ERROR", joinColumns = @JoinColumn(name = "TRANSPORT_UNIT_ID"), inverseJoinColumns = @JoinColumn(name = "ERROR_ID"))
    private Map<Date, UnitError> errors = new HashMap<Date, UnitError>();

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    @SuppressWarnings("unused")
    private TransportUnit() {}

    /**
     * Create a new <code>TransportUnit</code> with an unique id. The id is used
     * to create a {@link Barcode}.
     * 
     * @param unitId
     *            The unique identifier of the <code>TransportUnit</code>
     */
    public TransportUnit(String unitId) {
        this.barcode = new Barcode(unitId);
    }

    /**
     * Create a new <code>TransportUnit</code> with an unique {@link Barcode}.
     * 
     * @param barcode
     *            The unique identifier of this <code>TransportUnit</code> is
     *            the {@link Barcode}
     */
    public TransportUnit(Barcode barcode) {
        this.barcode = barcode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNew() {
        return (this.id == null);
    }

    /**
     * Get the actual {@link Location} of the <code>TransportUnit</code>.
     * 
     * @return The {@link Location} where the <code>TransportUnit</code> is
     *         placed on
     */
    public Location getActualLocation() {
        return actualLocation;
    }

    /**
     * Put the <code>TransportUnit</code> on a {@link Location}.
     * 
     * @param actualLocation
     *            The new {@link Location} of the <code>TransportUnit</code>
     */
    public void setActualLocation(Location actualLocation) {
        this.actualLocation = actualLocation;
        this.actualLocationDate = new Date();
    }

    /**
     * Get the target {@link Location} of the <code>TransportUnit</code>. This
     * property can not be <code>null</code> when an active
     * <code>TransportOrder</code> exists.
     * 
     * @return The target location
     */
    public Location getTargetLocation() {
        return this.targetLocation;
    }

    /**
     * Set the target {@link Location} of the <code>TransportUnit</code>. Shall
     * only be set in combination with an active <code>TransportOder</code>.
     * 
     * @param targetLocation
     *            The target {@link Location} where this
     *            <code>TransportUnit</code> shall be transported to
     */
    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }

    /**
     * Indicates whether the <code>TransportUnit</code> is empty or not.
     * 
     * @return <code>true</code> if empty, <code>false</code> if not empty,
     *         <code>null</code> when not defined
     */
    public Boolean isEmpty() {
        return this.empty;
    }

    /**
     * Marks the <code>TransportUnit</code> to be empty.
     * 
     * @param empty
     *            <code>true</code> to mark the <code>TransportUnit</code> as
     *            empty, <code>false</code> to mark it as not empty and
     *            <code>null</code> for no definition
     */
    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    /**
     * Returns the {@link User} who performed the last inventory action on the
     * <code>TransportUnit</code>.
     * 
     * @return The {@link User} who did the last inventory check
     */
    public User getInventoryUser() {
        return this.inventoryUser;
    }

    /**
     * Set the {@link User}> who performed the last inventory action on the
     * <code>TransportUnit</code>.
     * 
     * @param inventoryUser
     *            The {@link User} who did the last inventory check
     */
    public void setInventoryUser(User inventoryUser) {
        this.inventoryUser = inventoryUser;
    }

    /**
     * Number of <code>TransportUnit</code>s belonging to the
     * <code>TransportUnit</code>.
     * 
     * @return The number of all <code>TransportUnit</code>s belonging to this
     *         one
     */
    public int getNoTransportUnits() {
        return this.children.size();
    }

    /**
     * Returns the creation date of the <code>TransportUnit</code>.
     * 
     * @return The creation date
     */
    public Date getCreationDate() {
        return this.creationDate;
    }

    /**
     * Returns the date when the <code>TransportUnit</code> moved to the
     * actualLocation.
     * 
     * @return The timestamp when the <code>TransportUnit</code> moved the last
     *         time
     */
    public Date getActualLocationDate() {
        return this.actualLocationDate;
    }

    /**
     * Returns the timestamp of the last inventory check of the
     * <code>TransportUnit</code>.
     * 
     * @return The timestamp of the last inventory check of the
     *         <code>TransportUnit</code>.
     */
    public Date getInventoryDate() {
        return this.inventoryDate;
    }

    /**
     * Set the timestamp of the last inventory action of the
     * <code>TransportUnit</code>.
     * 
     * @param inventoryDate
     *            The timestamp of the last inventory check
     */
    public void setInventoryDate(Date inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    /**
     * Returns the current weight of the <code>TransportUnit</code>.
     * 
     * @return The current weight of the <code>TransportUnit</code>
     */
    public BigDecimal getWeight() {
        return this.weight;
    }

    /**
     * Sets the current weight of the <code>TransportUnit</code>.
     * 
     * @param weight
     *            The current weight of the <code>TransportUnit</code>
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * Get all errors that have occurred on the <code>TransportUnit</code>.
     * 
     * @return A Map of all occurred {@link UnitError}s on the
     *         <code>TransportUnit</code>
     */
    public Map<Date, UnitError> getErrors() {
        return Collections.unmodifiableMap(errors);
    }

    /**
     * Add an error to the <code>TransportUnit</code>.
     * 
     * @param error
     *            An {@link UnitError} to be added
     * @return The key.
     * @throws IllegalArgumentException
     *             when something went wrong
     */
    public UnitError addError(UnitError error) {
        if (error == null) {
            throw new IllegalArgumentException("Error may not be null!");
        }
        return errors.put(new Date(), error);
    }

    /**
     * Return the state of the <code>TransportUnit</code>.
     * 
     * @return The current state of the <code>TransportUnit</code>
     */
    public TransportUnitState getState() {
        return this.state;
    }

    /**
     * Set the state of the <code>TransportUnit</code>.
     * 
     * @param state
     *            The state to set on the <code>TransportUnit</code>
     */
    public void setState(TransportUnitState state) {
        this.state = state;
    }

    /**
     * Return the {@link TransportUnitType} of the <code>TransportUnit</code>.
     * 
     * @return The {@link TransportUnitType} the <code>TransportUnit</code>
     *         belongs to
     */
    public TransportUnitType getTransportUnitType() {
        return this.transportUnitType;
    }

    /**
     * Set the {@link TransportUnitType} of the <code>TransportUnit</code>.
     * 
     * @param transportUnitType
     *            The type of the <code>TransportUnit</code>
     */
    public void setTransportUnitType(TransportUnitType transportUnitType) {
        this.transportUnitType = transportUnitType;
    }

    /**
     * Return the {@link Barcode} of the <code>TransportUnit</code>.
     * 
     * @return The current {@link Barcode}
     */
    public Barcode getBarcode() {
        return barcode;
    }

    /**
     * Set the {@link Barcode} of the <code>TransportUnit</code>.
     * 
     * @param barcode
     *            The {@link Barcode} to be set on the
     *            <code>TransportUnit</code>
     */
    public void setBarcode(Barcode barcode) {
        this.barcode = barcode;
    }

    /**
     * Returns the parent <code>TransportUnit</code>.
     * 
     * @return the parent.
     */
    public TransportUnit getParent() {
        return parent;
    }

    /**
     * Set a parent <code>TransportUnit</code>.
     * 
     * @param parent
     *            The parent to set.
     */
    public void setParent(TransportUnit parent) {
        this.parent = parent;
    }

    /**
     * Get all child <code>TransportUnit</code>s.
     * 
     * @return the transportUnits.
     */
    public Set<TransportUnit> getChildren() {
        return Collections.unmodifiableSet(children);
    }

    /**
     * Add a <code>TransportUnit</code> to the children.
     * 
     * @param transportUnit
     *            The <code>TransportUnit</code> to be added to the list of
     *            children
     * @throws IllegalArgumentException
     *             when transportUnit is <code>null</code>
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
     * Remove a <code>TransportUnit</code> from the list of children.
     * 
     * @param transportUnit
     *            The <code>TransportUnit</code> to be removed from the list of
     *            children
     * @throws IllegalArgumentException
     *             when transportUnit is <code>null</code> or any other failure
     *             occurs
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
     * {@inheritDoc}
     */
    @Override
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
