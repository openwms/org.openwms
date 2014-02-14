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
package org.openwms.common.comm.api;

/**
 * A MessageMapper is able to map from a String telegram to a {@link CommonMessage}.
 * 
 * @param <T>
 *            A type of incoming CommonMessage
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
public interface MessageMapper<T extends CommonMessage> {

    /**
     * Investigate the telegram String <tt>telegram</tt> and retrieve from the telegram type a subtype of {@link CommonMessage}.
     * Implementations probably throw some kind of RuntimeExceptions if no telegram type was found.
     * 
     * @param telegram
     *            The telegram String to investigate
     * @return The {@link CommonMessage}
     */
    T mapTo(String telegram);

    /**
     * Return the telegram type, this mapper is responsible for.
     * 
     * @return the telegram type as String
     */
    String forType();
}
