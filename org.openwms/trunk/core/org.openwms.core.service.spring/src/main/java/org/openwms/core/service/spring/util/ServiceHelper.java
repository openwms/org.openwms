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
package org.openwms.core.service.spring.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.domain.DomainObject;
import org.openwms.core.integration.GenericDao;

/**
 * A ServiceHelper. Collection of useful utility methods to convert and deal
 * with domain objects.
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision$
 * @since 0.1
 */
public final class ServiceHelper {

    /**
     * Pass in a list of detached entity classes and retrieve a list of managed
     * ones.
     * 
     * @param <T>
     *            The type of entity
     * @param <ID>
     *            The type of entity's primary key
     * @param detachedEntities
     *            The list of detached entities
     * @param dao
     *            An instance of a DAO to use for conversion
     * @return The list of managed entities or an empty list
     */
    public static <T extends AbstractEntity & DomainObject<ID>, ID extends Serializable> List<T> managedEntities(
            List<T> detachedEntities, GenericDao<T, ID> dao) {
        if (detachedEntities == null || detachedEntities.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> managed = new ArrayList<T>(detachedEntities.size());
        for (T detachedEntity : detachedEntities) {
            T managedTu = dao.findById(detachedEntity.getId());
            if (managedTu != null) {
                managed.add(managedTu);
            }
        }
        return managed;
    }
}
