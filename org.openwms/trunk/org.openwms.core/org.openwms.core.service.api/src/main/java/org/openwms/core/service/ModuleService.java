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
package org.openwms.core.service;

import java.util.List;

import org.openwms.core.domain.Module;

/**
 * A ModuleService offers some functionality for common module management tasks.
 * Handling modules is an essential functionality of the CORE openwms.org
 * project.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public interface ModuleService {

    /**
     * Return a list of all persisted {@link Module}s.
     * 
     * @return A list of {@link Module}s or an empty list when no
     *         {@link Module}s exist
     */
    List<Module> findAll();

    /**
     * Save a {@link Module}.
     * 
     * @param module
     *            {@link Module} instance to be saved
     * @return The saved {@link Module} instance
     */
    Module save(Module module);

    /**
     * Removes a persistent {@link Module} instance.
     * 
     * @param module
     *            {@link Module} instance to be removed
     */
    void remove(Module module);

    /**
     * Force a login. Call this method and try to access the security filter
     * chain. The implementation does not need to execute any logic.
     */
    void login();

    /**
     * For a list of {@link Module}s the startupOrder is saved. The
     * startupOrder property of all {@link Module}s in the list must already be
     * calculated and saved before.
     * 
     * @param modules
     *            The list of {@link Module}s to be modified
     */
    void saveStartupOrder(List<Module> modules);

}
