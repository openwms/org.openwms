/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao.core;

import java.io.Serializable;

import org.openwms.common.dao.GenericDao;

/**
 * A GenericJpaDaoImpl.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class GenericJpaDaoImpl<T extends Serializable, ID extends Serializable> extends AbstractGenericJpaDao<T, ID>
		implements GenericDao<T, ID> {

	@Override
	protected String getFindAllQuery() {
		return getPersistentClass().getSimpleName().concat(FIND_ALL);
	}

	@Override
	protected String getFindByUniqueIdQuery() {
		return getPersistentClass().getSimpleName().concat(FIND_BY_ID);
	}

}