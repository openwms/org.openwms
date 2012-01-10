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
package org.openwms.common.integration.jpa;

import java.util.Date;

import org.openwms.common.domain.LocationGroup;
import org.openwms.common.integration.LocationGroupDao;
import org.openwms.core.integration.jpa.AbstractGenericJpaDao;
import org.springframework.stereotype.Repository;

/**
 * A LocationGroupDaoImpl.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.integration.jpa.AbstractGenericJpaDao
 * @see org.openwms.common.integration.LocationGroupDao
 */
@Repository("locationGroupDao")
public class LocationGroupDaoImpl extends AbstractGenericJpaDao<LocationGroup, Long> implements LocationGroupDao {

    /**
     * @return Name of the query
     * @see org.openwms.core.integration.jpa.AbstractGenericJpaDao#getFindAllQuery()
     */
    @Override
    protected String getFindAllQuery() {
        return LocationGroupDao.NQ_FIND_ALL;
    }

    /**
     * @return Name of the query
     * @see org.openwms.core.integration.jpa.AbstractGenericJpaDao#getFindByUniqueIdQuery()
     */
    @Override
    protected String getFindByUniqueIdQuery() {
        return LocationGroupDao.NQ_FIND_BY_NAME;
    }

    /**
     * Update the date of last change before update.
     * 
     * @param entity
     *            LocationGroup to be updated
     */
    @Override
    protected void beforeUpdate(LocationGroup entity) {
        entity.setLastUpdated(new Date());
    }

}
