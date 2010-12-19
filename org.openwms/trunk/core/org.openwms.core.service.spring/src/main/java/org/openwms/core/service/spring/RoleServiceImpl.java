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

import org.openwms.core.domain.system.usermanagement.Role;
import org.openwms.core.integration.system.usermanagement.RoleDao;
import org.openwms.core.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A RoleServiceImpl.
 * 
 * @author <a href="mailto:scherrer@users.sourceforge.net">Heiko Scherrer</a>
 * @version $Revision$
 * @see org.openwms.core.integration.system.usermanagement.RoleDao;
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected RoleDao dao;

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(List<Role> roles) {
        if (roles == null) {
            logger.debug("Nothing to remove");
            return;
        }
        for (Role r : roles) {
            Role role = dao.findById(r.getId());
            dao.remove(role);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role save(Role role) {
        return dao.save(role);
    }

    /**
     * {@inheritDoc}
     * 
     * Marked as <code>readOnly</code> transactional method.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return dao.findAll();
    }

}
