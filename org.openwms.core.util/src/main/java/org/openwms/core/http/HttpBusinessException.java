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
package org.openwms.core.http;

import org.springframework.http.HttpStatus;

/**
 * A HttpBusinessException encapsulates well-known BusinessExceptions into exceptions particular to http translation.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public class HttpBusinessException extends RuntimeException {

    private final HttpStatus httpStatus;

    /**
     * Create a new HttpBusinessException.
     *
     * @param pHttpStatus
     */
    public HttpBusinessException(HttpStatus pHttpStatus) {
        super();
        this.httpStatus = pHttpStatus;
    }

    /**
     * Create a new HttpBusinessException.
     *
     * @param pMessage
     * @param pHttpStatus
     */
    public HttpBusinessException(String pMessage, HttpStatus pHttpStatus) {
        super(pMessage);
        this.httpStatus = pHttpStatus;
    }

    /**
     * Create a new HttpBusinessException.
     *
     * @param pCause
     * @param pHttpStatus
     */
    public HttpBusinessException(Throwable pCause, HttpStatus pHttpStatus) {
        super(pCause);
        this.httpStatus = pHttpStatus;
    }

    /**
     * Get the httpStatus.
     *
     * @return the httpStatus.
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
