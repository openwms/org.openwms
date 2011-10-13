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
package org.openwms.core.domain.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.domain.DomainObject;

/**
 * A AbstractPreference is a superclass for all other preferences within the
 * application.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@XmlType(name = "abstractPreference", propOrder = { "description" }, namespace = "http://www.openwms.org/schema/preferences")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "COR_PREFERENCE")
@NamedQueries({ @NamedQuery(name = AbstractPreference.NQ_FIND_ALL, query = "select p from AbstractPreference p") })
public abstract class AbstractPreference extends AbstractEntity implements DomainObject<Long> {

    private static final long serialVersionUID = 4396571221433949201L;
    /**
     * Query to find all <code>AbstractPreference</code>s.
     */
    public static final String NQ_FIND_ALL = "AbstractPreference" + FIND_ALL;
    /**
     * /** Unique technical key.
     */
    @XmlTransient
    @Id
    @Column(name = "C_ID")
    @GeneratedValue
    private Long id;

    /**
     * The value of the <code>AbstractPreference</code>.
     */
    @XmlAttribute(name = "val")
    @Column(name = "C_VALUE")
    private String value;

    /**
     * Float representation of the value.
     */
    @XmlAttribute(name = "floatValue")
    @Column(name = "C_FLOAT_VALUE")
    private Float floatValue;

    /**
     * Description text of the <code>AbstractPreference</code>.
     */
    @XmlValue
    @Column(name = "C_DESCRIPTION")
    private String description;

    /**
     * Minimum value.
     */
    @XmlAttribute(name = "minimum")
    @Column(name = "C_MINIMUM")
    private int minimum = 0;

    /**
     * Maximum value.
     */
    @XmlAttribute(name = "maximum")
    @Column(name = "C_MAXIMUM")
    private int maximum = 0;

    /**
     * Flag to remember if the preference was originally imported from a file
     * and cannot be deleted.
     */
    @XmlTransient
    @Column(name = "C_FROM_FILE")
    private boolean fromFile = true;

    /**
     * Version field.
     */
    @XmlTransient
    @Version
    @Column(name = "C_VERSION")
    private long version;

    /* ----------------------------- constructors ------------------- */
    /**
     * Accessed by persistence provider.
     */
    protected AbstractPreference() {}

    /* ----------------------------- inherited ------------------- */
    /**
     * {@inheritDoc}
     */
    @Override
    public Long getId() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNew() {
        return this.id == null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getVersion() {
        return version;
    }

    /* ----------------------------- methods ------------------- */
    /**
     * Return the value of the <code>AbstractPreference</code>.
     * 
     * @return The value of the <code>AbstractPreference</code>
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Set the value of the <code>AbstractPreference</code>.
     * 
     * @param value
     *            The value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Get the <code>floatValue</code> of the <code>AbstractPreference</code>.
     * 
     * @return The <code>floatValue</code> of the
     *         <code>AbstractPreference</code>
     */
    public Float getFloatValue() {
        return this.floatValue;
    }

    /**
     * Set the <code>floatValue</code> of the <code>AbstractPreference</code>.
     * 
     * @param floatValue
     *            The <code>floatValue</code> to set
     */
    public void setFloatValue(Float floatValue) {
        this.floatValue = floatValue;
    }

    /**
     * Return the description of the <code>AbstractPreference</code>.
     * 
     * @return The description as String
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set a description for the <code>AbstractPreference</code>.
     * 
     * @param description
     *            The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return the possible minimum value of the <code>AbstractPreference</code>.
     * 
     * @return The possible minimum value
     */
    public int getMinimum() {
        return this.minimum;
    }

    /**
     * Set a possible minimum value for the <code>AbstractPreference</code>.
     * 
     * @param minimum
     *            The possible minimum value to set
     */
    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    /**
     * Return the possible maximum value of the <code>AbstractPreference</code>.
     * 
     * @return The possible maximum value
     */
    public int getMaximum() {
        return this.maximum;
    }

    /**
     * Set a possible maximum integer value for the
     * <code>AbstractPreference</code>.
     * 
     * @param maximum
     *            The possible maximum value to set
     */
    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    /**
     * Get the fromFile.
     * 
     * @return the fromFile.
     */
    public boolean isFromFile() {
        return fromFile;
    }

    /**
     * Return all fields as concatenated String.
     * 
     * @return fields as String
     */
    public String getPropertiesAsString() {
        ToStringBuilder.setDefaultStyle(ToStringStyle.SIMPLE_STYLE);
        return new ToStringBuilder(this).append(getFields()).append(getValue()).append(getDescription())
                .append(getFloatValue()).append(getMinimum()).append(getMaximum()).toString();
    }

    /**
     * Return all fields in an object array.
     * 
     * @return fields as array
     */
    protected abstract Object[] getFields();

    /**
     * Return the particular type the preference belongs to.
     * 
     * @return The type of the preference
     */
    public abstract PropertyScope getType();

    /**
     * Return a {@link PreferenceKey} as a key representation of this
     * preference.
     * 
     * @return A {@link PreferenceKey}
     */
    public abstract PreferenceKey getPrefKey();

}
