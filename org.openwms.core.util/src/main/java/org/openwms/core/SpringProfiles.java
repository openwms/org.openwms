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
 * A SpringProfiles is a collection of wellknown Spring Profiles used in OpenWMS.org.
 *
 * @author Heiko Scherrer
 */
public final class SpringProfiles {

    /**
     * Used to define that the service is running in a distributed environment.
     *
     * @deprecated Use {@link org.ameba.app.SpringProfiles#DISTRIBUTED DISTRIBUTED} instead
     */
    @Deprecated(forRemoval = true)
    public static final String DISTRIBUTED = "DISTRIBUTED";
    /**
     * Used to define that asynchronous message handling is used.
     *
     * @deprecated Use {@link org.ameba.app.SpringProfiles#AMQP AMQP} instead
     */
    @Deprecated(forRemoval = true)
    public static final String ASYNCHRONOUS_PROFILE = "ASYNCHRONOUS";
    /** Used to define that the asynchronous message handling is NOT used. */
    public static final String NOT_ASYNCHRONOUS = "!ASYNCHRONOUS";
    /** Used to define that synchronous message handling is used. */
    public static final String SYNCHRONOUS_PROFILE = "SYNCHRONOUS";
    /** Used to define application slices when the application's workflow is not managed by a supervisor process. */
    public static final String NOT_MANAGED = "!MANAGED";
    /** Used to define that synchronous message handling is used. */
    public static final String IN_MEMORY = "INMEM";
    /**
     * Used to define that running within an OSGi container.
     *
     * @deprecated In the next mayor release
     */
    @Deprecated(forRemoval = true)
    public static final String OSGI = "OSGI";
    /**
     * Used to define that not running in an OSGi container.
     *
     * @deprecated In the next mayor release
     */
    @Deprecated(forRemoval = true)
    public static final String NON_OSGI = "noOSGI";

    private SpringProfiles() { }
}
