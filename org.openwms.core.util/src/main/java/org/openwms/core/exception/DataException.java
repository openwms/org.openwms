/*
 * Copyright 2018 Heiko Scherrer
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

/**
 * A DataException is a general type of DAO exception.
 * <p>
 * Superclass of exceptions thrown in the persistence integration layer regarding data access errors.
 * </p>
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @deprecated Use IntegrationRuntimeException instead
 */
@Deprecated
public class DataException extends RuntimeException {

    /**
     * Create a new <code>DataException</code>.
     */
    public DataException() {
        super();
    }

    /**
     * Create a new <code>DataException</code> with a message text.
     *
     * @param message Message text as String
     */
    public DataException(String message) {
        super(message);
    }

    /**
     * Create a new <code>DataException</code> with the root exception.
     *
     * @param cause The root exception
     */
    public DataException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a new <code>DataException</code> with a message text and the root exception.
     *
     * @param message Message text as String
     * @param cause The root exception
     */
    public DataException(String message, Throwable cause) {
        super(message, cause);
    }
}