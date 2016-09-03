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
package org.openwms.tms.delegate;

/**
 * A TransportOrderStateDelegate is called after state changes on a TransportOrder happen.
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @since 1.0
 */
public interface TransportOrderStateDelegate {

    /**
     * An action that should be triggered after a TransportOrder has been created.
     * 
     * @param pk
     *            The primary key of the created {@code TransportOrder}
     */
    void afterCreation(Long pk);

    /**
     * An action that should be triggered after a TransportOrder has been canceled.
     * 
     * @param id
     *            The id of the TransportOrder
     */
    void onCancel(Long id);

    /**
     * An action that should be triggered after a TransportOrder has been finished successfully.
     * 
     * @param id
     *            The id of the TransportOrder
     */
    void afterFinish(Long id);

    /**
     * An action that should be triggered after a TransportOrder has been set on failure.
     * 
     * @param id
     *            The id of the TransportOrder
     */
    void onFailure(Long id);

    /**
     * An action that should be triggered after a TransportOrder has been interrupted.
     * 
     * @param id
     *            The id of the TransportOrder
     */
    void onInterrupt(Long id);
}