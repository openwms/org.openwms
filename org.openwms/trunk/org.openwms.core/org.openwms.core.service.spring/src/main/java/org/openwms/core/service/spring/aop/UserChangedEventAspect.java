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
package org.openwms.core.service.spring.aop;

import java.util.EventObject;

import org.openwms.core.annotation.FireAfterTransaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * An applicationContextAware UserChangedEventAspect.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Component("userChangedEventAspect")
public class UserChangedEventAspect implements ApplicationContextAware {

    private ApplicationContext ctx;

    /**
     * Create a new UserChangedEventAspect.
     */
    public UserChangedEventAspect() {}

    /**
     * {@inheritDoc}
     * 
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.ctx = applicationContext;
    }

    /**
     * Only {@link ApplicationEvent}s are created and published over Springs
     * {@link ApplicationContext}.
     * 
     * @param publisher
     *            The instance that is publishing the event
     * @param events
     *            A list of event classes to fire
     * @throws Throwable
     *             Any exception is re-thrown
     */
    public void fireUserEvent(Object publisher, FireAfterTransaction events) throws Throwable {
        for (int i = 0; i < events.events().length; i++) {
            Class<? extends EventObject> event = events.events()[i];
            if (ApplicationEvent.class.isAssignableFrom(event)) {
                ctx.publishEvent((ApplicationEvent) event.getConstructor(Object.class).newInstance(publisher));
            }
        }
    }
}
