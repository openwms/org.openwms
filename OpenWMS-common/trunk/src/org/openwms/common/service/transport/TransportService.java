/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service.transport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openwms.common.dao.IGenericDAO;
import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.TransportUnit;
import org.openwms.common.domain.values.Barcode;
import org.openwms.common.exception.service.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A TransportService.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
@Service
public class TransportService {

    private IGenericDAO<TransportUnit, Barcode> entityDao;

    public TransportService(IGenericDAO<TransportUnit, Barcode> entityDao) {
	this.entityDao = entityDao;
    }

    /**
     * Moves a <tt>TransportUnit</tt> identified by its <tt>Barcode</tt> to the given actual <tt>Location</tt>
     * identified by the <tt>LocationPK</tt>.
     * 
     * @param barcode
     * @param locationPk
     */
    @Transactional
    public void moveTransportUnit(Barcode barcode, LocationPK locationPk) {
	Map<String, String> params = new HashMap<String, String>();
	params.put("id", barcode.getId());
	List<TransportUnit> transportUnits = entityDao.findByQuery("findTUByBarcodeString", params);
	// FIXME: Provide a findUniqueResult method in DAO to prevent result checks in service method
	if (transportUnits.size() != 1) {
	    throw new ServiceException("Excepted a unique query result while searching a TransportUnit by Barcode");
	}

    }

}
