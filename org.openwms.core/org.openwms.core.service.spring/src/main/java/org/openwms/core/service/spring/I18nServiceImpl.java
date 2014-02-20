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
package org.openwms.core.service.spring;

import java.util.ArrayList;
import java.util.Collection;

import org.openwms.core.domain.system.I18n;
import org.openwms.core.integration.GenericDao;
import org.openwms.core.integration.I18nRepository;
import org.openwms.core.service.ExceptionCodes;
import org.openwms.core.service.I18nService;
import org.openwms.core.service.exception.ServiceRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * An I18nServiceImpl is responsible to load and save i18n translations.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Transactional
@Service(I18nServiceImpl.COMPONENT_NAME)
public class I18nServiceImpl extends AbstractGenericEntityService<I18n, Long, String> implements I18nService {

    @Autowired
    private I18nRepository i18nRepository;
    /** Springs service name. */
    public static final String COMPONENT_NAME = "i18nService";

    /**
     * {@inheritDoc}
     * 
     * @throws ServiceRuntimeException
     *             if the <tt>translations</tt> argument is <code>null</code>
     */
    @Override
    public Collection<I18n> saveAll(Collection<I18n> translations) {
        checkForNull(translations, ExceptionCodes.I18N_SAVE_NOT_BE_NULL);
        Collection<I18n> result = new ArrayList<>(translations.size());
        for (I18n entity : translations) {
            if (entity.isNew()) {
                create(entity);
            }
            result.add(save(entity));
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected GenericDao<I18n, Long> getRepository() {
        return i18nRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected I18n resolveByBK(I18n entity) {
        return i18nRepository.findByUniqueId(entity.getKey());
    }
}