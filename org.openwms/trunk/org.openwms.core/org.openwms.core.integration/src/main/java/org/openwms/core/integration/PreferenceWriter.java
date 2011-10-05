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
package org.openwms.core.integration;

import java.io.Serializable;

import org.openwms.core.domain.system.AbstractPreference;

/**
 * A PreferenceWriter.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
public interface PreferenceWriter<ID extends Serializable> extends PreferenceDao<ID> {

    /**
     * Synchronize an entity with the persistence layer and return it.
     * 
     * @param entity
     *            Entity instance to be synchronized with the persistence layer
     * @return The synchronized entity instance. If JPA is used as
     *         implementation, the returned instance is managed.
     */
    <T extends AbstractPreference> T save(T entity);

    /**
     * Removes an already persistent entity.
     * 
     * @param entity
     *            Entity instance to be removed
     */
    void remove(AbstractPreference entity);

}
