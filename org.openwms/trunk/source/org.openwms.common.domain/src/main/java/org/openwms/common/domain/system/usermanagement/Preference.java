/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain.system.usermanagement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Entity to persist preferences. A <code>Preference</code> could be an user-,
 * role- or system preference.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 */
/*
 * TODO: + Assing key, value as complementary key instead of using ID + Replace
 * BigDecimal with float
 */
@Entity
@Table(name = "T_PREFERENCE")
public class Preference implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unique identifier of the <code>Preference</code>.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    @Column(name = "C_KEY")
    private String key;

    /**
     * The type of the value.
     */
    @Column(name = "TYPE")
    private String type;

    /**
     * The general value of the <code>Preference</code>.
     */
    @Column(name = "VALUE")
    private String value;

    /**
     * A float value representation of the value.
     */
    @Column(name = "FLOAT_VALUE")
    private BigDecimal floatValue;

    /**
     * Description text of the <code>Preference</code>.
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * A minimum for the value.
     */
    @Column(name = "MINIMUM")
    private int minimum;

    /**
     * A maximum for the value.
     */
    @Column(name = "MAXIMUM")
    private int maximum;

    /**
     * Version field
     */
    @Version
    private long version;

    /**
     * A list of <code>Role</code>s assigned to the <code>Preference</code>.
     */
    @ManyToMany(mappedBy = "preferences")
    private Set<Role> roles = new HashSet<Role>();

    /* ----------------------------- methods ------------------- */
    public Preference() {}

    public Long getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.value = key;
    }

    public BigDecimal getFloatValue() {
        return this.floatValue;
    }

    public void setFloatValue(BigDecimal floatValue) {
        this.floatValue = floatValue;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinimum() {
        return this.minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public int getMaximum() {
        return this.maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public boolean addRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role may not be null!");
        }
        return roles.add(role);
    }

    public boolean removeRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role may not be null!");
        }
        return roles.remove(role);
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * JPA optimistic locking: Returns version field.
     * 
     * @return
     */
    public long getVersion() {
        return version;
    }
}
