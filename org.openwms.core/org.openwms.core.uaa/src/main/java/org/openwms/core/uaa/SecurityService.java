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

import org.openwms.core.GenericEntityService;
import org.openwms.core.system.usermanagement.Grant;
import org.openwms.core.system.usermanagement.SecurityObject;

/**
 * A SecurityService defines functionality to handle <code>SecurityObject</code>s, especially <code>Grant</code>s.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public interface SecurityService extends GenericEntityService<SecurityObject, Long, String> {

    /**
     * Merge a list of persisted, detached or transient {@link Grant}s of a particular <code>Module</code>.
     * 
     * @param moduleName
     *            The moduleName
     * @param grants
     *            The list of {@link Grant}s to merge
     * @return All existing {@link Grant}s
     */
    List<Grant> mergeGrants(String moduleName, List<Grant> grants);

    /**
     * Force a login. Call this method to access the security filter chain. The implementation does not need to execute anything.
     */
    void login();
}