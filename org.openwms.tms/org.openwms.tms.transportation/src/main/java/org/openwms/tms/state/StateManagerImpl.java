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
package org.openwms.tms.state;

import static org.openwms.tms.TransportOrderState.CANCELED;
import static org.openwms.tms.TransportOrderState.INITIALIZED;
import static org.openwms.tms.TransportOrderState.ONFAILURE;
import static org.openwms.tms.TransportOrderState.STARTED;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

import org.ameba.i18n.Translator;
import org.openwms.tms.StateChangeException;
import org.openwms.tms.StateManager;
import org.openwms.tms.TMSMessageCodes;
import org.openwms.tms.TransportOrder;
import org.openwms.tms.TransportOrderRepository;
import org.openwms.tms.TransportOrderState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A StateManagerImpl.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
//@Transactional(propagation = Propagation.MANDATORY) don't because it is called within a Hibernate generation
@Component
class StateManagerImpl implements StateManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(StateManagerImpl.class);
    @Transient
    @Autowired
    private Translator translator;

    @Transient
    @Autowired
    private TransportOrderRepository repo;

    @Override
    public void validate(TransportOrderState newState, TransportOrder transportOrder) throws StateChangeException {
        TransportOrderState state = transportOrder.getState();
        LOGGER.debug("Request for state change of TransportOrder with PK [{}] from [{}] to [{}]", transportOrder.getPk(), state, newState);
        if (newState == null) {
            throw new StateChangeException(translator.translate(TMSMessageCodes.TO_STATE_CHANGE_NULL_STATE), TMSMessageCodes.TO_STATE_CHANGE_NULL_STATE, transportOrder.getPersistentKey());
        }
        if (state.compareTo(newState) > 0) {
            // Don't allow to turn back the state!
            throw new StateChangeException(translator.translate(TMSMessageCodes.TO_STATE_CHANGE_BACKWARDS_NOT_ALLOWED, transportOrder.getPersistentKey()), TMSMessageCodes.TO_STATE_CHANGE_BACKWARDS_NOT_ALLOWED, transportOrder.getPersistentKey());
        }
        switch (state) {
            case CREATED:
                if (newState != INITIALIZED && newState != CANCELED) {
                    throw new StateChangeException(translator.translate(TMSMessageCodes.TO_STATE_CHANGE_NOT_READY, newState, transportOrder.getPersistentKey()), TMSMessageCodes.TO_STATE_CHANGE_NOT_READY, newState, transportOrder.getPersistentKey());
                }
                if (transportOrder.getTransportUnitBK() == null || transportOrder.getTransportUnitBK().isEmpty() || transportOrder.getTargetLocation() == null && transportOrder.getTargetLocationGroup() == null) {
                    throw new StateChangeException(String.format("Not all properties set to turn TransportOrder into next state! transportUnit's barcode [%s], targetLocation [%s], targetLocationGroup [%s]", transportOrder.getTransportUnitBK(), transportOrder.getTargetLocation(), transportOrder.getTargetLocationGroup()));
                }
                break;
            case INITIALIZED:
                if (newState != STARTED && newState != CANCELED && newState != ONFAILURE) {
                    throw new StateChangeException(translator.translate(TMSMessageCodes.STATE_CHANGE_ERROR_FOR_INITIALIZED_TO, transportOrder.getPersistentKey()), TMSMessageCodes.STATE_CHANGE_ERROR_FOR_INITIALIZED_TO, transportOrder.getPersistentKey());
                }
                if (newState == STARTED && repo.findByTransportUnitBKAndStates(transportOrder.getTransportUnitBK(), STARTED).size() > 0) {
                    throw new StateChangeException(translator.translate(TMSMessageCodes.START_TO_NOT_ALLOWED_ALREADY_STARTED_ONE, transportOrder.getTransportUnitBK(), transportOrder.getPersistentKey()), TMSMessageCodes.START_TO_NOT_ALLOWED_ALREADY_STARTED_ONE, transportOrder.getTransportUnitBK(), transportOrder.getPersistentKey());
                }
                List<TransportOrder> orders = repo.findByTransportUnitBKAndStates(transportOrder.getTransportUnitBK(), STARTED);
                LOGGER.debug("Current State is [{}], new state is [{}], # of started is [{}]", state, newState, repo.numberOfTransportOrders(transportOrder.getTransportUnitBK(), STARTED));
                break;
            case STARTED:
                // new state may be one of the following, no additional if-check required here
                break;
            case FINISHED:
            case ONFAILURE:
            case CANCELED:
                throw new StateChangeException("Not allowed to change the state of a TransportOrder that has already been completed. Current state is CANCELED");
            default:
                throw new IllegalStateException("State not managed: " + state);
        }
        switch (newState) {
            case STARTED:
                transportOrder.setStartDate(new Date());
                break;
            case FINISHED:
            case ONFAILURE:
            case CANCELED:
                transportOrder.setEndDate(new Date());
                break;
            default:
                // OK for all others
        }
        LOGGER.debug("Request processed, order is now [{}]", newState);
    }
}
