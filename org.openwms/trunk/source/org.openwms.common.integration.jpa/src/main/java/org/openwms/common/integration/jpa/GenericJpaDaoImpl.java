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
package org.openwms.common.integration.jpa;

import java.io.Serializable;

import org.openwms.common.integration.GenericDao;

/**
 * A GenericJpaDaoImpl.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see {@link org.openwms.common.integration.GenericDao}
 * @see {@link org.openwms.common.integration.jpa.AbstractGenericJpaDao}
 */
public class GenericJpaDaoImpl<T extends Serializable, ID extends Serializable> extends AbstractGenericJpaDao<T, ID>
        implements GenericDao<T, ID> {

    /**
     * Concatenates the simple class name of the persistent class with the
     * default prefix {@link org.openwms.common.integration.FIND_ALL}.
     * 
     * @return Name of the query
     * @see org.openwms.common.integration.jpa.AbstractGenericJpaDao#getFindAllQuery()
     */
    @Override
    protected String getFindAllQuery() {
        return getPersistentClass().getSimpleName().concat(FIND_ALL);
    }

    /**
     * Concatenates the simple class name of the persistent class with the
     * default prefix {@link org.openwms.common.integration.FIND_BY_ID}.
     * 
     * @return Name of the query
     * @see org.openwms.common.integration.jpa.AbstractGenericJpaDao#getFindByUniqueIdQuery()
     */
    @Override
    protected String getFindByUniqueIdQuery() {
        return getPersistentClass().getSimpleName().concat(FIND_BY_ID);
    }

}