/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain.helper;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.openwms.common.domain.TransportUnit;
import org.openwms.common.domain.TransportUnitType;

/**
 * 
 * A TransportUnitHelper.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 */
public class TransportUnitHelper {

	private EntityManager em;

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
