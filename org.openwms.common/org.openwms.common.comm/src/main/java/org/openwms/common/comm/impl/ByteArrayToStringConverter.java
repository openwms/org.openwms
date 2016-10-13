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
package org.openwms.common.comm.impl;

import java.io.UnsupportedEncodingException;

import org.springframework.core.convert.converter.Converter;
import org.springframework.integration.config.IntegrationConverter;
import org.springframework.stereotype.Component;

/**
 * A ByteArrayToStringConverter is a simple byte array to String converter; allowing the character set to be specified.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@IntegrationConverter
@Component
class ByteArrayToStringConverter implements Converter<byte[], String> {

    private String charSet = "UTF-8";

    /**
     * {@inheritDoc}
     */
    @Override
    public String convert(byte[] bytes) {
        try {
            return new String(bytes, this.charSet);
        } catch (UnsupportedEncodingException e) {
            return new String(bytes);
        }
    }

    /**
     * @return the charSet
     */
    public String getCharSet() {
        return charSet;
    }

    /**
     * @param charSet
     *            the charSet to set
     */
    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }
}
