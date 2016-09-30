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
package org.openwms.common.comm.sysu.api;

import java.util.function.Function;

import org.ameba.exception.NotFoundException;
import org.openwms.common.comm.sysu.SystemUpdateMessage;
import org.openwms.common.location.LocationGroup;
import org.openwms.common.location.LocationGroupService;
import org.openwms.common.location.LocationGroupState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A SystemUpdateMessageHandler.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Component
class SystemUpdateMessageHandler implements Function<SystemUpdateMessage, Void> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemUpdateMessageHandler.class);
    @Autowired
    private LocationGroupService<LocationGroup> service;

    /**
     * {@inheritDoc}
     */
    @Override
    public Void apply(SystemUpdateMessage message) {
        LOGGER.debug("Handling {}", message);
        LocationGroup lg = service.findByName(message.getLocationGroupName()).orElseThrow(() -> new NotFoundException(""));

        LocationGroupState[] states = parse(message.getErrorCode());
        service.changeGroupState(lg.getPk().toString(), states[0], states[1]);
        LOGGER.debug("Changed group states of LocationGroup {} to infeed:[{}], outfeed:[{}]", lg.getPk(),states[0], states[1]);
        return null;
    }

    private LocationGroupState[] parse(String errorCode) {
        LocationGroupState[] lgs = new LocationGroupState[]{LocationGroupState.NOT_AVAILABLE, LocationGroupState.NOT_AVAILABLE};
        if (errorCode.endsWith("1")) {
            lgs[1] = LocationGroupState.AVAILABLE;
        }
        if (errorCode.endsWith("11") || errorCode.endsWith("10")) {
            lgs[0] = LocationGroupState.AVAILABLE;
        }
        return lgs;
    }
}
