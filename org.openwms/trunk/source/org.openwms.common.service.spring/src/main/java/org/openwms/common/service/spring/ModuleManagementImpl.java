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
package org.openwms.common.service.spring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openwms.common.domain.MenuItem;
import org.openwms.common.domain.Module;
import org.openwms.common.domain.PopupItem;
import org.openwms.common.service.ModuleManagementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A ModuleManagementImpl.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: $
 * 
 */
@Service
@Transactional
public class ModuleManagementImpl extends EntityServiceImpl<Module, Long> implements ModuleManagementService<Module> {

    /**
     * @see org.openwms.common.service.ModuleManagementService#getMenuItems()
     */
    @Override
    public List<MenuItem> getMenuItems(Module module) {
        Module m = dao.save(module);
        return m.getMenuItems();
    }

    /**
     * @see org.openwms.common.service.ModuleManagementService#getModules()
     */
    @Override
    @Transactional(readOnly = true)
    public List<Module> getModules() {
        return dao.findAll();
    }

    /**
     * @see org.openwms.common.service.ModuleManagementService#getPopupItems()
     */
    @Override
    @Transactional(readOnly = true)
    public List<PopupItem> getPopupItems(Module module) {
        Map<String, String> params = new HashMap<String, String>();
        dao.findByQuery("jjj", params);
        // TODO [scherrer] Auto-generated method stub
        return null;
    }

}
