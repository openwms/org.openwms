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
package org.openwms.core.service.spring;

import java.util.List;

import org.openwms.core.domain.system.usermanagement.Preference;
import org.openwms.core.domain.values.Unit;
import org.openwms.core.service.ConfigurationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A ConfigurationServiceImpl.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Transactional
@Service
public class ConfigurationServiceImpl extends EntityServiceImpl<Preference, Long> implements
        ConfigurationService<Preference> {

    /**
     * @see org.openwms.core.service.ConfigurationService#findApplicationProperties()
     */
    @Override
    public List<Preference> findApplicationProperties() {
        return null;
    }

    @Override
    public List<Preference> findModuleProperties() {
        // TODO [scherrer] Auto-generated method stub
        return null;
    }

    /**
     * @see org.openwms.core.service.ConfigurationService#getAllUnits()
     */
    @Override
    public List<? extends Unit> getAllUnits() {
        // TODO [scherrer] Auto-generated method stub
        return null;
    }
}
