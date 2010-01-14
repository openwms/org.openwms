/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.integration.jpa;

import javax.annotation.PostConstruct;

import org.openwms.common.domain.TransportUnit;
import org.openwms.common.integration.TransportUnitDao;

/**
 * A TransportUnitDaoImpl.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class TransportUnitDaoImpl extends AbstractGenericJpaDao<TransportUnit, Long> implements TransportUnitDao {

	@PostConstruct
	public void init() {
		logger.debug("TransportUnitDao bean initialized");
	}

	@Override
	protected String getFindAllQuery() {
		return TransportUnitDao.NQ_FIND_ALL;
	}

	@Override
	protected String getFindByUniqueIdQuery() {
		return TransportUnitDao.NQ_FIND_BY_UNIQUE_QUERY;
	}

}
