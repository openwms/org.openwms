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
 * Handling modules is an essential functionality of the core openwms.org
 * application.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @see 0.1
 */
public interface ModuleService {

    /**
     * Return a list of all persisted modules.
     * 
     * @return A list of modules or an empty list when no Modules exist
     */
    List<Module> findAll();

    /**
     * Save a Module.
     * 
     * @param module
     *            Module instance to be saved
     * @return The saved Module instance
     */
    Module save(Module module);

    /**
     * Removes a persistent Module instance.
     * 
     * @param module
     *            Module instance to be removed
     */
    void remove(Module module);

    /**
     * Force a login. Call this method and try to access the security filter
     * chain. The implementation does not need to execute any logic.
     */
    void login();

    /**
     * For a list of modules the startupOrder is saved. The startupOrder
     * property of all modules in the list must already be calculated and saved
     * before.
     * 
     * @param modules
     *            The list of modules to be modified
     */
    void saveStartupOrder(List<Module> modules);

}
