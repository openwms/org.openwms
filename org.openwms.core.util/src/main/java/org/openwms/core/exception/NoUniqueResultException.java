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

/**
 * A NoUniqueResultException is thrown when a single result was expected but multiple
 * entities were found.
 *
 * @author Heiko Scherrer
 */
public class NoUniqueResultException extends IntegrationLayerException {

    /**
     * Create a new NoUniqueResultException.
     */
    public NoUniqueResultException() {
        super();
    }

    /**
     * Create a new NoUniqueResultException with a message text.
     *
     * @param message Message text as String
     */
    public NoUniqueResultException(String message) {
        super(message);
    }

    /**
     * Create a new NoUniqueResultException with a message text and the root
     * exception.
     *
     * @param message Message text as String
     * @param cause The root exception
     */
    public NoUniqueResultException(String message, Throwable cause) {
        super(message, cause);
    }
}