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
import org.openwms.tms.target.LocationGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * A LocationGroupRedirector votes for a {@link RedirectVote} whether the target locationGroup is enabled for infeed. The class is lazy
 * initialized.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @see org.openwms.tms.voter.DecisionVoter
 * @since 0.1
 */
@Lazy
@Order(10)
@Component
class LocationGroupRedirector implements DecisionVoter<RedirectVote> {

    @Autowired
    private CommonGateway commonGateway;
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationGroupRedirector.class);

    /**
     * {@inheritDoc}
     * <p>
     * Simple check for blocked infeed.
     */
    @Override
    public void voteFor(RedirectVote vote) throws DeniedException {

        Optional<LocationGroup> group = commonGateway.getLocationGroup(vote.getTarget());
        if (group.isPresent()) {
            if (group.get().isInfeedBlocked()) {
                LOGGER.warn("The targetLocationGroup is blocked and is not accepted as target");
                // we do not actually throw because of other voters in the chain
            }
        }
        // make change here
        vote.getTransportOrder().setTargetLocationGroup(vote.getTarget());
    }
}