/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.tms;

import javax.validation.Validator;

import org.openwms.common.CommonGateway;
import org.openwms.common.TransportUnit;
import org.openwms.tms.validation.ChangeTUValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A ChangeTU is responsible to change a {@link TransportOrder}s assigned {@code TransportUnit}.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @since 1.0
 */
@Component
class ChangeTU implements UpdateFunction {

    @Autowired
    private CommonGateway gateway;
    @Autowired
    private Validator validator;

    /**
     * {@inheritDoc}
     * <p>
     * If the assigned {@code TransportUnitBK} has changed, we're going to re-assign the {code TransportUnit}s.
     */
    @Override
    public void update(TransportOrder saved, TransportOrder toUpdate) {
        validateAttributes(toUpdate);
        if (!saved.getTransportUnitBK().equalsIgnoreCase(toUpdate.getTransportUnitBK())) {

            // change the target of the TU to assign
            TransportUnit savedTU = new TransportUnit();
            savedTU.setBarcode(toUpdate.getTransportUnitBK());
            savedTU.setTarget(toUpdate.getTargetLocationGroup());
            gateway.updateTransportUnit(savedTU);

            // clear target of an formerly assigned TU
            savedTU.setBarcode(saved.getTransportUnitBK());
            savedTU.clearTarget();
            gateway.updateTransportUnit(savedTU);

            saved.setTransportUnitBK(toUpdate.getTransportUnitBK());
        }
    }

    private void validateAttributes(TransportOrder to) {
        validator.validate(to, ChangeTUValidation.class);
    }
}
