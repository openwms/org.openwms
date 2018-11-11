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
package org.openwms.core;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * An AbstractEntity, used as a base class for all domain classes.
 * <p>
 * Adds an unique identified to a subclassed domain class that is created when the class is instantiated on the client tier. At least this
 * uid is created before the instance is persisted the first time. The uid may not be mistaken with the id property that is usually used for
 * database identity (primary key) or with a business key column.
 * </p>
 * <p>
 * This class has an inner static declared class that is registered as a JPA EntityListener and forces the creation of an uid if not already
 * created before. This assures that each persisted entity has an uid.
 * </p>
 * <p>
 * The uid property is used by the ActionScript client application to synchronize client-side entity instances with server-side ones.
 * </p>
 * <strong>NOTE:</strong><br />
 * This class uses the uid for comparison with {@link #equals(Object)} and calculation of {@link #hashCode()}.
 *
 * @param <ID> The type of technical key
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 */
@Deprecated
@XmlTransient
@MappedSuperclass
@EntityListeners({AbstractEntity.AbstractEntityListener.class})
public abstract class AbstractEntity<ID extends Serializable> implements DomainObject<ID> {

    private static final int UID_LENGTH = 36;
    /**
     * Suffix for the FIND_ALL named query. Default {@value}
     */
    public static final String FIND_ALL = ".findAll";

    /**
     * Unique identifier column, used for ActionScript clients.
     */
    /* "UUID" and "UID" are Oracle reserved keywords -> "ENTITY_UID" */
    @XmlTransient
    @Column(name = "C_ENTITY_UID", unique = true, nullable = false, updatable = false, length = UID_LENGTH)
    private String uid;

    /** The date this product has been created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_CREATED_DT")
    private Date createdDate;

    /** The date this LoadUnit has changed the last time. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_CHANGED_DT")
    private Date changedDate;


    /**
     * {@inheritDoc}
     * <p>
     * Compare the uid property field.
     *
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        return (o == this || (o instanceof AbstractEntity && uid().equals(((AbstractEntity) o).uid())));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Use the uid to calculate the hashCode.
     *
     * @see java.lang.String#hashCode()
     */
    @Override
    public int hashCode() {
        return uid().hashCode();
    }

    /**
     * An AbstractEntityListener forces the creation of an uid before the entity is persisted.
     *
     * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
     * @version $Revision$
     * @since 0.1
     */
    @XmlTransient
    public static class AbstractEntityListener {

        /**
         * Before a new entity is persisted we generate an UUID for it.
         *
         * @param abstractEntity The entity to persist
         */
        @PrePersist
        public void onPreInsert(AbstractEntity abstractEntity) {
            abstractEntity.uid();
        }
    }

    /**
     * Set the creation date.
     */
    @PrePersist
    protected void prePersist() {
        this.createdDate = new Date();
    }

    /**
     * Set the changed date.
     */
    @PreUpdate
    void preUpdate() {
        this.changedDate = new Date();
    }

    private String uid() {
        if (uid == null) {
            uid = UUID.randomUUID().toString();
        }
        return uid;
    }
}
