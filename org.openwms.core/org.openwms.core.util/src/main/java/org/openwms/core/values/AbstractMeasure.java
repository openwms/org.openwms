/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.core.values;

/**
 * A definition of any kind of measurement used in the application. In general an <code>AbstractMeasure</code> is the base class for all
 * kind of measures. For example <code>42 grams</code> is a <code>AbstractMeasure</code> (in particular a <code>Weight</code>), whereas 42
 * is the magnitude and grams is the <code>BaseUnit</code>. This class is merely used to erase the type declaration.
 * 
 * @param <V>
 *            Type of magnitude, any subtype of java.lang.Number
 * @param <T>
 *            Type of unit - an extension of <code>BaseUnit</code>
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.2
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractMeasure implements Measurable {

}