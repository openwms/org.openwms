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
package org.openwms.core.configuration;

import java.io.Serializable;
import java.util.Arrays;

/**
 * A PreferenceKey can be used as a unique key object to group preference instances. Unfortunately this class cannot be implemented as a JPA
 * embeddable, because of JPA does not support inheritance of embeddables.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.2
 * @since 0.1
 */
public class PreferenceKey implements Serializable {

    private Serializable[] fields;

    /**
     * Create a new {@code PreferenceKey} with a variable array of fields.
     * 
     * @param fields
     *            The array of fields to store as keys
     */
    public PreferenceKey(Serializable... fields) {
        this.fields = fields;
    }

    /**
     * {@inheritDoc}
     * 
     * Use of all fields for calculation of the hashCode.
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(fields);
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * Use all fields for comparison.
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PreferenceKey other = (PreferenceKey) obj;
        if (!Arrays.equals(fields, other.fields)) {
            return false;
        }
        return true;
    }
}