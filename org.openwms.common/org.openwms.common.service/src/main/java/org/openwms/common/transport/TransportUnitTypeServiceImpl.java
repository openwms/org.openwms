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
package org.openwms.common.transport;

import java.util.ArrayList;
import java.util.List;

import org.ameba.annotation.TxService;
import org.openwms.common.location.LocationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * A TransportUnitTypeServiceImpl.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @since 0.2
 */
@TxService
class TransportUnitTypeServiceImpl implements TransportUnitTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportUnitTypeServiceImpl.class);

    @Autowired
    private TransportUnitTypeRepository transportUnitTypeRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransportUnitType> findAll() {
        return transportUnitTypeRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransportUnitType create(TransportUnitType transportUnitType) {
        transportUnitTypeRepository.save(transportUnitType);
        return transportUnitTypeRepository.save(transportUnitType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteType(TransportUnitType... transportUnitTypes) {
        for (TransportUnitType transportUnitType : transportUnitTypes) {
            TransportUnitType tut = transportUnitTypeRepository.findByType(transportUnitType.getType()).get();
            if (tut != null) {
                transportUnitTypeRepository.delete(tut);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransportUnitType save(TransportUnitType transportUnitType) {
        TransportUnitType tut = transportUnitTypeRepository.save(transportUnitType);
        LOGGER.debug("Save a TransportUnitType, list of typePlacingRules:" + tut.getTypePlacingRules().size());
        return tut;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransportUnitType updateRules(String type, List<LocationType> newAssigned, List<LocationType> newNotAssigned) {

        TransportUnitType tut = transportUnitTypeRepository.findByType(type).get();
        boolean found = false;
        if (newAssigned != null && !newAssigned.isEmpty()) {
            for (LocationType locationType : newAssigned) {
                for (TypePlacingRule rule : tut.getTypePlacingRules()) {
                    if (rule.getAllowedLocationType() == locationType) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    TypePlacingRule newRule = new TypePlacingRule(tut, locationType);
                    tut.addTypePlacingRule(newRule);
                }
            }
        }

        if (newAssigned != null && !newAssigned.isEmpty()) {
            for (LocationType locationType : newNotAssigned) {
                for (TypePlacingRule rule : tut.getTypePlacingRules()) {
                    if (rule.getAllowedLocationType() == locationType) {
                        tut.removeTypePlacingRule(rule);
                        break;
                    }
                }
            }
        }

        return transportUnitTypeRepository.save(tut);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Rule> loadRules(String transportUnitType) {
        TransportUnitType type = transportUnitTypeRepository.findByType(transportUnitType).get();
        List<Rule> rules = new ArrayList<>();
        if (type != null) {
            LOGGER.debug("Found type " + type);
            rules.addAll(type.getTypePlacingRules());
            rules.addAll(type.getTypeStackingRules());
        }
        LOGGER.debug("returning a list with items" + rules.size());
        return rules;
    }
}