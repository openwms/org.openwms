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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openwms.core.domain.system.I18n;
import org.openwms.core.integration.I18nRepository;
import org.openwms.core.service.exception.ServiceRuntimeException;
import org.openwms.core.test.AbstractMockitoTests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

/**
 * A I18nServiceTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
public class I18nServiceTest extends AbstractMockitoTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(I18nServiceTest.class);
    @Mock
    private I18nRepository dao;
    @Mock
    private MessageSource messageSource;
    @InjectMocks
    private I18nServiceImpl srv;

    @Before
    public void onBefore() {
        when(messageSource.getMessage(anyString(), new Object[] { anyObject() }, any(Locale.class))).thenReturn("");
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.I18nServiceImpl#findAllTranslations()}
     * .
     */
    @Test
    public final void testFindAll() {
        LOGGER.debug("5%10:" + 5 % 10);
        LOGGER.debug("5/10:" + 5 / 10);
        LOGGER.debug("11%10:" + 13 % 10);
        LOGGER.debug("11/10:" + 13 / 10);

        // preparing mocks
        when(dao.findAll()).thenReturn(Arrays.asList(new I18n[] { new I18n() }));

        assertEquals(new I18n(), srv.findAll().get(0));
        verify(dao).findAll();
    }

    /**
     * Negative test to call saveAll with <code>null</code> argument.
     */
    @Test(expected = ServiceRuntimeException.class)
    public final void testSaveAllWithNull() {
        srv.saveAll(null);
    }

    /**
     * Positive test to save one I18n instance.
     */
    @Test
    public final void testSaveAll() {
        srv.saveAll(Arrays.asList(new I18n[] { new I18n() }));
        verify(dao, times(2)).save(new I18n());
    }
}