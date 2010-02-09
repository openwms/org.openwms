/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * A TypeStackingRule.<br>
 * Defines what <code>TransportUnitType</code> may be placed on the owning
 * <code>TransportUnitType</code>.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 */
@Entity
@Table(name = "TYPE_STACKING_RULE", uniqueConstraints = @UniqueConstraint(columnNames = { "TRANSPORT_UNIT_TYPE",
        "NO_TRANSPORT_UNITS", "ALLOWED_TRANSPORT_UNIT_TYPE" }))
public class TypeStackingRule implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Primary key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    /**
     * Parent <code>TransportUnitType</code>.
     */
    @ManyToOne
    @JoinColumn(name = "TRANSPORT_UNIT_TYPE")
    private TransportUnitType transportUnitType;

    /**
     * Number of <code>TransportUnitType</code>s that may be placed on the
     * owning <code>TransportUnitType</code>.
     */
    @Column(name = "NO_TRANSPORT_UNITS", nullable = false)
    private short noTransportUnits;

    /**
     * The allowed <code>TransportUnitType</code> that may be placed on the
     * owning <code>TransportUnitType</code>.
     */
    @ManyToOne
    @JoinColumn(name = "ALLOWED_TRANSPORT_UNIT_TYPE", nullable = false)
    private TransportUnitType allowedTransportUnitType;

    /* ----------------------------- methods ------------------- */
    /**
     * Create a new <code>TypeStackingRule</code>.
     */
    @SuppressWarnings("unused")
    private TypeStackingRule() {}

    /**
     * Create a new <code>TypeStackingRule</code>. Define how many
     * <code>TransportUnit</code>s of the allowedTransportUnitType may stacked
     * on this <code>TransportUnitType</code>.
     * 
     * @param noTransportUnits
     * @param allowedTransportUnitType
     */
    public TypeStackingRule(short noTransportUnits, TransportUnitType allowedTransportUnitType) {
        this.noTransportUnits = noTransportUnits;
        this.allowedTransportUnitType = allowedTransportUnitType;
    }

    /**
     * Get the Primary Key.
     * 
     * @return the id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the transportUnitType.
     * 
     * @return the transportUnitType.
     */
    public TransportUnitType getTransportUnitType() {
        return transportUnitType;
    }

    /**
     * Returns the number of <code>TransportUnitType</code>s that may be placed
     * on the owning <code>TransportUnitType</code>.
     * 
     * @return
     */
    public short getNoTransportUnits() {
        return this.noTransportUnits;
    }

    /**
     * Returns the allowed <code>TransportUnitType</code> that may be placed on
     * the owning <code>TransportUnitType</code>.
     * 
     * @return
     */
    public TransportUnitType getAllowedTransportUnitType() {
        return this.allowedTransportUnitType;
    }
}
