/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao.core;

import org.openwms.common.dao.TransportUnitTypeDao;
import org.openwms.common.domain.TransportUnitType;

/**
 * A TransportUnitTypeDaoImpl.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class TransportUnitTypeDaoImpl extends AbstractGenericJpaDAO<TransportUnitType, String> implements
	TransportUnitTypeDao {

    @Override
    String getFindAllQuery() {
	return TransportUnitTypeDao.NQ_FIND_ALL;
    }

    @Override
    String getFindByUniqueIdQuery() {
	return TransportUnitTypeDao.NQ_FIND_BY_NAME;
    }

}
