/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao.core;

import java.io.Serializable;

import org.openwms.common.dao.GenericDAO;

/**
 * A GenericJpaDAOImpl.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class GenericJpaDAOImpl<T extends Serializable, ID extends Serializable> extends AbstractGenericJpaDAO<T, ID>
	implements GenericDAO<T, ID> {

    @Override
    String getFindAllQuery() {
	// FIXME getFindAllQuery
	return " ";
    }

    @Override
    String getFindAllUniqueQuery() {
	// FIXME getFindAllUniqueQuery
	return " ";
    }

}
