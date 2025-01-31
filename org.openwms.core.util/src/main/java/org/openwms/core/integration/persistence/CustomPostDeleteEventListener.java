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
package org.openwms.core.integration.persistence;

import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.event.spi.EnversPostDeleteEventListenerImpl;
import org.hibernate.event.spi.PostDeleteEvent;

/**
 * A CustomPostDeleteEventListener overrides the default implementation.
 *
 * @author Heiko Scherrer
 */
public class CustomPostDeleteEventListener extends EnversPostDeleteEventListenerImpl {

    public CustomPostDeleteEventListener(EnversService enversService) {
        super(enversService);
    }

    /**
     * {@inheritDoc}
     *
     * Bypass the default implementation.
     */
    @Override
    public void onPostDelete(PostDeleteEvent event) {
        // Bypass the default implementation and do nothing.
    }
}
