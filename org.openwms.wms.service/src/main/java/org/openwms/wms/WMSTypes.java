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
package org.openwms.wms;

import java.io.Serializable;

/**
 * A WMSTypes.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public final class WMSTypes implements Serializable {

    /**
     * Hide constructor.
     */
    private WMSTypes() {}

    /**
     * Length of the orderId field.
     * 
     * @see org.openwms.wms.order.AbstractOrder
     */
    public static final int ORDER_ID_LENGTH = 20;
    /**
     * Length of the positionNo field.
     * 
     * @see org.openwms.wms.order.OrderPositionKey
     */
    public static final int POSITION_NO_LENGTH = 8;
}
