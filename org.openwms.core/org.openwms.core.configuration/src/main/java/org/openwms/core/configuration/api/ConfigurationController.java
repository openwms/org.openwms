/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openwms.core.configuration.api;

import org.openwms.core.configuration.ConfigurationService;
import org.openwms.core.configuration.file.AbstractPreference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * A ConfigurationController.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @since 2.0
 */
@RestController("/v1/preferences")
class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @GetMapping(produces= MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Flux<AbstractPreference> findAllReactive() {
        return Flux.fromIterable(configurationService.findAll()).log();
    }

    @GetMapping
    public Iterable<AbstractPreference> findAll() {
        return configurationService.findAll();
    }
}
