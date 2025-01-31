/*
 * Copyright 2005-2025 the original author or authors.
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

/**
 * An OnRemovalListener is able to hook in the lifecycle of an entity class. A class
 * implementing this interface is called before an Entity of type {@code T} is removed and
 * can prevent the deletion.
 *
 * @param <T> Any kind of {@link BaseEntity}
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 */
public interface OnRemovalListener<T extends BaseEntity> {

    /**
     * Decide whether the {@code entity} can be removed or not.
     *
     * @param entity The instance to be removed.
     * @return {@code true} if removal is allowed, otherwise {@code false}
     */
    boolean preRemove(T entity);
}