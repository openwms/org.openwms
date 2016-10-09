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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

import org.ameba.integration.jpa.ApplicationEntity;
import org.openwms.common.LocationEO;

/**
 * A Route.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Entity
@Table(name = "RSRV_ROUTE")
public class Route extends ApplicationEntity implements Serializable {

    /** For TransportUnits without active TransportOrder. */
    public static final Route NO_ROUTE = new Route("_NO_ROUTE");
    /** For all TransportOrders with no explicitly defined Route. */
    public static final Route DEF_ROUTE = new Route("_DEFAULT");
    @NotNull
    @Column(name = "C_NAME")
    private String routeId;
    @Column(name = "C_DESCRIPTION")
    private String description;
    @ManyToOne
    @JoinColumn(name = "C_SOURCE_LOCATION", referencedColumnName = "C_PK")
    private LocationEO sourceLocation;
    @ManyToOne
    @JoinColumn(name = "C_TARGET_LOCATION", referencedColumnName = "C_PK")
    private LocationEO targetLocation;
    @Column(name = "C_SOURCE_LOC_GROUP_NAME")
    private String sourceLocationGroupName;
    @Column(name = "C_TARGET_LOC_GROUP_NAME")
    private String targetLocationGroupName;
    @Column(name = "C_ENABLED")
    private boolean enabled = true;

    /** Dear JPA ... */
    protected Route() {
    }

    public Route(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteId() {
        return routeId;
    }

    public static Route of(String routeId) {
        if (routeId == null || routeId.isEmpty()) return DEF_ROUTE;
        return new Route(routeId);
    }

    @Override
    public String toString() {
        return routeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(routeId, route.routeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeId);
    }
}
