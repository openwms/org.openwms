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

/**
 * An I18n entity stores multiple translations assigned to an unique key.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Entity
@Table(name = "COR_I18N", uniqueConstraints = @UniqueConstraint(columnNames = { "KEY", "MODULE" }))
@NamedQueries({
        @NamedQuery(name = I18n.NQ_FIND_ALL, query = "select i from I18n i order by i.module, i.key"),
        @NamedQuery(name = I18n.NQ_FIND_BY_UNIQUE_QUERY, query = "select i from I18n i where i.key = :key and i.module = :module") })
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
    @Column(name = "KEY", nullable = false)
    private String key;
    /**
     * The name of the owning <code>Module</code> where this translation set
     * belongs to. Default is {@value} .
     */
    @Column(name = "MODULE")
    private String module = "CORE";
    @Column(name = "C_VERSION")
    /**
     * Version field.
     */
    private Long version;
    /**
     * The translation set of this entity.
     */
    @Embedded
    private I18nSet lang;
    @Transient
    private String cKey;

    /**
     * Query to find all <code>I18n</code> language entities.
     */
    public static final String NQ_FIND_ALL = "I18n.findAll";

    /**
     * Query to find <strong>one</strong> <code>Role</code> by its natural key.
     * <li>Query parameter name <strong>key</strong> : The key of the
     * <code>I18n</code> to search for</li> <li>Query parameter name
     * <strong>module</strong> : The module where the <code>I18n</code> entity
     * belongs to</li>
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
     * @param key
     *            The key to access this translation
     * @param module
     *            Defines a group where this entity belongs to
     * @param lang
     *            A set of languages
     */
    public I18n(String key, String module, I18nSet lang) {
        super();
        this.key = key;
        this.module = module;
        this.lang = lang;
    }

    /**
     * Create a new I18n.
     * 
     * @param key
     *            The key to access this translation
     * @param module
     *            Defines a group where this entity belongs to
     */
    public I18n(String key, String module) {
        super();
        this.key = key;
        this.module = module;
    }

    /**
     * After loading the entity, combine the <code>module</code> field and the
     * <code>key</code> field. Store the concatenated String in a transient
     * field <code>cKey</code>. This field acts as a combined identifier.
     */
    @PostLoad
    protected void onLoad() {
        this.cKey = this.module + this.key;
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
     * Get the module.
     * 
     * @return the module.
     */
    public String getModule() {
        return module;
    }

    /**
     * Get the language set.
     * 
     * @return the language set.
     */
    public I18nSet getLang() {
        return lang;
    }

}
