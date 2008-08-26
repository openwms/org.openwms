/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service.transport;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openwms.common.dao.IEntityDao;
import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.TransportUnit;
import org.openwms.common.domain.values.Barcode;
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

    private IEntityDao<Serializable> entityDao;

    public TransportService(IEntityDao<Serializable> entityDao) {
	this.entityDao = entityDao;
    }

    @Transactional
    public void moveTransportUnit(Barcode barcode, LocationPK locationPk) {
	Map<String, String> params = new HashMap<String, String>();
	params.put("id", barcode.getId());
	List<Serializable> transportUnits = entityDao.find("findTUByBarcodeString", params);

    }

}
