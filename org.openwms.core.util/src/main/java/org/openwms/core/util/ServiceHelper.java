/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.core.util;

import org.openwms.core.AbstractEntity;
import org.openwms.core.GenericDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A ServiceHelper centralizes common used utility methods to convert and deal
 * with domain objects.
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision$
 * @since 0.1
 */
public final class ServiceHelper {

    /**
     * Hide Constructor.
     */
    private ServiceHelper() {}

    /**
     * Returns a list of managed entities from a collection of detached
     * entities. Each entity in <code>detachedEntities</code> is reloaded with
     * the <code>dao</code>.
     * 
     * @param <T>
     *            The type of entity
     * @param <ID>
     *            The type of entity's primary key
     * @param detachedEntities
     *            A collection of detached entities
     * @param dao
     *            An instance of a DAO, used for reloading
     * @return The list of managed entities or an empty list
     */
    public static <T extends AbstractEntity<ID>, ID extends Serializable> List<T> managedEntities(
            Collection<T> detachedEntities, GenericDao<T, ID> dao) {
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