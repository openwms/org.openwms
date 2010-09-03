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
package org.openwms.tms.service.impl;

import javax.annotation.PostConstruct;

import org.openwms.common.domain.TransportUnit;
import org.openwms.tms.integration.TransportOrderDao;
import org.openwms.tms.service.order.delegate.DefaultOrderStateDelegate;
import org.openwms.tms.util.event.TransportServiceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A TransportOrderStateTracker.
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision: $
 * @since 0.1
 * @see org.openwms.tms.service.order.delegate.DefaultOrderStateDelegate
 * @see org.openwms.tms.service.impl.TransportOrderStateDelegate
 */
@Service
@Transactional
public class TransportOrderStateTracker implements ApplicationListener<TransportServiceEvent> {

    private TransportOrderStateDelegate transportOrderStateDelegate;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private TransportOrderDao dao;

    /**
     * Create a new TransportOrderStateTracker.
     */
    public TransportOrderStateTracker() {}

    /**
     * Set the delegate implementation that tracks the next order states.
     * 
     * @param transportOrderStateDelegate
     *            The delegate to use
     */
    public void setTransportOrderStateDelegate(TransportOrderStateDelegate transportOrderStateDelegate) {
        this.transportOrderStateDelegate = transportOrderStateDelegate;
    }

    public void setDao(TransportOrderDao dao) {
        this.dao = dao;
    }

    @PostConstruct
    private void initialize() {
        this.transportOrderStateDelegate = new DefaultOrderStateDelegate(this.dao);
    }

    /**
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    @Override
    public void onApplicationEvent(TransportServiceEvent event) {
        if (logger.isDebugEnabled()) {
            logger.debug("Got event" + event.getType());
        }
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
        }
    }

}
