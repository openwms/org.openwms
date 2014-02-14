/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
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
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.core.service.spring;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openwms.core.domain.system.I18n;
import org.openwms.core.integration.I18nRepository;
import org.openwms.core.test.AbstractMockitoTests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A I18nServiceTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * 
 */
public class I18nServiceTest extends AbstractMockitoTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(I18nServiceTest.class);
    @Mock
    private I18nRepository dao;
    @InjectMocks
    private I18nServiceImpl srv;

    /**
     * Test method for {@link org.openwms.core.service.spring.I18nServiceImpl#findAllTranslations()} .
     */
    @Test
    public final void testFindAllTranslations() {
        LOGGER.debug("5%10:" + 5 % 10);
        LOGGER.debug("5/10:" + 5 / 10);
        LOGGER.debug("11%10:" + 13 % 10);
        LOGGER.debug("11/10:" + 13 / 10);

        // preparing mocks
        when(dao.findAll()).thenReturn(Arrays.asList(new I18n[] { new I18n() }));

        assertEquals(new I18n(), srv.findAllTranslations().get(0));
        verify(dao).findAll();
    }

    /**
     * Test method for {@link org.openwms.core.service.spring.I18nServiceImpl#saveTranslations(org.openwms.core.domain.system.I18n[])} .
     * 
     * Test with an empty array argument.
     */
    @Test
    public final void testSaveTranslationsNoArgs() {
        srv.saveTranslations();
        verify(dao, never()).save(new I18n());
    }

    /**
     * Test method for {@link org.openwms.core.service.spring.I18nServiceImpl#saveTranslations(org.openwms.core.domain.system.I18n[])} .
     * 
     * Test with <code>null</code> argument.
     */
    @Test
    public final void testSaveTranslationsWithNull() {
        srv.saveTranslations(null);
        verify(dao, never()).save(new I18n());
    }

    /**
     * Test method for {@link org.openwms.core.service.spring.I18nServiceImpl#saveTranslations(org.openwms.core.domain.system.I18n[])} .
     * 
     * Test to save an I18n instance.
     */
    @Test
    public final void testSaveTranslations() {
        srv.saveTranslations(new I18n[] { new I18n() });
        verify(dao).save(new I18n());
    }
}