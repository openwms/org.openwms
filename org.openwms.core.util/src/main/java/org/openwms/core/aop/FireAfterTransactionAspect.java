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
package org.openwms.core.aop;

import org.openwms.core.annotation.FireAfterTransaction;
import org.openwms.core.annotation.FireAfterTransactionAsynchronous;
import org.openwms.core.event.RootApplicationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.annotation.FireAfterTransaction
 */
@Component(FireAfterTransactionAspect.COMPONENT_NAME)
public class FireAfterTransactionAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(FireAfterTransactionAspect.class);
    @Autowired
    private ApplicationContext ctx;
    /** Springs component name. */
    public static final String COMPONENT_NAME = "fireAfterTransactionAspect";

    /**
     * Only {@link ApplicationEvent}s are created and published over Springs
     * {@link ApplicationContext}.
     * 
     * @param publisher
     *            The instance that is publishing the event
     * @param events
     *            A list of event classes to fire
     * @throws Exception
     *             Any exception is re-thrown
     */
    public void fireEvent(Object publisher, FireAfterTransaction events) throws Exception {
        for (int i = 0; i < events.events().length; i++) {
            Class<? extends EventObject> event = events.events()[i];
            if (ApplicationEvent.class.isAssignableFrom(event)) {
                ctx.publishEvent((ApplicationEvent) event.getConstructor(Object.class).newInstance(publisher));
            }
        }
    }

    /**
     * Only {@link ApplicationEvent}s are created and published over Springs
     * {@link ApplicationContext}.
     * 
     * @param publisher
     *            The instance that is publishing the event
     * @param events
     *            Stores a list of event classes to fire
     * @throws Exception
     *             Any exception is re-thrown
     */
    @Async
    public void fireEventAsync(Object publisher, FireAfterTransactionAsynchronous events) throws Exception {
        for (int i = 0; i < events.events().length; i++) {
            Class<? extends EventObject> event = events.events()[i];
            if (RootApplicationEvent.class.isAssignableFrom(event)) {
                LOGGER.debug("Sending event:" + event);
                ctx.publishEvent((RootApplicationEvent) event.getConstructor(Object.class).newInstance(publisher));
            }
        }
    }
}