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
package org.openwms.tms.service.impl;

import org.openwms.common.domain.TransportUnit;

/**
 * A TransportOrderStateDelegate.
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision$
 * @since 0.1
 */
public interface TransportOrderStateDelegate {

    /**
     * An actions that shall be triggered after a TransportOrder has bees
     * created.
     * 
     * @param transportUnit
     *            The TransportUnit for that the order was started.
     */
    void afterCreation(TransportUnit transportUnit);

    void onCancel(Long id);

    void afterFinish(Long id);

    void onFailure(Long id);

    void onInterrupt(Long id);
}
