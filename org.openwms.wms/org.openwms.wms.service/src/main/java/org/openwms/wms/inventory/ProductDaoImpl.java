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
package org.openwms.wms.inventory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * A ProductDaoImpl.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Transactional(propagation = Propagation.MANDATORY)
@Repository(ProductDaoImpl.COMPONENT_NAME)
public class ProductDaoImpl implements ProductDao {

    /** Springs component name. */
    public static final String COMPONENT_NAME = "productDao";

    @PersistenceContext
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Product> T save(T entity) {
        return em.merge(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Product> void remove(T entity) {
        em.remove(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Product> void persist(T entity) {
        em.persist(entity);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Product> T findByProductId(String productId) {
        Query query = em.createNamedQuery(Product.NQ_FIND_SKU);
        query.setParameter(Product.QP_FIND_PRODUCT_SKU, productId);
        return (T) query.getSingleResult();
    }
}