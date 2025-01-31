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
package org.openwms.core.aop;

import org.ameba.exception.TechnicalRuntimeException;
import org.openwms.core.annotation.FireAfterTransaction;
import org.openwms.core.annotation.FireAfterTransactionAsynchronous;
import org.openwms.core.event.RootApplicationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.EventObject;

/**
 * An UserChangedEventAspect fires events after a method invocation completes.
 * It's main purpose is to fire events after a transaction succeeds, thereby the
 * advice must be enabled around Spring's Transaction advice.
 * <p>
 * Use the {@link FireAfterTransaction} event and declare some type of events
 * inside the <code>value</code> attribute. Instances of these events will then
 * be fired after the transaction completes.
 * </p>
 * Example: <blockquote>
 *
 * <pre>
 * &#064;FireAfterTransaction(events = { UserChangedEvent.class })
 * public User save(User user) { .. }
 * </pre>
 *
 * </blockquote>
 * <p>
 * The component can be referenced by name {@value #COMPONENT_NAME}.
 * </p>
 *
 * @author Heiko Scherrer
 */
@Component(FireAfterTransactionAspect.COMPONENT_NAME)
public class FireAfterTransactionAspect {

    /** Springs component name. */
    public static final String COMPONENT_NAME = "fireAfterTransactionAspect";
    private static final Logger LOGGER = LoggerFactory.getLogger(FireAfterTransactionAspect.class);
    private final ApplicationContext ctx;

    public FireAfterTransactionAspect(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    /**
     * Only {@link ApplicationEvent}s are created and published over Springs
     * {@link ApplicationContext}.
     *
     * @param publisher The instance that is publishing the event
     * @param events A list of event classes to fire
     */
    public void fireEvent(Object publisher, FireAfterTransaction events) {
        try {
            for (int i = 0; i < events.events().length; i++) {
                Class<? extends EventObject> event = events.events()[i];
                if (ApplicationEvent.class.isAssignableFrom(event)) {
                    ctx.publishEvent((ApplicationEvent) event.getConstructor(Object.class).newInstance(publisher));
                }
            }
        } catch (Exception e) {
            throw new TechnicalRuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Only {@link ApplicationEvent}s are created and published over Springs
     * {@link ApplicationContext}.
     *
     * @param publisher The instance that is publishing the event
     * @param events Stores a list of event classes to fire
     */
    @Async
    public void fireEventAsync(Object publisher, FireAfterTransactionAsynchronous events) {
        try {
            for (int i = 0; i < events.events().length; i++) {
                Class<? extends EventObject> event = events.events()[i];
                if (RootApplicationEvent.class.isAssignableFrom(event)) {
                    LOGGER.debug("Sending event: [{}]", event);
                    ctx.publishEvent((RootApplicationEvent) event.getConstructor(Object.class).newInstance(publisher));
                }
            }
        } catch (Exception e) {
            throw new TechnicalRuntimeException(e.getMessage(), e);
        }
    }
}