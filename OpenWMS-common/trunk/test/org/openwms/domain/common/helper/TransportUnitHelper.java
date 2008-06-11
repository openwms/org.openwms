/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common.helper;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openwms.domain.common.TransportUnit;
import org.openwms.domain.common.TransportUnitType;

public class TransportUnitHelper {

	private EntityManager em;
	protected Log LOG = LogFactory.getLog(this.getClass());

	public TransportUnitHelper(EntityManager em) {
		this.em = em;
	}

	public TransportUnit createTransportUnit(String tut, String tuId) {
		TransportUnitType transportUnitType = new TransportUnitType(tut);
		TransportUnit transportUnit = new TransportUnit(tuId);
		transportUnit.setTransportUnitType(transportUnitType);
		EntityTransaction entityTransaction = em.getTransaction();
		entityTransaction.begin();
		em.persist(transportUnit);
		entityTransaction.commit();
		return transportUnit;
	}

}
