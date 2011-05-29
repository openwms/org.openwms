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
package org.openwms.core.service;

import java.util.List;

import org.openwms.core.domain.system.I18n;

/**
 * An I18nService.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public interface I18nService {

    /**
     * Find and return a list of all translations.
     * 
     * @return all translations
     */
    List<I18n> findAllTranslations();

    /**
     * Save one translation, this can be a new one all an already existing one.
     * 
     * @param translation
     *            Translation to save or persist
     */
    void saveTranslation(I18n translation);

    /**
     * Save a list of translations. Elements of the list can be transient or
     * persisted entities.
     * 
     * @param translations
     *            The list of translations to save or persist.
     */
    void saveTranslations(List<I18n> translations);
}
