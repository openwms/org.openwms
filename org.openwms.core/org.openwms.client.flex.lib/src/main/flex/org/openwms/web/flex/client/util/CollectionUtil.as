/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the
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
package org.openwms.web.flex.client.util {

    import flash.utils.Dictionary;
    
    import mx.collections.ArrayCollection;
    
    import org.granite.util.DictionaryUtil;

    /**
     * A CollectionUtil provides useful helper methods regarding Collections/Maps or tree-like structures.
     *
     * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
     * @version $Revision: 1409 $
     * @since 0.1
     */
    public final class CollectionUtil {

        /**
         * Constructor.
         */
        public function CollectionUtil() {
        }

        /**
         * Get all values from the dictionary and return them as an ArrayCollection. When the dictionary
         * has no values, the returned ArrayCollection is empty, but not null.
         *
         * @param dictionary The Dictionary to convert
         * @return The dictionary as ArrayCollection
         */
        public static function toArrayCollection(dictionary : Dictionary, keys : Boolean = false) : ArrayCollection {
            if (dictionary == null) {
                return new ArrayCollection();
            }
            var ar : Array;
            if (keys) {
                ar = DictionaryUtil.getKeys(dictionary);
            } else {
                ar = DictionaryUtil.getValues(dictionary);
            }
            if (ar.length > 0) {
                return new ArrayCollection(ar);
            }
            return new ArrayCollection();
        }
    }
}