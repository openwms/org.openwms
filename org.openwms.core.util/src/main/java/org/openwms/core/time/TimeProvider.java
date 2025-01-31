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
package org.openwms.core.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * A TimeProvider defines several widely used time formats as constants and provides the current time. An implementation may be loaded using
 * the Java1.6 ServiceLoader facility or by Spring dependency injection.
 *
 * @author Heiko Scherrer
 */
public interface TimeProvider {

    /** The format pattern for all date types. */
    String DATE_FORMAT = "yyyy-MM-dd";
    /** The format pattern for all date-time types without milliseconds and without timezone. */
    String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /** The format pattern for all date-time with milliseconds without timezone types. */
    String DATE_TIME_MILLIS_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    /** The format pattern for all date-time types without milliseconds but with timezone. */
    String DATE_TIME_WITH_TIMEZONE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";
    /** The format pattern for all date-time types with milliseconds and timezone. */
    String DATE_TIME_MILLIS_WITH_TIMEZONE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    /**
     * Returns the current date and time considering the configured timezone.
     *
     * @return Timezone aware Date
     */
    default Date nowAsCurrentDate() {
        return Date.from(ZonedDateTime.of(LocalDateTime.now(ZoneId.systemDefault()), ZoneId.systemDefault()).toInstant());
    }

    /**
     * Returns the current date and time in UTC timezone.
     *
     * @return UTC Date
     */
    default Date nowAsZuluDate() {
        return Date.from(ZonedDateTime.now(ZoneId.of("Z")).toInstant());
    }

    /**
     * Returns the current date and time considering the configured timezone.
     *
     * @return Timezone aware ZonedDateTime
     */
    default ZonedDateTime nowAsCurrentZonedDateTime() {
        return nowAsZonedDateTime(ZoneId.systemDefault());
    }

    /**
     * Returns the current date and time considering the configured timezone.
     *
     * @return Timezone aware ZonedDateTime
     */
    default ZonedDateTime nowAsZuluZonedDateTime() {
        return nowAsZonedDateTime(ZoneOffset.UTC);
    }

    /**
     * Returns the current date and time considering the given timezone.
     *
     * @param zoneId ZoneId to consider
     * @return Timezone aware ZonedDateTime
     */
    default ZonedDateTime nowAsZonedDateTime(ZoneId zoneId) {
        return ZonedDateTime.now(zoneId);
    }

    /**
     * Returns the current date and time of the system considering the configured timezone.
     *
     * @return Timezone aware Instant
     */
    default Instant nowAsCurrentInstant() {
        return ZonedDateTime.of(LocalDateTime.now(ZoneId.systemDefault()), ZoneId.systemDefault()).toInstant();
    }

    /**
     * Returns the date and time in UTC timezone.
     *
     * @return Timezone aware Instant
     */
    default Instant nowAsZuluInstant() {
        return ZonedDateTime.of(LocalDateTime.now(ZoneOffset.UTC), ZoneOffset.UTC).toInstant();
    }
}
