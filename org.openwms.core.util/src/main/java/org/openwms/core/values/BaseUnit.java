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

import java.util.List;

/**
 * A BaseUnit is the type definition of an <code>Measurable</code>. Each BaseUnit defines a base <code>Measurable</code>. For example the
 * BaseUnit of weights can define grams (G), kilograms (KG) or tons (T) and 1G is the base unit.
 * 
 * @param <T>
 *            Concrete type of BaseUnit
 * @GlossaryTerm
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.2
 */
public interface BaseUnit<T extends BaseUnit<T>> {

    /**
     * Return all sub types of the <code>UnitType</code>.
     * 
     * @param <T>
     *            Type definition of all concrete Units
     * @return a list of sub types
     */
    List<T> getAll();

    /**
     * Return the base unit type of the <code>UnitType</code>.
     * 
     * @param <T>
     *            Type definition of all concrete Units
     * @return The base unit type
     */
    T getBaseUnit();
}