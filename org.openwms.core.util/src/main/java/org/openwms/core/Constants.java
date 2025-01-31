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

/**
 * A Constants class collects general used static constants.
 *
 * @author Heiko Scherrer
 */
public final class Constants {

    private Constants() {
    }

    /**
     * Path to the initial application properties file. This constant can be referenced by
     * Springs Value annotation. Value is {@value}. appProps is declared in Springs XML
     * configuration.
     */
    public static final String APPLICATION_INITIAL_PROPERTIES = "#{ appProps['application.initial.properties'] }";

}
