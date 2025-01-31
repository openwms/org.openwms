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
package org.openwms.core.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * A NonBlockingEventPublisherImpl is publishing events asynchronously.
 *
 * @author Heiko Scherrer
 */
@Component(value = NonBlockingEventPublisherImpl.COMPONENT_NAME)
public class NonBlockingEventPublisherImpl<T extends RootApplicationEvent> implements EventPublisher<T> {

    /** Springs service name. */
    public static final String COMPONENT_NAME = "nonBlockingEventPublisherImpl";

    private final EventDispatcher dispatcher;

    public NonBlockingEventPublisherImpl(EventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    /**
     * {@inheritDoc}
     */
    @Async
    @Override
    public void publish(T event) {
        dispatcher.dispatch(event);
    }
}