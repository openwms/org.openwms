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
package org.openwms.common.service.spring;

import java.io.Serializable;
import java.util.List;

import org.openwms.common.integration.GenericDao;
import org.openwms.common.service.EntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

/**
 * A EntityServiceImpl.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 314 $
 * @since 0.1
 */
@Transactional
public class EntityServiceImpl<T extends Serializable, ID extends Serializable> implements EntityService<T> {

    /**
     * Generic Repository DAO.
     */
    protected GenericDao<T, ID> dao;

    /**
     * Logger instance can be used by subclasses.
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * FIXME [scherrer] Comment this
     * 
     * @param dao
     */
    @Required
    public void setDao(GenericDao<T, ID> dao) {
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        logger.debug("findAll called");
        return dao.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<T> findAll(Class<T> clazz) {
        logger.debug("findAll(clazz) called");
        dao.setPersistentClass(clazz);
        return dao.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T save(Class<T> clazz, T entity) {
        logger.debug("save called");
        dao.setPersistentClass(clazz);
        return dao.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(Class<T> clazz, T entity) {
        logger.debug("remove called");
        dao.setPersistentClass(clazz);
        // dao.save(entity);
        dao.remove(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEntity(T newEntity) {
        // FIXME [scherrer]: All entities shall extend a superclass Entity with
        // isNew()
        // method, to check this here
        dao.persist(newEntity);
    }
}