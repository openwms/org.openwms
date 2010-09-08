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

import java.util.List;

import org.openwms.common.domain.system.usermanagement.SystemUser;
import org.openwms.common.domain.system.usermanagement.User;
import org.openwms.common.integration.system.usermanagement.UserDao;
import org.springframework.stereotype.Repository;

/**
 * An UserDaoImpl.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.common.integration.jpa.AbstractGenericJpaDao
 * @see org.openwms.common.integration.system.usermanagement.UserDao
 */
@Repository
public class UserDaoImpl extends AbstractGenericJpaDao<User, Long> implements UserDao {

    /**
     * @return Name of the query
     * @see org.openwms.common.integration.jpa.AbstractGenericJpaDao#getFindAllQuery()
     */
    @Override
    protected String getFindAllQuery() {
        return UserDao.NQ_FIND_ALL;
    }

    /**
     * @return Name of the query
     * @see org.openwms.common.integration.jpa.AbstractGenericJpaDao#getFindByUniqueIdQuery()
     */
    @Override
    protected String getFindByUniqueIdQuery() {
        return UserDao.NQ_FIND_BY_USERNAME;
    }

    /**
     * @return List of all {@link User}s
     * @see org.openwms.common.integration.jpa.AbstractGenericJpaDao#findAll()
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        return super.findByPositionalParameters(UserDao.NQ_FIND_ALL_ORDERED);
    }

    /**
     * {@inheritDoc}
     * 
     * Is the passed in User object is the SuperUser or <code>null</code> no
     * action is performed.
     */
    @Override
    public void persist(User user) {
        if (isSuperUser(user)) {
            return;
        }
        super.persist(user);
    }

    /**
     * {@inheritDoc}
     * 
     * Is the passed in User object is the SuperUser or <code>null</code> no
     * action is performed.
     */
    @Override
    public User save(User user) {
        if (isSuperUser(user)) {
            return user;
        }
        return super.save(user);
    }

    /**
     * {@inheritDoc}
     * 
     * Is the passed in User object is the SuperUser or <code>null</code> no
     * action is performed.
     */
    @Override
    public void remove(User user) {
        if (isSuperUser(user)) {
            return;
        }
        super.remove(user);
    }

    private boolean isSuperUser(User user) {
        return (user == null || user instanceof SystemUser || SystemUser.SYSTEM_USERNAME.equals(user.getFullname()));
    }
}
