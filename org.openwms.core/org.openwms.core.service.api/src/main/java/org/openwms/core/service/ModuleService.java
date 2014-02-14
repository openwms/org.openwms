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
package org.openwms.core.service;

import java.util.List;

import org.openwms.core.domain.Module;

/**
 * A ModuleService offers functionality of common {@link Module} management tasks. Handling {@link Module}s is an essential functionality of
 * the CORE openwms.org subproject. {@link Module}s can be created, saved, loaded or unloaded.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.domain.Module
 */
public interface ModuleService {

    /**
     * Return a list of all existing {@link Module}s.
     * 
     * @return A list of {@link Module}s or an empty list when no {@link Module} s exist
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
     * Remove an already existing {@link Module}.
     * 
     * @param module
     *            {@link Module} to be removed
     */
    void remove(Module module);

    /**
     * Save the <code>startupOrder</code> for a list of {@link Module}s. The <code>startupOrder</code> of all {@link Module}s in the list
     * <code>modules</code> has to be calculated before.
     * 
     * @param modules
     *            The list of {@link Module}s to be saved
     */
    void saveStartupOrder(List<Module> modules);
}