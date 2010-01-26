/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.integration.jpa;

import java.util.List;

import org.openwms.common.domain.Location;
import org.openwms.common.integration.LocationDao;
import org.springframework.transaction.annotation.Transactional;

/**
 * A LocationDaoImpl.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class LocationDaoImpl extends AbstractGenericJpaDao<Location, Long> implements LocationDao {

    @Override
    protected String getFindAllQuery() {
        return Location.NQ_FIND_ALL;
    }

    @Override
    protected String getFindByUniqueIdQuery() {
        return Location.NQ_FIND_BY_UNIQUE_QUERY;
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Location> getAllLocations() {
        logger.debug("getAllLocations in Dao called");
        List<Location> list = getEm().createNamedQuery(Location.NQ_FIND_ALL_EAGER).getResultList();
        for (Location location : list) {
            location.getLocationType();
        }
        for (Location location : list) {
            logger.debug(location.getDescription());
        }
        return list;
    }

}
