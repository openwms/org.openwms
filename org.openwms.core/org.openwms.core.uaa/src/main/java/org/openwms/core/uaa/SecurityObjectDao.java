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
package org.openwms.core.uaa;

import java.util.List;

import org.openwms.core.GenericDao;
import org.openwms.core.system.usermanagement.Grant;
import org.openwms.core.system.usermanagement.SecurityObject;

/**
 * A SecurityDao is used to find, modify and delete {@link SecurityObject}s.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public interface SecurityObjectDao extends GenericDao< SecurityObject, Long> {

    /**
     * Find and retrieve all {@link SecurityObject}s that belong to a given <code>Module</code>.
     * 
     * @param moduleName
     *            The name of the <code>Module</code>
     * @return a list of {@link SecurityObject}s. <code>null</code> might be possible as well, see the particular implementation
     */
    List<Grant> findAllOfModule(String moduleName);

    /**
     * Save a {@link SecurityObject} and return the updated instance.
     * 
     * @param entity
     *            the {@link SecurityObject} to save
     * @return the saved instance
     */
    SecurityObject merge(SecurityObject entity);

    /**
     * Delete a list of {@link Grant}s.
     * 
     * @param grants
     *            the {@link Grant}s to delete
     */
    void delete(List<Grant> grants);
}