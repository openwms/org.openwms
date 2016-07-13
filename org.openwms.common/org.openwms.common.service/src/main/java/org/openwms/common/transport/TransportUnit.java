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
package org.openwms.common.transport;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.ameba.integration.jpa.BaseEntity;
import org.openwms.common.location.Location;
import org.openwms.common.units.Weight;
import org.openwms.core.values.CoreTypeDefinitions;
import org.springframework.util.Assert;

/**
 * A TransportUnit is an item like a box, a toad, a bin or a palette that is moved around within a warehouse and can carry goods. <p> Used
 * as container to transport items like {@code LoadUnit}s. It can be moved between {@code Location}s. </p>
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @GlossaryTerm
 * @since 0.1
 */
@Entity
@Table(name = "COM_TRANSPORT_UNIT", uniqueConstraints = @UniqueConstraint(columnNames = {"C_BARCODE"}))
public class TransportUnit extends BaseEntity {

    /** Unique natural key. */
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "C_BARCODE", length = Barcode.BARCODE_LENGTH))
    @OrderBy
    private Barcode barcode;

    /** Indicates whether the {@code TransportUnit} is empty or not (nullable). */
    @Column(name = "C_EMPTY")
    private Boolean empty;

    /** Date when the {@code TransportUnit} has been moved to the current {@link Location}. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_ACTUAL_LOCATION_DATE")
    private Date actualLocationDate;

    /** Date of last inventory check. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_INVENTORY_DATE")
    private Date inventoryDate;

    /** Weight of the {@code TransportUnit}. */
    @Embedded
    @AttributeOverride(name = "quantity", column = @Column(name = "C_WEIGHT", length = CoreTypeDefinitions.QUANTITY_LENGTH))
    private Weight weight = new Weight("0");

    /** State of the {@code TransportUnit}. */
    @Column(name = "C_STATE")
    @Enumerated(EnumType.STRING)
    private TransportUnitState state = TransportUnitState.AVAILABLE;

    /** The current {@link Location} of the {@code TransportUnit}. */
    @ManyToOne
    @JoinColumn(name = "C_ACTUAL_LOCATION", nullable = false)
    private Location actualLocation;

    /** The target {@link Location} of the {@code TransportUnit}.<br /> This property will be set when a {@code TransportOrder} is started. */
    @ManyToOne
    @JoinColumn(name = "C_TARGET_LOCATION")
    private Location targetLocation;

    /** The {@link TransportUnitType} of the {@code TransportUnit}. */
    @ManyToOne
    @JoinColumn(name = "C_TRANSPORT_UNIT_TYPE", nullable = false)
    private TransportUnitType transportUnitType;

    /** Owning {@code TransportUnit}. */
    @ManyToOne
    @JoinColumn(name = "C_PARENT")
    private TransportUnit parent;

    /** The {@code User} who performed the last inventory action on the {@code TransportUnit}. */
    @Column(name = "C_INVENTORY_USER")
    private String inventoryUser;

    /** A set of all child {@code TransportUnit}s, ordered by id. */
    @OneToMany(mappedBy = "parent", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @OrderBy("id DESC")
    private Set<TransportUnit> children = new HashSet<>();

    /** A Map of errors occurred on the {@code TransportUnit}. */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "COM_TRANSPORT_UNIT_ERROR", joinColumns = @JoinColumn(name = "C_TRANSPORT_UNIT_ID"), inverseJoinColumns = @JoinColumn(name = "C_ERROR_ID"))
    // TODO [openwms]: 12/07/16 refactor into a JPA2 map withou using Date as key!
    private Map<Date, UnitError> errors = new HashMap<>();

    /*~ ----------------------------- constructors ------------------- */

    /** Dear JPA... */
    protected TransportUnit() {
    }

    /**
     * Create a new {@code TransportUnit} with an unique id. The id is used to create a {@link Barcode}.
     *
     * @param unitId The unique identifier of the {@code TransportUnit}
     */
    TransportUnit(String unitId) {
        Assert.hasText(unitId);
        this.barcode = new Barcode(unitId);
    }

    /**
     * Create a new {@code TransportUnit} with an unique {@link Barcode}.
     *
     * @param barcode The unique identifier of this {@code TransportUnit} is the {@link Barcode}
     */
    TransportUnit(Barcode barcode) {
        this.barcode = new Barcode(barcode.adjustBarcode(barcode.getValue()));
    }

    /*~ ----------------------------- methods ------------------- */

    /**
     * Get the actual {@link Location} of the {@code TransportUnit}.
     *
     * @return The {@link Location} where the {@code TransportUnit} is placed on
     */
    public Location getActualLocation() {
        return actualLocation;
    }

    /**
     * Put the {@code TransportUnit} on a {@link Location}.
     *
     * @param actualLocation The new {@link Location} of the {@code TransportUnit}
     */
    public void setActualLocation(Location actualLocation) {
        this.actualLocation = actualLocation;
        this.actualLocationDate = new Date();
    }

    /**
     * Get the target {@link Location} of the {@code TransportUnit}. This property can not be {@literal null} when an active {@code
     * TransportOrder} exists.
     *
     * @return The target location
     */
    public Location getTargetLocation() {
        return this.targetLocation;
    }

    /**
     * Set the target {@link Location} of the {@code TransportUnit}. Shall only be set in combination with an active {@code
     * TransportOrder}.
     *
     * @param targetLocation The target {@link Location} where this {@code TransportUnit} shall be transported to
     */
    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }

    /**
     * Indicates whether the {@code TransportUnit} is empty or not.
     *
     * @return {@literal true} if empty, {@literal false} if not empty, {@literal null} when not defined
     */
    public Boolean isEmpty() {
        return this.empty;
    }

    /**
     * Marks the {@code TransportUnit} to be empty.
     *
     * @param empty {@literal true} to mark the {@code TransportUnit} as empty, {@literal false} to mark it as not empty and {@literal null}
     * for no definition
     */
    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    /**
     * Returns the username of the User who performed the last inventory action on the {@code TransportUnit}.
     *
     * @return The username who did the last inventory check
     */
    public String getInventoryUser() {
        return this.inventoryUser;
    }

    /**
     * Set the username who performed the last inventory action on the {@code TransportUnit}.
     *
     * @param inventoryUser The username who did the last inventory check
     */
    public void setInventoryUser(String inventoryUser) {
        this.inventoryUser = inventoryUser;
    }

    /**
     * Number of {@code TransportUnit}s belonging to the {@code TransportUnit}.
     *
     * @return The number of all {@code TransportUnit}s belonging to this one
     */
    public int getNoTransportUnits() {
        return this.children.size();
    }

    /**
     * Returns the date when the {@code TransportUnit} moved to the actualLocation.
     *
     * @return The timestamp when the {@code TransportUnit} moved the last time
     */
    public Date getActualLocationDate() {
        return new Date(this.actualLocationDate.getTime());
    }

    /**
     * Returns the timestamp of the last inventory check of the {@code TransportUnit}.
     *
     * @return The timestamp of the last inventory check of the {@code TransportUnit}.
     */
    public Date getInventoryDate() {
        return new Date(this.inventoryDate.getTime());
    }

    /**
     * Set the timestamp of the last inventory action of the {@code TransportUnit}.
     *
     * @param inventoryDate The timestamp of the last inventory check
     */
    public void setInventoryDate(Date inventoryDate) {
        this.inventoryDate = new Date(inventoryDate.getTime());
    }

    /**
     * Returns the current weight of the {@code TransportUnit}.
     *
     * @return The current weight of the {@code TransportUnit}
     */
    public Weight getWeight() {
        return this.weight;
    }

    /**
     * Sets the current weight of the {@code TransportUnit}.
     *
     * @param weight The current weight of the {@code TransportUnit}
     */
    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    /**
     * Get all errors that have occurred on the {@code TransportUnit}.
     *
     * @return A Map of all occurred {@link UnitError}s on the {@code TransportUnit}
     */
    public Map<Date, UnitError> getErrors() {
        return Collections.unmodifiableMap(errors);
    }

    /**
     * Add an error to the {@code TransportUnit}.
     *
     * @param error An {@link UnitError} to be added
     * @return The key.
     * @throws IllegalArgumentException when something went wrong
     */
    public UnitError addError(UnitError error) {
        Assert.notNull(error, "Error to add may not be null, this: " + this);
        return errors.put(new Date(), error);
    }

    /**
     * Return the state of the {@code TransportUnit}.
     *
     * @return The current state of the {@code TransportUnit}
     */
    public TransportUnitState getState() {
        return this.state;
    }

    /**
     * Set the state of the {@code TransportUnit}.
     *
     * @param state The state to set on the {@code TransportUnit}
     */
    public void setState(TransportUnitState state) {
        this.state = state;
    }

    /**
     * Return the {@link TransportUnitType} of the {@code TransportUnit}.
     *
     * @return The {@link TransportUnitType} the {@code TransportUnit} belongs to
     */
    public TransportUnitType getTransportUnitType() {
        return this.transportUnitType;
    }

    /**
     * Set the {@link TransportUnitType} of the {@code TransportUnit}.
     *
     * @param transportUnitType The type of the {@code TransportUnit}
     */
    public void setTransportUnitType(TransportUnitType transportUnitType) {
        this.transportUnitType = transportUnitType;
    }

    /**
     * Return the {@link Barcode} of the {@code TransportUnit}.
     *
     * @return The current {@link Barcode}
     */
    public Barcode getBarcode() {
        return barcode;
    }

    /**
     * Set the {@link Barcode} of the {@code TransportUnit}.
     *
     * @param barcode The {@link Barcode} to be set on the {@code TransportUnit}
     */
    public void setBarcode(Barcode barcode) {
        this.barcode = barcode;
    }

    /**
     * Returns the parent {@code TransportUnit}.
     *
     * @return the parent.
     */
    public TransportUnit getParent() {
        return parent;
    }

    /**
     * Set a parent {@code TransportUnit}.
     *
     * @param parent The parent to set.
     */
    public void setParent(TransportUnit parent) {
        this.parent = parent;
    }

    /**
     * Get all child {@code TransportUnit}s.
     *
     * @return the transportUnits.
     */
    public Set<TransportUnit> getChildren() {
        return Collections.unmodifiableSet(children);
    }

    /**
     * Add a {@code TransportUnit} to the children.
     *
     * @param transportUnit The {@code TransportUnit} to be added to the list of children
     * @throws IllegalArgumentException when transportUnit is {@literal null}
     */
    public void addChild(TransportUnit transportUnit) {
        Assert.notNull(transportUnit, "Child to add may not be null, this: " + this);
        if (transportUnit.hasParent()) {
            if (transportUnit.getParent().equals(this)) {

                // if this instance is already the parent, we just return
                return;
            }

            // disconnect post from it's current relationship
            transportUnit.getParent().removeChild(transportUnit);
        }

        // make this instance the new parent
        transportUnit.setParent(this);
        children.add(transportUnit);
    }

    boolean hasParent() {
        return parent != null;
    }

    /**
     * Remove a {@code TransportUnit} from the list of children.
     *
     * @param transportUnit The {@code TransportUnit} to be removed from the list of children
     * @throws IllegalArgumentException when transportUnit is {@literal null} or any other failure occurs
     */
    public void removeChild(TransportUnit transportUnit) {
        Assert.notNull(transportUnit, "Child to remove may not be null, this: " + this);
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
     * @param actualLocationDate The actualLocationDate to set.
     */
    public void setActualLocationDate(Date actualLocationDate) {
        this.actualLocationDate = new Date(actualLocationDate.getTime());
    }

    /**
     * Return the {@link Barcode} as String.
     *
     * @return String
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return barcode.toString();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Uses barcode for calculation.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Uses barcode for comparison.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof TransportUnit)) {
            return false;
        }
        TransportUnit other = (TransportUnit) obj;
        if (barcode == null) {
            if (other.barcode != null) {
                return false;
            }
        } else if (!barcode.equals(other.getBarcode())) {
            return false;
        }
        return true;
    }
}