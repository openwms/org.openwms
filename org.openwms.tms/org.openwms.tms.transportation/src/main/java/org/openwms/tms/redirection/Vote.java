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

import java.util.ArrayList;
import java.util.List;

import org.openwms.tms.Message;

/**
 * A Vote stores all information used by {@link DecisionVoter}s to vote for or against an action that shall be executed. Acts as a
 * superclass for certain votes.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @since 1.0
 */
public class Vote {

    private List<Message> messages = new ArrayList<>();
    private boolean completed = false;

    public boolean addMessage(Message message) {
        return messages.add(message);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public boolean hasMessages() {
        return messages != null && !messages.isEmpty();
    }

    public void complete() {
        this.completed = true;
    }

    public boolean completed() {
        return completed;
    }
}