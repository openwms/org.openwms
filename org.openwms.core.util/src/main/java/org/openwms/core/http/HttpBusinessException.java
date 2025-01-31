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
package org.openwms.core.http;

import org.springframework.http.HttpStatus;

/**
 * A HttpBusinessException encapsulates well-known BusinessExceptions into exceptions
 * particular to http translation.
 *
 * @author Heiko Scherrer
 */
public class HttpBusinessException extends RuntimeException {

    private final HttpStatus httpStatus;

    /**
     * Create a new HttpBusinessException.
     *
     * @param httpStatus The passed http status
     */
    public HttpBusinessException(HttpStatus httpStatus) {
        super();
        this.httpStatus = httpStatus;
    }

    /**
     * Create a new HttpBusinessException.
     *
     * @param message The passed message text
     * @param httpStatus The passed http status
     */
    public HttpBusinessException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    /**
     * Create a new HttpBusinessException.
     *
     * @param cause The root cause
     * @param httpStatus The passed http status
     */
    public HttpBusinessException(Throwable cause, HttpStatus httpStatus) {
        super(cause);
        this.httpStatus = httpStatus;
    }

    /**
     * Get the http status.
     *
     * @return the httpStatus.
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
