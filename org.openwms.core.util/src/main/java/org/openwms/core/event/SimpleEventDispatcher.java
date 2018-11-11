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
package org.openwms.core.event;

import org.ameba.exception.ServiceLayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A SimpleEventDispatcher.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Component(value = SimpleEventDispatcher.COMPONENT_NAME)
public class SimpleEventDispatcher implements EventDispatcher {

    private final Map<Class<? extends RootApplicationEvent>, Set<EventListener>> subscriptions = new HashMap<>();
    /** Springs service name. */
    public static final String COMPONENT_NAME = "simpleEventDispatcher";
    @Autowired
    private ApplicationContext ctx;

    /**
     * @see org.openwms.core.event.EventBroker#subscribe(java.lang.Class, org.openwms.core.event.EventListener)
     */
    @Override
    public void subscribe(Class<? extends RootApplicationEvent> event, EventListener listener) {
        Set<EventListener> listeners;
        synchronized (subscriptions) {
            if (subscriptions.containsKey(event)) {
                listeners = subscriptions.get(event);
                if (null == listeners) {
                    listeners = new HashSet<>(1);
                    subscriptions.put(event, listeners);
                }
                listeners.add(listener);
            } else {
                listeners = new HashSet<>(1);
                listeners.add(listener);
                subscriptions.put(event, listeners);
            }
        }
    }

    /**
     * @see org.openwms.core.event.EventBroker#subscribe(java.lang.Class, java.lang.String)
     */
    @Override
    public void subscribe(Class<? extends RootApplicationEvent> event, String listenerBeanName) {
        Object instance = ctx.getBean(listenerBeanName);
        if (instance instanceof EventListener) {
            subscribe(event, (EventListener) instance);
        } else {
            throw new ServiceLayerException("The bean with name " + listenerBeanName + " is not of type EventListener and cannot subscribe to events");
        }
    }

    /**
     * @see org.openwms.core.event.EventBroker#unsubscribe(java.lang.Class, org.openwms.core.event.EventListener)
     */
    @Override
    public void unsubscribe(Class<? extends RootApplicationEvent> event, EventListener listener) {
        if (subscriptions.containsKey(event)) {
            synchronized (subscriptions.get(event)) {
                subscriptions.get(event).remove(listener);
            }
        }
    }

    /**
     * @see org.openwms.core.event.EventBroker#unsubscribe(java.lang.Class, java.lang.String)
     */
    @Override
    public void unsubscribe(Class<? extends RootApplicationEvent> event, String listenerBeanName) {
        Object instance = ctx.getBean(listenerBeanName);
        if (instance instanceof EventListener) {
            unsubscribe(event, (EventListener) instance);
        } else {
            throw new ServiceLayerException("The bean with name " + listenerBeanName + " is not of type EventListener and cannot unsubscribe from events");
        }
    }

    /**
     * @see org.openwms.core.service.spring.event.EventDispatcher#dispatch(org.openwms.core.event.RootApplicationEvent)
     */
    @Override
    public <T extends RootApplicationEvent> void dispatch(T event) {
        if (null == event || !subscriptions.containsKey(event.getClass())) {
            return;
        }
        synchronized (subscriptions.get(event.getClass())) {
            Set<EventListener> listeners = subscriptions.get(event.getClass());
            for (EventListener eventListener : listeners) {
                eventListener.onEvent(event);
            }
        }
    }
}