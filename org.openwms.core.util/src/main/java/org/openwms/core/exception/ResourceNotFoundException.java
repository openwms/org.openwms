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
 * A ResourceNotFoundException is thrown when a given resource couldn't be resolved.
 *
 * @author Heiko Scherrer
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Create a new ResourceNotFoundException.
     */
    public ResourceNotFoundException() {
        super();
    }

    /**
     * Create a new ResourceNotFoundException with a message text.
     *
     * @param message Message text as String
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Create a new ResourceNotFoundException with the root exception.
     *
     * @param cause The root exception
     */
    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a new ResourceNotFoundException with a message text and the root exception.
     *
     * @param message Message text as String
     * @param cause The root exception
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}