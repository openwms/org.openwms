/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
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
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.tms.service.spring.voter;

import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationGroup;
import org.openwms.core.service.voter.Vote;

/**
 * A RedirectVote. Encapsulates a targetLocationGroup and a targetLocation to vote for as targets.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public class RedirectVote extends Vote {

    private LocationGroup locationGroup;
    private Location location;

    /**
     * Create a new RedirectVote.
     * 
     * @param targetLocationGroup
     *            The target {@link LocationGroup} to vote for
     * @param targetLocation
     *            The target {@link Location} to vote for
     */
    public RedirectVote(LocationGroup targetLocationGroup, Location targetLocation) {
        this.locationGroup = targetLocationGroup;
        this.location = targetLocation;
    }

    /**
     * Get the location.
     * 
     * @return the location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Get the locationGroup.
     * 
     * @return the locationGroup.
     */
    public LocationGroup getLocationGroup() {
        return locationGroup;
    }
}