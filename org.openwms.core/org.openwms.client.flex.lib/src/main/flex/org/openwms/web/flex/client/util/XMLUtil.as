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

    import mx.core.ByteArrayAsset;

    /**
     * A XMLUtil. Utility class to do basic XML handling.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public final class XMLUtil {

        /**
         * Constructor.
         */
        public function XMLUtil() : void {
            super();
        }

        /**
         * Convert a class that is embedded as XML file into a XML representation.
         *
         * @param clazz The class to convert
         * @return The converted class as XML instance
         */
        public static function getXML(clazz : *) : XML {
            var ba : ByteArrayAsset = ByteArrayAsset(clazz);
            var xml : XML = new XML(ba.readUTFBytes(ba.length));
            return xml;
        }
    }
}

