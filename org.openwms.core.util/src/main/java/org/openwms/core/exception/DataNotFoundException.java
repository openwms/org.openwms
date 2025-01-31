/*
 * Copyright 2005-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openwms.core.exception;

import org.ameba.exception.IntegrationLayerException;

import java.io.Serializable;

import static java.lang.String.format;

/**
 * A DataNotFoundException is thrown to indicate that data was expected but nothing was
 * found.
 *
 * @author Heiko Scherrer
 */
public class DataNotFoundException extends IntegrationLayerException {

    /**
     * Create a new DataNotFoundException with a message text and the root
     * exception.
     *
     * @param message Message text as String
     * @param cause The root exception
     */
    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a new DataNotFoundException with a message text.
     *
     * @param message Message text as String
     */
    public DataNotFoundException(String message) {
        super(message);
    }

    /**
     * Create a new DataNotFoundException with the id of the expected entity.
     *
     * @param id Id of the expected entity
     */
    public DataNotFoundException(Serializable id) {
        super(format("Entity class not found in persistence layer, id=[%s]",id));
    }
}