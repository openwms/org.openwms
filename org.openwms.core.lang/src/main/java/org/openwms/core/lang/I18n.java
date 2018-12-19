/*
 * Copyright 2018 Heiko Scherrer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openwms.core.lang;

import org.ameba.integration.jpa.BaseEntity;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;


/**
 * An I18n entity stores multiple translations assigned to an unique key.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Entity
@Table(name = "COR_I18N", uniqueConstraints = @UniqueConstraint(columnNames = {"C_KEY", "C_MODULE_NAME"}))
public class I18n extends BaseEntity implements Serializable {

    /** The natural key is used as references in the application (not nullable). */
    @Column(name = "C_KEY", nullable = false)
    private String key;
    /** The name of the owning {@code Module} to which this translation set belongs to. */
    @Column(name = "C_MODULE_NAME")
    private String moduleName = "CORE";
    /** The translation set of this entity. */
    @Embedded
    private I18nSet lang;
    /**
     * The cKey is a transient field that is constructed after the entity is loaded from the persistent store. Usually this field is
     * accessed from the client application to have an unique identifier - a combination of the owning {@code moduleName} and the {@code
     * key}.
     */
    @Transient
    private String cKey;

    /** Dear JPA... */
    public I18n() {
    }

    /**
     * Create a new I18n.
     *
     * @param moduleName The name of the {@code Module} where this entity belongs to
     * @param key The key to access this translation
     * @param lang A set of languages
     * @throws IllegalArgumentException when the {@code moduleName} or the {@code key} is {@literal null} or empty
     */
    public I18n(String moduleName, String key, I18nSet lang) {
        super();
        Assert.hasText(moduleName, "Not allowed to create an I18n instance with an empty moduleName");
        Assert.hasText(key, "Not allowed to create an I18n instance with an empty key");
        this.moduleName = moduleName;
        this.key = key;
        this.lang = lang;
    }

    /**
     * Create a new I18n.
     *
     * @param key The key to access this translation
     * @param lang A set of languages
     * @throws IllegalArgumentException when the {@code key} is {@literal null} or empty
     */
    public I18n(String key, I18nSet lang) {
        super();
        Assert.hasText(key, "Not allowed to create an I18n instance with an empty key");
        this.key = key;
        this.lang = lang;
    }

    /**
     * After loading the entity, combine the {@code moduleName} field and the {@code key} field. Store the concatenated String in a
     * transient field {@code cKey}.
     */
    @PostLoad
    protected void onLoad() {
        cKey = moduleName + key;
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
     * {@inheritDoc}
     * <p>
     * Use {@code key} and {@code moduleName} for calculation.
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
     * {@inheritDoc}
     * <p>
     * Use {@code key} and {@code moduleName} for comparison.
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof I18n)) {
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