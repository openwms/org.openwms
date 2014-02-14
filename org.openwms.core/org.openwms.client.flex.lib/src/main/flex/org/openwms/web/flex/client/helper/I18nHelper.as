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
package org.openwms.web.flex.client.helper {

    import mx.resources.ResourceManager;

    [Name]
    [Bindable]
    /**
     * An I18nHelper is a helper class to support i18n functionality.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision: 1468 $
     * @since 0.1
     */
    public class I18nHelper {

        /**
         * Constructor.
         */
        public function I18nHelper() : void {
            super();
        }

        /**
         * Switch the language of the application.
         *
         * @param language The language appreviation (i.e. en_US)
         */
        public static function switchLanguage(language : String) : void {
            trace("Switching language to: " + language);
            ResourceManager.getInstance().localeChain = [language];
            ResourceManager.getInstance().update();
        }

        /**
         * Translate a paramterized String into the language set by the user.
         * The first argument is expected to be the String, the rest arbitrary parameters.
         *
         * @param bundle The I18n ResourceBundle
         * @param key The key to lookup the translation
         * @param args An arbitrary list. At least the first parameter must be set as the
         *      String to be translated
         * @return The translated interpolated String
         */
        public static function trans(bundle : String, key : String, ... args) : String {
            return ResourceManager.getInstance().getString(bundle, key, args);
        }
    }
}

