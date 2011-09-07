/*
 * openwms.org, the Open Warehouse Management System.
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.core.domain.system;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * An ApplicationPreference. Used to store settings in application scope.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Entity
@DiscriminatorValue("APPLICATION")
@Table(name = "COR_APP_PREFERENCE")
@NamedQueries({ @NamedQuery(name = ApplicationPreference.NQ_FIND_ALL, query = "SELECT ap FROM ApplicationPreference ap") })
public class ApplicationPreference extends AbstractPreference {

    private static final long serialVersionUID = -2942285512161603092L;
    public static final String OWNER = "Application";

    /**
     * Query to find all <code>ApplicationPreference</code>s.
     */
    public static final String NQ_FIND_ALL = "ApplicationPreference" + FIND_ALL;

    /**
     * Create a new ApplicationPreference.
     * 
     */
    protected ApplicationPreference() {
        super();
        super.setOwner(OWNER);
    }

    /**
     * Create a new ApplicationPreference.
     * 
     * @param key
     */
    public ApplicationPreference(String key) {
        super(key);
        super.setOwner(OWNER);
    }

}
