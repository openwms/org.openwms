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
package org.openwms.core.service.spring.event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.openwms.core.service.event.EventListener;
import org.openwms.core.service.exception.ServiceRuntimeException;
import org.openwms.core.util.event.RootApplicationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * A SimpleEventDispatcher.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
@Component(value = SimpleEventDispatcher.COMPONENT_NAME)
public class SimpleEventDispatcher implements EventDispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEventDispatcher.class);
    private Map<Class<? extends RootApplicationEvent>, Set<EventListener>> subscriptions = new HashMap<Class<? extends RootApplicationEvent>, Set<EventListener>>();
    /** Springs service name. */
    public static final String COMPONENT_NAME = "simpleEventDispatcher";
    @Autowired
    private ApplicationContext ctx;

    /**
     * @see org.openwms.core.service.event.EventBroker#subscribe(java.lang.Class,
     *      org.openwms.core.service.event.EventListener)
     */
    @Override
    public void subscribe(Class<? extends RootApplicationEvent> event, EventListener listener) {
        LOGGER.debug("subscribe");
        Set<EventListener> listeners;
        synchronized (subscriptions) {
            if (subscriptions.containsKey(event)) {
                listeners = subscriptions.get(event);
                if (null == listeners) {
                    listeners = new HashSet<EventListener>(1);
                    subscriptions.put(event, listeners);
                }
                listeners.add(listener);
            } else {
                listeners = new HashSet<EventListener>(1);
                listeners.add(listener);
                subscriptions.put(event, listeners);
            }
        }
    }

    /**
     * @see org.openwms.core.service.event.EventBroker#subscribe(java.lang.Class,
     *      java.lang.String)
     */
    @Override
    public void subscribe(Class<? extends RootApplicationEvent> event, String listenerBeanName) {
        Object instance = ctx.getBean(listenerBeanName);
        if (instance instanceof EventListener) {
            subscribe(event, (EventListener) instance);
        } else {
            throw new ServiceRuntimeException("The bean with name " + listenerBeanName
                    + " is not of type EventListener and cannot subscribe to events");
        }
    }

    /**
     * @see org.openwms.core.service.event.EventBroker#unsubscribe(java.lang.Class,
     *      org.openwms.core.service.event.EventListener)
     */
    @Override
    public void unsubscribe(Class<? extends RootApplicationEvent> event, EventListener listener) {
        LOGGER.debug("unsubscribe");
        if (subscriptions.containsKey(event)) {
            synchronized (subscriptions.get(event)) {
                subscriptions.get(event).remove(listener);
            }
        }
    }

    /**
     * @see org.openwms.core.service.event.EventBroker#unsubscribe(java.lang.Class,
     *      java.lang.String)
     */
    @Override
    public void unsubscribe(Class<? extends RootApplicationEvent> event, String listenerBeanName) {
        Object instance = ctx.getBean(listenerBeanName);
        if (instance instanceof EventListener) {
            unsubscribe(event, (EventListener) instance);
        } else {
            throw new ServiceRuntimeException("The bean with name " + listenerBeanName
                    + " is not of type EventListener and cannot unsubscribe from events");
        }
    }

    /**
     * @see org.openwms.core.service.spring.event.EventDispatcher#dispatch(org.openwms.core.util.event.RootApplicationEvent)
     */
    @Override
    public <T extends RootApplicationEvent> void dispatch(T event) {
        LOGGER.debug("In dispatch()");
        if (null == event || !subscriptions.containsKey(event.getClass())) {
            return;
        }
        LOGGER.debug("In dispatch()2");
        synchronized (subscriptions.get(event.getClass())) {
            Set<EventListener> listeners = subscriptions.get(event.getClass());
            for (EventListener eventListener : listeners) {
                LOGGER.debug("DRINN");
                eventListener.onEvent(event);
            }
        }
    }
}