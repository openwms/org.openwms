/*
 * Copyright 2005-2024 the original author or authors.
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

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;

/**
 * A TimeProviderTest.
 *
 * @author Heiko Scherrer
 */
class TimeProviderTest {


    /**
     * This test verifies if the nowAsCurrentDate method returns the current date
     * and time considering the system's configured timezone. The date generation
     * process by the method is replicated inside the test to compare and validate
     * the actual result.
     */
    @Test
    public void testNowAsCurrentDate() {
        TimeProvider timeProvider = new TimeProvider() {};

        // Call the method under test
        Date actualDate = timeProvider.nowAsCurrentDate();

        // Get the expected current date
        Date expectedDate = Date.from(ZonedDateTime.of(LocalDateTime.now(ZoneId.systemDefault()), ZoneId.systemDefault()).toInstant());

        // Since the date creation and method call can't happen simultaneously,
        // there might be slight microseconds difference between actual and expected dates.
        // The comparison should be upto the seconds to avoid test failure
        long actualTimeInSec = actualDate.getTime() / 1000;
        long expectedTimeInSec = expectedDate.getTime() / 1000;

        assertEquals(expectedTimeInSec, actualTimeInSec, "The expected and actual dates should be equal");
    }

    @Test
    void nowAsZuluZonedDateTime() {
        var tp = spy(TimeProvider.class);


        var zuluTime = tp.nowAsZuluZonedDateTime();
        System.out.println(zuluTime);

        ZonedDateTime nowGMTPlus1 = ZonedDateTime.now(ZoneId.of("GMT+1"));
        Date nowZulu = Date.from(nowGMTPlus1.toInstant());
        System.out.println(nowGMTPlus1);

        assertTrue(zuluTime.isBefore(nowGMTPlus1));
        assertTrue(zuluTime.plusMinutes(60).isAfter(nowGMTPlus1));
    }

    /**
     * Trivial implementation of TimeProvider for testing purposes.
     */
    private final TimeProvider timeProvider = new TimeProvider() {};

    @Test
    void testNowAsCurrentInstant() {
        Instant testInstant = Instant.now();
        Instant methodInstant = timeProvider.nowAsCurrentInstant();

        // We only check minutes as the precision of the test might result in a difference in milliseconds
        assertEquals(testInstant.atZone(ZoneId.systemDefault()).getMinute(), methodInstant.atZone(ZoneId.systemDefault()).getMinute());
    }
}