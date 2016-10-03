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
package org.openwms.tms.routing;

import org.openwms.common.Location;
import org.openwms.common.LocationGroupVO;

/**
 * A Matrix.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public interface Matrix {

    /**
     * Find and return an {@code Action}.
     *
     * @param actionType The type of action is often the type of triggering event (REQ_, SYSU, etc.)
     * @param route The {@code TransportOrder}s {@code Route}
     * @param location The actual {@code Location}
     * @param locationGroup The corresponding actual {@code LocationGroup}
     * @return The Action
     * @throws org.ameba.exception.NotFoundException in case no Action was found
     */
    Action findBy(String actionType, Route route, Location location, LocationGroupVO locationGroup);
}
