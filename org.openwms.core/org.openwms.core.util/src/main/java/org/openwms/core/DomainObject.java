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
package org.openwms.core;

import java.io.Serializable;

/**
 * A DomainObject, implementation classes offer basic functionality characteristic to all persisted domain objects.
 * <p>
 * Each domain object:
 * <ul>
 * <li>must have a field for optimistic locking purpose</li>
 * <li>must return whether it is a transient or persisted instance</li>
 * <li>must return the technical key value to the caller</li>
 * </ul>
 * </p>
 * 
 * @param <ID>
 *            Type of technical key class
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public interface DomainObject<ID extends Serializable> {

    /**
     * Check whether the instance is a transient or persisted one.
     * 
     * @return <code>true</code> if transient (not persisted before), otherwise <code>false</code>
     */
    boolean isNew();

    /**
     * Return the value of the optimistic locking field.
     * 
     * @return the version number
     */
    long getVersion();

    /**
     * Return the technical key value.
     * 
     * @return The technical, unique key
     */
    ID getId();
}