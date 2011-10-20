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
package org.openwms.core.integration.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.openwms.core.domain.system.usermanagement.Grant;
import org.openwms.core.domain.system.usermanagement.SecurityObject;
import org.openwms.core.integration.SecurityObjectDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * A SecurityDaoImpl.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Transactional
@Repository("securityObjectDao")
public class SecurityObjectDaoImpl implements SecurityObjectDao {

    @PersistenceContext
    private EntityManager em;

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.integration.SecurityObjectDao#delete(java.util.List)
     */
    @Override
    public void delete(List<Grant> grants) {
        for (Grant grant : grants) {
            em.remove(em.merge(grant));
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.integration.SecurityObjectDao#findAll()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SecurityObject> findAll() {
        return em.createQuery("select g from Grant g").getResultList();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.integration.SecurityObjectDao#findAllOfModule(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Grant> findAllOfModule(String moduleName) {
        return em.createQuery("select g from Grant g where g.name like :moduleName")
                .setParameter("moduleName", moduleName).getResultList();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.integration.SecurityObjectDao#merge(org.openwms.core.domain.system.usermanagement.SecurityObject)
     */
    @Override
    public SecurityObject merge(SecurityObject entity) {
        return em.merge(entity);
    }

}
