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
 */
public final class ServiceHelper {

    /** Hide Constructor. */
    private ServiceHelper() {
    }

    /**
     * Returns a list of managed entities from a collection of detached
     * entities. Each entity in <code>detachedEntities</code> is reloaded with
     * the <code>dao</code>.
     *
     * @param <T> The type of entity
     * @param <ID> The type of entity's primary key
     * @param detachedEntities A collection of detached entities
     * @param dao An instance of a DAO, used for reloading
     * @return The list of managed entities or an empty list
     */
    public static <T extends AbstractEntity<ID>, ID extends Serializable> List<T> managedEntities(Collection<T> detachedEntities, GenericDao<T, ID> dao) {
        if (detachedEntities == null || detachedEntities.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> managed = new ArrayList<>(detachedEntities.size());
        for (T detachedEntity : detachedEntities) {
            T managedTu = dao.findById(detachedEntity.getId());
            if (managedTu != null) {
                managed.add(managedTu);
            }
        }
        return managed;
    }
}