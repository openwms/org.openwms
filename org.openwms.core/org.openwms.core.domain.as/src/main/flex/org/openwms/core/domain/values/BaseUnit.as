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
package org.openwms.core.domain.values {

    import mx.collections.ListCollectionView;


    /**
     * An BaseUnit is the type definition of an <code>Measurable</code>. Each
     * BaseUnit defines a base <code>Measurable</code>. For example the BaseUnit of
     * weights can define grams (G), kilograms (KG) or tons (T) and 1G is the base
     * unit.
     *
     * @version $Revision: 1719 $
     * @since 0.1
     */
    public interface BaseUnit {

        /**
         * Return all sub types of the <code>BaseUnit</code>.
         *
         * @param <T>
         *            Type definition of all concrete Units
         * @return a list of sub types
         */
        function get all() : ListCollectionView;

        /**
         * Return the base unit type of the <code>BaseUnit</code>.
         *
         * @param <T>
         *            Type definition of all concrete Units
         * @return The base unit type
         */
        function get baseUnit() : BaseUnit;
    }
}
