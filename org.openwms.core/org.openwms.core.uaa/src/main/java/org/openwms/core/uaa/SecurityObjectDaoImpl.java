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
package org.openwms.core.uaa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import org.openwms.core.AbstractGenericJpaDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * A SecurityDaoImpl is a JPA implementation that is used as a repository to find, delete and save {@link SecurityObject}s. It can be
 * injected by name {@value #COMPONENT_NAME}. <p> All methods have to be invoked within an active transaction context. </p>
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Transactional(propagation = Propagation.MANDATORY)
@Repository(SecurityObjectDaoImpl.COMPONENT_NAME)
public class SecurityObjectDaoImpl extends AbstractGenericJpaDao<SecurityObject, Long> implements SecurityObjectRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * Springs component name.
     */
    public static final String COMPONENT_NAME = "securityObjectDao";

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(List<Grant> grants) {
        for (Grant grant : grants) {
            em.remove(em.merge(grant));
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Grant> findAllOfModule(String moduleName) {
        return em.createQuery("select g from Grant g where g.name like :moduleName")
                .setParameter("moduleName", moduleName).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SecurityObject merge(SecurityObject entity) {
        return em.merge(entity);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SecurityObject> findAll() {
        return em.createQuery("select g from Grant g").getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<SecurityObject> getPersistentClass() {
        return SecurityObject.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getFindAllQuery() {
        return SecurityObject.NQ_FIND_ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getFindByUniqueIdQuery() {
        return SecurityObject.NQ_FIND_BY_UNIQUE_QUERY;
    }
}