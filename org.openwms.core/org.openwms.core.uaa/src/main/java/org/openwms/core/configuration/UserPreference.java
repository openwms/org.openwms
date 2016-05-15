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

/**
 * A UserPreference encapsulates all preferences dedicated to an {@code User}.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
public class UserPreference {

    private String username;
    private String key;
    private Serializable value;

    /**
     * At least the users name must be present.
     *
     * @param username The users identifiable name
     */
    public UserPreference(String username) {
        this.username = username;
    }

    public UserPreference(String username, String key, Serializable value) {
        this.username = username;
        this.key = key;
        this.value = value;
    }

    public String getUsername() {
        return username;
    }

    public String getKey() {
        return key;
    }

    public Serializable getValue() {
        return value;
    }
}
