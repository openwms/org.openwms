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
import org.openwms.common.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * A LocationRedirector votes for a {@link RedirectVote} whether the target location is enabled for infeed. The class is lazy initialized.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @see DecisionVoter
 * @since 0.1
 */
@Lazy
@Order(5)
@Component
class LocationRedirector implements DecisionVoter<RedirectVote> {

    @Autowired
    private CommonGateway commonGateway;
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationRedirector.class);

    /**
     * {@inheritDoc}
     * <p>
     * Simple check for blocked infeed.
     */
    @Override
    public void voteFor(RedirectVote vote) throws DeniedException {

        Optional<Location> group = commonGateway.getLocation(vote.getTarget());
        if (group.isPresent()) {
            if (group.get().isInfeedBlocked()) {
                LOGGER.warn("The targetLocation is blocked and is not accepted as target");
                // we do not actually throw because of other voters in the chain
            }
            // make change here
            vote.getTransportOrder().setTargetLocation(vote.getTarget());
        }
    }
}