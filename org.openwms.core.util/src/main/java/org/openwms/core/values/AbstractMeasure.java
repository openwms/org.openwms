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
package org.openwms.core.values;

/**
 * A definition of any kind of measurement used in the application. In general an <code>AbstractMeasure</code> is the base class for all
 * kind of measures. For example <code>42 grams</code> is a <code>AbstractMeasure</code> (in particular a <code>Weight</code>), whereas 42
 * is the magnitude and grams is the <code>BaseUnit</code>. This class is merely used to erase the type declaration.
 *
 * @param <V> Type of magnitude, any subtype of java.lang.Number
 * @param <T> Type of unit - an extension of <code>BaseUnit</code>
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractMeasure implements Measurable {

}