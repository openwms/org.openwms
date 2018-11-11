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
 * A DomainModelRuntimeException is an unchecked top-level exception for all unhandled runtime exceptions of the domain model layer.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public class DomainModelRuntimeException extends RuntimeException {

    /**
     * Create a new DomainModelRuntimeException.
     */
    public DomainModelRuntimeException() {
        super();
    }

    /**
     * Create a new DomainModelRuntimeException.
     *
     * @param message Detail message
     */
    public DomainModelRuntimeException(String message) {
        super(message);
    }

    /**
     * Create a new DomainModelRuntimeException.
     *
     * @param cause Root cause
     */
    public DomainModelRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a new DomainModelRuntimeException.
     *
     * @param message Detail message
     * @param cause Root cause
     */
    public DomainModelRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}