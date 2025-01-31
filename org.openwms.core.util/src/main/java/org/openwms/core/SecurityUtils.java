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
package org.openwms.core;

import org.springframework.http.HttpHeaders;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * A SecurityUtils collects useful functions regarding security aspects.
 *
 * @author Heiko Scherrer
 */
public final class SecurityUtils {

    private SecurityUtils() {}

    /**
     * With the given {@code username} and {@code password} create a Base64 encoded valid
     * http BASIC schema authorization header, and return it within a {@link HttpHeaders}
     * object.
     *
     * @param username The BASIC auth username
     * @param password The BASIC auth password
     * @return The HttpHeaders object containing the Authorization Header
     */
    public static HttpHeaders createHeaders(String username, String password) {
        if (username == null || username.isEmpty()) {
            return new HttpHeaders();
        }
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);

        HttpHeaders result =  new HttpHeaders();
        result.add(HttpHeaders.AUTHORIZATION, authHeader);
        return result;
    }
}
