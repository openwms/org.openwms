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
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.openwms.core.domain.system.usermanagement.User;
import org.springframework.stereotype.Component;

/**
 * A BeanMapper.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Component("userMapper")
public class BeanMapper {

    public UserVO map(User user) {
        Mapper mapper = new DozerBeanMapper();
        return mapper.map(user, UserVO.class);
    }

    public Collection<UserVO> map(Collection<User> users) {
        Mapper mapper = new DozerBeanMapper();
        List<UserVO> result = new ArrayList<>(users.size());
        for (User user : users) {
            result.add(mapper.map(user, UserVO.class));
        }
        return result;
    }
}
