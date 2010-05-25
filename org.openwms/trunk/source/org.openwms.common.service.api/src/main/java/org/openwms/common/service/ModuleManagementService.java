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
package org.openwms.common.service;

import java.util.List;

import org.openwms.common.domain.MenuItem;
import org.openwms.common.domain.Module;
import org.openwms.common.domain.PopupItem;

/**
 * A ModuleManagementService.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: $
 * @see 0.1
 */
public interface ModuleManagementService<T extends Module> extends EntityService<Module> {

    /**
     * Return a List of all MenuItems. MenuItems are used to populate the main
     * menu bar in the application. They can be persisted or loaded from the swf
     * files directly.
     * 
     * @param module
     *            The module to search for.
     * @return A List of all MenuItems belonging to the module.
     */
    List<MenuItem> getMenuItems(Module module);

    /**
     * Return a List of PopupItems. PopupItems are used to populate the context
     * menu.
     * 
     * @param module
     *            The module to search for.
     * @return A List of all PopupItems belonging to the module.
     */
    List<PopupItem> getPopupItems(Module module);

    /**
     * Return a List of all persisted modules.
     * 
     * @return A List of modules.
     */
    List<Module> getModules();

    /**
     * Force a login. Call this method and try to access the security filter
     * chain. The implementation does not need to execute any logic.
     */
    void login();

}
