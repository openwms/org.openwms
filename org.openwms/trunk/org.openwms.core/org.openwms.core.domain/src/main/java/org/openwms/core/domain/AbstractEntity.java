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
package org.openwms.core.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

/**
 * An AbstractEntity used as a base class for all domain classes.
 * 
 * Adds an unique identified to each subclassed domain class that is usually
 * created when the class is instantiated on the client tier. At least this uid
 * is created before the instance is persisted the first time. The uid may not
 * be mistaken with the id property that is usually used for database identity
 * (primary key) or with a business key column.
 * 
 * This class has an inner static declared class that is registered as a JPA
 * EntityListener and forces to create an uid if not already done before. This
 * assures that each persisted entity does have an uid.
 * 
 * The uid property is useful on the ActionScript client application.
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision$
 * @since 0.1
 */
@MappedSuperclass
@EntityListeners({ AbstractEntity.AbstractEntityListener.class })
public abstract class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 827478159133738540L;

    /**
     * Suffix for the FIND_ALL named query.
     */
    public static final String FIND_ALL = ".findAll";

    /**
     * Suffix for the FIND_BY_ID named query.
     */
    public static final String FIND_BY_ID = ".findById";

    /**
     * Unique identifier column, used for ActionScript clients.
     */
    /* "UUID" and "UID" are Oracle reserved keywords -> "ENTITY_UID" */
    @Column(name = "ENTITY_UID", unique = true, nullable = false, updatable = false, length = 36)
    private String uid;

    /**
     * {@inheritDoc}
     * 
     * Compare the uid property fields.
     * 
     * @see java.lang.Object#equals()
     */
    @Override
    public boolean equals(Object o) {
        return (o == this || (o instanceof AbstractEntity && uid().equals(((AbstractEntity) o).uid())));
    }

    /**
     * {@inheritDoc}
     * 
     * Delegates to java.lang.String#hashCode().
     * 
     * @see java.lang.String#hashCode()
     */
    @Override
    public int hashCode() {
        return uid().hashCode();
    }

    /**
     * An AbstractEntityListener. Forces the creation of the uid before the
     * entity is persisted.
     * 
     * @author <a href="mailto:russelltina@users.sourceforge.net">Tina
     *         Russell</a>
     * @version $Revision$
     * @since 0.1
     */
    public static class AbstractEntityListener {

        /**
         * Before a new entity is persisted we generated an UUID for it.
         * 
         * @param abstractEntity
         *            The entity to persist
         */
        @PrePersist
        public void onPreInsert(AbstractEntity abstractEntity) {
            abstractEntity.uid();
        }
    }

    private String uid() {
        if (uid == null) {
            uid = UUID.randomUUID().toString();
        }
        return uid;
    }

}
