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

import java.util.List;

import org.openwms.core.domain.system.I18n;
import org.openwms.core.integration.I18nRepository;
import org.openwms.core.service.I18nService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * An I18nServiceImpl.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Transactional
@Service("i18nService")
public class I18nServiceImpl implements I18nService {

    private static final Logger logger = LoggerFactory.getLogger(I18nServiceImpl.class);
    @Autowired
    private I18nRepository repo;

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.service.I18nService#findAllTranslations()
     */
    @Override
    public List<I18n> findAllTranslations() {
        return repo.findAll();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.service.I18nService#saveTranslations(org.openwms.core.domain.system.I18n[])
     */
    @Override
    public void saveTranslations(I18n... translations) {
        if (null == translations) {
            logger.warn("I18nService called to save translations but these are NULL");
            return;
        }
        // TODO [scherrer] : extend repository to be compliant with lists,
        // instead of looping through all elements
        for (I18n i18n : translations) {
            // TODO [scherrer] : Is this implementation safe for persisted
            // entities?
            repo.save(i18n);
        }
    }
}
