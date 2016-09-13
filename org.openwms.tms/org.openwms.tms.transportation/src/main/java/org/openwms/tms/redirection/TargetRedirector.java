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
package org.openwms.tms.redirection;

import java.util.Optional;

import org.ameba.i18n.Translator;
import org.openwms.tms.Message;
import org.openwms.tms.TMSMessageCodes;
import org.openwms.tms.Target;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A TargetRedirector.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @since 1.0
 */
abstract class TargetRedirector<T extends Target> implements DecisionVoter<RedirectVote> {

    @Autowired
    private Translator translator;
    private static final Logger LOGGER = LoggerFactory.getLogger(TargetRedirector.class);

    /**
     * The implementation has to vote for a certain vote on particular rules that are implemented by the voter.
     *
     * @param vote The vote to vote for
     * @throws DeniedException is thrown when the voter cannot vote for the action
     */
    @Override
    public void voteFor(RedirectVote vote) throws DeniedException {
        Optional<T> optionalTarget = resolveTarget(vote);
        if (optionalTarget.isPresent()) {
            if (isTargetAvailable(optionalTarget.get())) {
                assignTarget(vote);
                vote.complete();
            } else {
                String msg = translator.translate(TMSMessageCodes.TARGET_BLOCKED_MSG, vote.getTarget(), vote.getTransportOrder().getPersistentKey());
                vote.addMessage(new Message.Builder().withMessage(msg).withMessageNo(TMSMessageCodes.TARGET_BLOCKED_MSG).build());
                LOGGER.debug(msg);
            }
        }
    }

    protected abstract boolean isTargetAvailable(T target);

    protected abstract Optional<T> resolveTarget(RedirectVote vote);

    protected abstract void assignTarget(RedirectVote vote);
}
