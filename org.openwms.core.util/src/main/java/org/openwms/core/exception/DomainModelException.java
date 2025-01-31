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

/**
 * A DomainModelException is a checked top-level exception for all exceptions within the
 * domain model layer.
 *
 * @author Heiko Scherrer
 */
public class DomainModelException extends Exception {

    /**
     * Create a new DomainModelException.
     */
    public DomainModelException() {
        super();
    }

    /**
     * Create a new DomainModelException.
     *
     * @param message Detail message
     */
    public DomainModelException(String message) {
        super(message);
    }

    /**
     * Create a new DomainModelException.
     *
     * @param cause Root cause
     */
    public DomainModelException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a new DomainModelException.
     *
     * @param message Detail message
     * @param cause Root cause
     */
    public DomainModelException(String message, Throwable cause) {
        super(message, cause);
    }
}