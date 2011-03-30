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
package org.openwms.core.integration.jpa;

import org.openwms.core.domain.Module;
import org.openwms.core.domain.system.usermanagement.Role;
import org.openwms.core.integration.ModuleDao;
import org.springframework.stereotype.Repository;

/**
 * A ModuleDao is an extension of a {@link AbstractGenericJpaDao} about
 * functionality regarding {@link Role}s. The stereotype annotation
 * {@link Repository} marks this class as DAO in the architecture and enables
 * exception translation and component scanning.
 * <p>
 * So far there is no implementation, just to be compliant.
 * </p>
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 * @see org.openwms.core.integration.jpa.AbstractGenericJpaDao
 * @see org.openwms.core.integration.ModuleDao
 */
@Repository("moduleDao")
public class ModuleDaoImpl extends AbstractGenericJpaDao<Module, Long> implements ModuleDao {

    /**
     * @see org.openwms.core.integration.jpa.AbstractGenericJpaDao#getFindAllQuery()
     */
    @Override
    protected String getFindAllQuery() {
        return Module.NQ_FIND_ALL;
    }

    /**
     * @see org.openwms.core.integration.jpa.AbstractGenericJpaDao#getFindByUniqueIdQuery()
     */
    @Override
    protected String getFindByUniqueIdQuery() {
        return Module.NQ_FIND_BY_UNIQUE_QUERY;
    }

}
