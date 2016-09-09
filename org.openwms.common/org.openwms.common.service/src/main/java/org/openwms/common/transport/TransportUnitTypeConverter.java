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

import org.ameba.exception.NotFoundException;
import org.ameba.i18n.Translator;
import org.dozer.DozerConverter;
import org.openwms.common.CommonMessageCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * A TransportUnitTypeConverter.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Configurable
public class TransportUnitTypeConverter extends DozerConverter<String, TransportUnitType> {

    @Autowired
    private TransportUnitTypeRepository repository;
    @Autowired
    private Translator translator;

    /**
     * {@inheritDoc}
     */
    public TransportUnitTypeConverter() {
        super(String.class, TransportUnitType.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransportUnitType convertTo(String source, TransportUnitType destination) {
        return repository.findByType(source).orElseThrow(() -> new NotFoundException(translator, CommonMessageCodes.TRANSPORT_UNIT_TYPE_NOT_FOUND, new String[]{source}, source));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String convertFrom(TransportUnitType source, String destination) {
        return source.getType();
    }
}
