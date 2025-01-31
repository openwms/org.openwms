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

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A AbstractWebControllerTest.
 *
 * @author Heiko Scherrer
 */
class AbstractWebControllerTest {

    @Test
    void test_Location_header_without_forwarded() {

        var request = new MockHttpServletRequest();
        request.setServerName("172.19.0.4");
        request.setRequestURI("/v1/rest");

        var testee = new AbstractWebController(){};
        assertThat(testee.getLocationURIForCreatedResource(request, "4711").toASCIIString()).isEqualTo("http://172.19.0.4:80/v1/rest/4711/");
    }

    @Test
    void test_Location_header_with_forwarded() {

        var request = new MockHttpServletRequest();
        request.setServerName("172.19.0.4");
        request.setRequestURI("/v1/rest");
        request.addHeader("x-forwarded-for", "10.10.16.6");
        request.addHeader("x-forwarded-proto", "http");
        request.addHeader("x-forwarded-prefix", "/common");
        request.addHeader("x-forwarded-port", "8086");
        request.addHeader("x-forwarded-host", "hostname.local:8086");

        var testee = new AbstractWebController(){};
        assertThat(testee.getLocationURIForCreatedResource(request, "4711").toASCIIString()).isEqualTo("http://hostname.local:8086/common/v1/rest/4711/");
    }
}