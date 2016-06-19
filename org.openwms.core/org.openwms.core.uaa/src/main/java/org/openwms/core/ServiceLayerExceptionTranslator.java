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
package org.openwms.core;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import org.ameba.aop.ServiceLayerAspect;
import org.ameba.exception.ServiceLayerException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * A ServiceLayerExceptionTranslator.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@Component
@Order(16)
class ServiceLayerExceptionTranslator extends ServiceLayerAspect {

    /**
     * Override method to handle the transaction yourself and skip to the default exception handling .
     *
     * @param ex Exception to handle
     * @return An empty Optional to use the default exception handling or an Exception to skip default handling
     */
    @Override
    protected Optional<Exception> doTranslateException(Exception ex) {
        if (ex instanceof ConstraintViolationException) {
            return Optional.of(ex);
        }
        if (ex instanceof ServiceLayerException) {
            return Optional.of(ex);
        }
        return Optional.of(new ServiceLayerException(ex.getMessage()));
    }
}
