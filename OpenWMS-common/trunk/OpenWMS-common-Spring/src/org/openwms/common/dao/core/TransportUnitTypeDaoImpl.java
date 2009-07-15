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
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class TransportUnitTypeDaoImpl extends AbstractGenericJpaDao<TransportUnitType, String> implements
		TransportUnitTypeDao {

	@Override
	protected String getFindAllQuery() {
		return TransportUnitTypeDao.NQ_FIND_ALL;
	}

	@Override
	protected String getFindByUniqueIdQuery() {
		return TransportUnitTypeDao.NQ_FIND_BY_NAME;
	}

}
