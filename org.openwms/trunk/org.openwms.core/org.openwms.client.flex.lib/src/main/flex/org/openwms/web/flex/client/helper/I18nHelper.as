/*
 * openwms.org, the Open Warehouse Management System.
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
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.web.flex.client.helper {

    import mx.resources.ResourceManager;
    import mx.utils.ArrayUtil;

    [Name]
    [Bindable]
    /**
     * An I18nHelper is a helper class for i18n functionality.
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
        
        public static function switchLanguage(language : String) : void {
            ResourceManager.getInstance().localeChain = [language];
            ResourceManager.getInstance().update();
        }

        /**
         * Translate a paramterized String into the language set by the user.
         * The first argument is expected to be the String, the rest arbitrary parameters.
         *
         * @param args An arbitrary list. At least the first parameter must be set as the
         * String to be translated
         */
        public static function trans(bundle : String, key : String, ... args) : String {
            return ResourceManager.getInstance().getString(bundle, key, args);
        }
    }
}

