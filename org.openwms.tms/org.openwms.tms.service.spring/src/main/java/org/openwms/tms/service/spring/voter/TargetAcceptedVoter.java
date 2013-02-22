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
package org.openwms.tms.service.spring.voter;

import org.openwms.core.service.voter.DecisionVoter;
import org.openwms.core.service.voter.DeniedException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * A TargetAcceptedVoter votes for a {@link RedirectVote} whether the target
 * location or the target locationGroup is enabled for infeed. The class is lazy
 * initialized.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.service.voter.DecisionVoter
 */
@Lazy
@Component(TargetAcceptedVoter.COMPONENT_NAME)
public class TargetAcceptedVoter implements DecisionVoter<RedirectVote> {

    /** Springs component name. */
    public static final String COMPONENT_NAME = "targetAcceptedVoter";

    /**
     * {@inheritDoc}
     * 
     * Simple check for blocked infeed.
     */
    @Override
    public void voteFor(RedirectVote vote) throws DeniedException {
        if (null != vote.getLocationGroup() && vote.getLocationGroup().isInfeedBlocked()) {
            throw new DeniedException("The targetLocationGroup is blocked and is not accepted as target");
        }
        if (null != vote.getLocation() && vote.getLocation().isInfeedBlocked()) {
            throw new DeniedException("The targetLocation is blocked and is not accepted as target");
        }
    }
}