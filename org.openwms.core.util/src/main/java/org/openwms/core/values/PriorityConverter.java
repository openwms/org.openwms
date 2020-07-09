/*
 * Copyright 2005-2020 the original author or authors.
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
package org.openwms.core.values;

import org.dozer.DozerConverter;

/**
 * A PriorityConverter is a {@link DozerConverter} to convert between {@link Integer} and {@link PriorityLevel}.
 *
 * @author Heiko Scherrer
 */
public class PriorityConverter extends DozerConverter<Integer, PriorityLevel> {

    public PriorityConverter() {
        super(Integer.class, PriorityLevel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PriorityLevel convertTo(Integer source, PriorityLevel destination) {
        if (source == null) {
            return PriorityLevel.NORMAL;
        }
        return PriorityLevel.convert(source);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer convertFrom(PriorityLevel source, Integer destination) {
        if (source == null) {
            return null;
        }
        return source.getOrder();
    }
}
