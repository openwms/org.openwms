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
package org.openwms.core.domain.system.usermanagement;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.openwms.core.util.validation.AssertUtils;

/**
 * A Grant.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: 1364 $
 * @since 0.1
 */
@Entity
@DiscriminatorValue("GRANT")
public class Grant extends SecurityObject {

    private static final long serialVersionUID = 2061059874657176727L;

    /**
     * Create a new Grant.
     * 
     * @param moduleName
     *            The name of module where the grant belong to
     * @param grantName
     *            The name of the grant
     */
    public Grant(String moduleName, String grantName) {
        super();
        AssertUtils.isNotEmpty(moduleName, "An empty or null moduleName is not allowed");
        super.setName(moduleName.toUpperCase() + grantName);
    }

}
