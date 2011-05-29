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
package org.openwms.core.service.spring;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.openwms.core.annotation.FireAfterTransaction;
import org.openwms.core.util.event.UserChangedEvent;

/**
 * A SecurityServiceImpl.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Transactional
@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    @Qualifier("securityObjectDao")
    private SecurityObjectDao dao;

    /**
     * @see org.openwms.core.service.SecurityService#mergeGrants(java.lang.String,
     *      java.util.List)
     */
    @Override
    @FireAfterTransaction(events = { UserChangedEvent.class })
    public List<Grant> mergeGrants(String moduleName, List<Grant> grants) {
        if (logger.isDebugEnabled()) {
            logger.debug("Merging grants of module:" + moduleName);
        }
        List<Grant> persisted = dao.findAllOfModule(moduleName + "%");
        List<Grant> result = new ArrayList<Grant>(persisted.size());
        result.addAll(persisted);
        Grant merged = null;
        for (Grant grant : grants) {
            if (!persisted.contains(grant)) {
                merged = (Grant) dao.merge(grant);
                result.add(merged);
                if (persisted.contains(merged)) {
                    persisted.remove(merged);
                }
            } else {
                persisted.remove(grant);
            }
        }
        dao.delete(persisted);
        return result;
    }

}
