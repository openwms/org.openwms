/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain.helper;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationPK;

/**
 * 
 * A LocationHelper.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
public class LocationHelper {

	private EntityManager em;
	protected Log LOG = LogFactory.getLog(this.getClass());

	public LocationHelper(EntityManager em) {
		this.em = em;
	}

	public Location createLocation(String area, String aisle, String x, String y, String z) {
		LocationPK locationPk = new LocationPK(area, aisle, x, y, z);
		Location location = new Location(locationPk);
		EntityTransaction entityTransaction = em.getTransaction();
		try {
			entityTransaction.begin();
			em.persist(location);
			entityTransaction.commit();
		}
		catch (PersistenceException pe) {
			LOG.debug("OK:Execption while persisting TransportUnit without TransportUnitType");
		}
		return location;
	}

}
