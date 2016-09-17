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
package org.openwms.tms;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

import org.ameba.integration.jpa.BaseEntity;

/**
 * A ProblemHistory stores an occurred problem, in form of {@code Message}, recorded on {@code TransportOrder}.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @since 1.0
 */
@Entity
public class ProblemHistory extends BaseEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "C_FK_TO")
    private TransportOrder transportOrder;
    @JoinColumn(name = "C_FK_MSG")
    private Message problem;

    /** Dear JPA ... */
    protected ProblemHistory() {
    }

    /**
     * Full constructor.
     *
     * @param transportOrder The TO this problem initially occurred
     * @param problem The problem itself
     */
    public ProblemHistory(TransportOrder transportOrder, Message problem) {
        this.transportOrder = transportOrder;
        this.problem = problem;
    }

    /**
     * Get the problem.
     *
     * @return The problem
     */
    public Message getProblem() {
        return problem;
    }

    /**
     * Get the corresponding {@code TransportOrder}.
     *
     * @return The transportOrder
     */
    public TransportOrder getTransportOrder() {
        return transportOrder;
    }
}
