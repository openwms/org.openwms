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
package org.openwms.tms.voter;

import java.util.Optional;

import org.openwms.common.CommonGateway;
import org.openwms.tms.targets.LocationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * A LocationGroupRedirector votes for a {@link RedirectVote} whether the target locationGroup is enabled for infeed. The class is lazy
 * initialized.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @since 1.0
 */
@Lazy
@Order(10)
@Component
class LocationGroupRedirector extends TargetRedirector<LocationGroup> {

    @Autowired
    private CommonGateway commonGateway;

    @Override
    protected boolean isTargetAvailable(LocationGroup target) {
        return !target.isInfeedBlocked();
    }

    @Override
    protected Optional<LocationGroup> resolveTarget(RedirectVote vote) {
        return commonGateway.getLocationGroup(vote.getTarget());
    }

    @Override
    protected void assignTarget(RedirectVote vote) {
        vote.getTransportOrder().setTargetLocationGroup(vote.getTarget());
    }
}