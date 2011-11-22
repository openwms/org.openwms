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
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.domain.DomainObject;
import org.openwms.core.domain.values.I18nSet;
import org.openwms.core.util.validation.AssertUtils;

/**
 * An I18n entity stores multiple translations assigned to an unique key.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Entity
@Table(name = "COR_I18N", uniqueConstraints = @UniqueConstraint(columnNames = { "C_KEY", "C_MODULE_NAME" }))
@NamedQueries({
        @NamedQuery(name = I18n.NQ_FIND_ALL, query = "select i from I18n i order by i.moduleName, i.key"),
        @NamedQuery(name = I18n.NQ_FIND_BY_UNIQUE_QUERY, query = "select i from I18n i where i.key = :key and i.moduleName = :moduleName") })
public class I18n extends AbstractEntity implements DomainObject<Long> {

    private static final long serialVersionUID = -9176131734403683401L;

    /**
     * The unique technical key.
     */
    @Column(name = "ID")
    @Id
    @GeneratedValue
    private Long id;
    /**
     * The natural key is used as references in the application (not nullable).
     */
    @Column(name = "C_KEY", nullable = false)
    private String key;
    /**
     * The name of the owning <code>Module</code> to which this translation set
     * belongs to. Default is {@value} .
     */
    @Column(name = "C_MODULE_NAME")
    private String moduleName = "CORE";
    /**
     * Version field.
     */
    @Column(name = "C_VERSION")
    private Long version;
    /**
     * The translation set of this entity.
     */
    @Embedded
    private I18nSet lang;
    /**
     * The cKey is a transient field that is constructed after the entity is
     * loaded from the persistent storage. Usually this field is accessed from
     * the client application to have an unique identifier - a combination of
     * the owning <code>moduleName</code> and the <code>key</code>.
     */
    @Transient
    private String cKey;

    /**
     * Query to find all <code>I18n</code> entities.
     */
    public static final String NQ_FIND_ALL = "I18n.findAll";

    /**
     * Query to find <strong>one</strong> <code>I18n</code> by
     * <code>moduleName</code> and <code>key</code>.<li>Query parameter name
     * <strong>moduleName</strong> : The name of the <code>Module</code> where
     * the <code>I18n</code> entity belongs to</li><li>Query parameter name
     * <strong>key</strong> : The key of the <code>I18n</code> to search for</li>
     * 
     */
    public static final String NQ_FIND_BY_UNIQUE_QUERY = "I18n.findByKeyModule";

    /**
     * Create a new I18n.
     */
    public I18n() {
        super();
    }

    /**
     * Create a new I18n.
     * 
     * @param moduleName
     *            The name of the <code>Module</code> where this entity belongs
     *            to
     * @param key
     *            The key to access this translation
     * @param lang
     *            A set of languages
     * @throws IllegalArgumentException
     *             when the <code>moduleName</code> or the <code>key</code> is
     *             <code>null</code> or empty
     */
    public I18n(String moduleName, String key, I18nSet lang) {
        super();
        AssertUtils.isNotEmpty(moduleName, "Not allowed to create an I18n instance with an empty moduleName");
        AssertUtils.isNotEmpty(key, "Not allowed to create an I18n instance with an empty key");
        this.moduleName = moduleName;
        this.key = key;
        this.lang = lang;
    }

    /**
     * Create a new I18n.
     * 
     * @param key
     *            The key to access this translation
     * @param lang
     *            A set of languages
     * @throws IllegalArgumentException
     *             when the <code>key</code> is <code>null</code> or empty
     */
    public I18n(String key, I18nSet lang) {
        super();
        AssertUtils.isNotEmpty(key, "Not allowed to create an I18n instance with an empty key");
        this.key = key;
        this.lang = lang;
    }

    /**
     * After loading the entity, combine the <code>moduleName</code> field and
     * the <code>key</code> field. Store the concatenated String in a transient
     * field <code>cKey</code>.
     */
    @PostLoad
    protected void onLoad() {
        this.cKey = this.moduleName + this.key;
    }

    /**
     * Get the cKey.
     * 
     * @return the cKey.
     */
    public String getCKey() {
        return cKey;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.domain.DomainObject#isNew()
     */
    @Override
    public boolean isNew() {
        return this.id == null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.domain.DomainObject#getVersion()
     */
    @Override
    public long getVersion() {
        return this.version;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.domain.DomainObject#getId()
     */
    @Override
    public Long getId() {
        return this.id;
    }

    /**
     * Get the key.
     * 
     * @return the key.
     */
    public String getKey() {
        return key;
    }

    /**
     * Get the moduleName.
     * 
     * @return the moduleName.
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * Get the language set.
     * 
     * @return the language set.
     */
    public I18nSet getLang() {
        return lang;
    }

    /**
     * Use <code>key</code> and <code>moduleName</code> for calculation.
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 42;
        result = prime * result + ((cKey == null) ? 0 : cKey.hashCode());
        result = prime * result + ((moduleName == null) ? 0 : moduleName.hashCode());
        return result;
    }

    /**
     * Use <code>key</code> and <code>moduleName</code> for comparison.
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        I18n other = (I18n) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        if (moduleName == null) {
            if (other.moduleName != null) {
                return false;
            }
        } else if (!moduleName.equals(other.moduleName)) {
            return false;
        }
        return true;
    }
}