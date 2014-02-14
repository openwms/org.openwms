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
package org.openwms.tms.service.spring;

import org.openwms.common.domain.TransportUnit;
import org.openwms.tms.service.delegate.TransportOrderStateDelegate;
import org.openwms.tms.util.event.TransportServiceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * A TransportOrderStateTracker is a listening adapter that delegates to an instance of {@link TransportOrderStateDelegate}.
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.tms.service.delegate.TransportOrderStateDelegate
 */
@Transactional
@Component
public class TransportOrderStateTracker implements ApplicationListener<TransportServiceEvent> {

    @Autowired
    private TransportOrderStateDelegate transportOrderStateDelegate;

    /**
     * Override the delegate implementation that tracks the next order states.
     * 
     * @param transportOrderStateDelegate
     *            The delegate to use
     */
    public void setTransportOrderStateDelegate(TransportOrderStateDelegate transportOrderStateDelegate) {
        this.transportOrderStateDelegate = transportOrderStateDelegate;
    }

    /**
     * Delegate all {@link TransportServiceEvent}s to a delegate synchronously.
     * 
     * @param event
     *            A {@link TransportServiceEvent}.
     */
    @Override
    public void onApplicationEvent(TransportServiceEvent event) {
        switch (event.getType()) {
        case TRANSPORT_CREATED:
            transportOrderStateDelegate.afterCreation((TransportUnit) event.getSource());
            break;
        case TRANSPORT_INTERRUPTED:
            transportOrderStateDelegate.onInterrupt((Long) event.getSource());
            break;
        case TRANSPORT_ONFAILURE:
            transportOrderStateDelegate.onFailure((Long) event.getSource());
            break;
        case TRANSPORT_CANCELED:
            transportOrderStateDelegate.onCancel((Long) event.getSource());
            break;
        case TRANSPORT_FINISHED:
            transportOrderStateDelegate.afterFinish((Long) event.getSource());
            break;
        default:
            break;
        }
    }
}