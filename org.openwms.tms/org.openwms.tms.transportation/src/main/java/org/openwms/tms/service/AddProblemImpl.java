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
package org.openwms.tms.service;

import org.openwms.tms.AddProblem;
import org.openwms.tms.Message;
import org.openwms.tms.ProblemHistory;
import org.openwms.tms.ProblemHistoryRepository;
import org.openwms.tms.TransportOrder;
import org.openwms.tms.UpdateFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * A AddProblemImpl.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @since 1.0
 */
@Transactional(propagation = Propagation.MANDATORY)
@Component
class AddProblemImpl implements UpdateFunction, AddProblem {

    @Autowired
    private ProblemHistoryRepository repository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(TransportOrder saved, TransportOrder toUpdate) {
        if (saved.hasProblem() && toUpdate.hasProblem() && !saved.getProblem().equals(toUpdate.getProblem()) ||
                !saved.hasProblem() && toUpdate.hasProblem()) {

            // A Problem occurred and must be added to the TO ...
            add(toUpdate.getProblem(), saved);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Message problem, TransportOrder transportOrder) {
        if (transportOrder.hasProblem()) {
            repository.save(new ProblemHistory(transportOrder, transportOrder.getProblem()));
        }
        transportOrder.setProblem(problem);
    }
}
