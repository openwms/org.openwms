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
package org.openwms.core.values;

/**
 * A definition of any kind of measurement used in the application. In general an
 * {@code AbstractMeasure} is the base class for all kind of measures. For example {@literal 42 grams}
 * is a {@code AbstractMeasure} (in particular a {@code Weight}, whereas 42 is the
 * magnitude and grams is the {@code BaseUnit}. This type is merely used to erase the
 * type declaration.
 *
 * @author Heiko Scherrer
 */
@SuppressWarnings("rawtypes")
public interface AbstractMeasure extends Measurable {
}