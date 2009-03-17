/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao.core;

import org.openwms.common.dao.TransportUnitDao;
import org.openwms.common.domain.TransportUnit;

/**
 * A TransportUnitDAO.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class TransportUnitDAOImpl extends AbstractGenericJpaDAO<TransportUnit, Long> implements TransportUnitDao {

    @Override
    String getFindAllQuery() {
	return TransportUnitDao.NQ_FIND_ALL;
    }

    @Override
    String getFindByUniqueIdQuery() {
	return TransportUnitDao.NQ_FIND_BY_UNIQUE_QUERY;
    }

    // TODO: Provide other TransportUnit based non-CRUD operations here!
}
