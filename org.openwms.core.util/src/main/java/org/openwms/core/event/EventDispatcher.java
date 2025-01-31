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

/**
 * An EventDispatcher dispatches / delivers events to subscribers.
 *
 * @author Heiko Scherrer
 */
public interface EventDispatcher extends EventBroker {

    /**
     * Take an event of type {@code T} and dispatch it to all subscribed
     * listeners.
     * Note: It is not defined whether the event is delivered synchronously or
     * asynchronously.
     *
     * @param <T> A subtype of RootApplicationEvent
     * @param event The event to deliver
     */
    <T extends RootApplicationEvent> void dispatch(T event);
}