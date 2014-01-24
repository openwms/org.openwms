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
package org.openwms.core.rest.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A BeanMapper.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Component("beanMapper")
public class BeanMapper<S, T> {

    @Autowired
    private Mapper mapper;

    public T map(S user, Class<T> clazz) {
        return mapper.map(user, clazz);
    }

    public S mapBackwards(T user, Class<S> clazz) {
        return mapper.map(user, clazz);
    }

    public S mapFromTo(S source, S target) {
        mapper.map(source, target);
        return target;
    }

    public Collection<T> map(Collection<S> users, Class<T> clazz) {
        if (users == null || users.isEmpty()) {
            return Collections.<T> emptyList();
        }
        List<T> result = new ArrayList<>(users.size());
        for (S user : users) {
            result.add(mapper.map(user, clazz));
        }
        return result;
    }

    public Collection<S> mapBackwards(Collection<T> users, Class<S> clazz) {
        if (users == null || users.isEmpty()) {
            return Collections.<S> emptyList();
        }
        List<S> result = new ArrayList<>(users.size());
        for (T user : users) {
            result.add(mapper.map(user, clazz));
        }
        return result;
    }
}
