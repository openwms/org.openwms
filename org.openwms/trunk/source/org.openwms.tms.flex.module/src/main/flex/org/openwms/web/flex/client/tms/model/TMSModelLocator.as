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
package org.openwms.web.flex.client.tms.model
{

    import com.adobe.cairngorm.model.IModelLocator;
    import mx.collections.ArrayCollection;
    import org.openwms.common.domain.system.usermanagement.User;

    /**
     * A TMSModelLocator.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision: 796 $
     */
    [Bindable]
    public class TMSModelLocator implements IModelLocator
    {

        public var allTransportOrders:ArrayCollection = new ArrayCollection();

        private static var instance:TMSModelLocator;

        /**
         * Used to construct the Singleton instance.
         */
        public function TMSModelLocator(enforcer:SingletonEnforcer)
        {
        }

        /**
         * Return the instance of TMSModelLocator which is implemented
         * as Singleton.
         */
        public static function getInstance():TMSModelLocator
        {
            if (instance == null)
            {
                instance = new TMSModelLocator(new SingletonEnforcer);
            }
            return instance;
        }
    }
}

class SingletonEnforcer
{
}