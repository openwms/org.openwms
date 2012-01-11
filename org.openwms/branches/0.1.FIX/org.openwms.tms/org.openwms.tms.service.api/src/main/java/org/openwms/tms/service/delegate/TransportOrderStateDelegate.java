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
package org.openwms.tms.service.delegate;

import org.openwms.common.domain.TransportUnit;

/**
 * A TransportOrderStateDelegate. Is called after state changes on a
 * TransportOrder have been performed.
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision$
 * @since 0.1
 */
public interface TransportOrderStateDelegate {

    /**
     * An action that should be triggered after a TransportOrder has been
     * created.
     * 
     * @param transportUnit
     *            The {@link TransportUnit} of the corresponding TransportOrder
     */
    void afterCreation(TransportUnit transportUnit);

    /**
     * An action that should be triggered after a TransportOrder has been
     * canceled.
     * 
     * @param id
     *            The id of the TransportOrder
     */
    void onCancel(Long id);

    /**
     * An action that should be triggered after a TransportOrder has been
     * finished successfully.
     * 
     * @param id
     *            The id of the TransportOrder
     */
    void afterFinish(Long id);

    /**
     * An action that should be triggered after a TransportOrder has been set on
     * failure.
     * 
     * @param id
     *            The id of the TransportOrder
     */
    void onFailure(Long id);

    /**
     * An action that should be triggered after a TransportOrder has been
     * interrupted.
     * 
     * @param id
     *            The id of the TransportOrder
     */
    void onInterrupt(Long id);
}
