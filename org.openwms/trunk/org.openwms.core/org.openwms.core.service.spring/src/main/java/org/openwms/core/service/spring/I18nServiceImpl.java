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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * An I18nServiceImpl.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Transactional
@Service("i18nService")
public class I18nServiceImpl implements I18nService {

    @Autowired
    private I18nRepository repo;

    /**
     * @see org.openwms.core.service.I18nService#findAllTranslations()
     */
    @Override
    public List<I18n> findAllTranslations() {
        return repo.findAll();
    }

    /**
     * @see org.openwms.core.service.I18nService#saveTranslation(org.openwms.core.domain.system.I18n)
     */
    @Override
    public void saveTranslation(I18n translation) {
        repo.save(translation);
    }

    /**
     * @see org.openwms.core.service.I18nService#saveTranslations(java.util.List)
     */
    @Override
    public void saveTranslations(List<I18n> translations) {
        for (I18n i18n : translations) {
            repo.save(i18n);
        }
    }

}
