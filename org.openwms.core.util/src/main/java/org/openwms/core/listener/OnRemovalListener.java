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
package org.openwms.core.listener;

import org.ameba.integration.jpa.BaseEntity;
import org.openwms.core.AbstractEntity;

/**
 * An OnRemovalListener is able to hook in the lifecycle of an entity class. A class implementing this interface is called before an entity
 * of type <code>T</code> is removed.
 *
 * @param <T> Any kind of {@link AbstractEntity}
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 */
public interface OnRemovalListener<T extends BaseEntity> {

    /**
     * Do something prior the <code>entity</code> instance is been removed.
     *
     * @param entity The instance to be removed.
     * @return <code>true</code> if removal is allowed, otherwise <code>false</code>
     * @throws RemovalNotAllowedException When it is not allowed to remove the entity, because depending items exist
     */
    boolean preRemove(T entity) throws RemovalNotAllowedException;
}