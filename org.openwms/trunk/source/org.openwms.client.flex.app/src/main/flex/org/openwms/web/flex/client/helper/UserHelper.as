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
package org.openwms.web.flex.client.helper
{

    import org.openwms.common.domain.system.usermanagement.User;
    import org.openwms.common.domain.system.usermanagement.UserDetails$SEX;

    /**
     * An UserHelper.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision$
     */
    public final class UserHelper
    {
        public function UserHelper()
        {

        }

        public static function haveDetails(userData:User):Boolean
        {
            if (userData != null && userData.userDetails != null)
            {
                return true;
            }
            return false;
        }

        public static function traceUser(user:User):void
        {
            if (user == null)
            {
                return;
            }
            trace("Username:" + user.username);
            trace("Fullname:" + user.fullname);

            if (haveDetails(user))
            {
                // trace also the details
                //trace("Sex:"+user.userDetails.sex);
            }
        }

        public static function bindUserDetailsSEXtoRBG(sex:UserDetails$SEX):UserDetails$SEX
        {
            if (sex != null)
            {
                trace("Sex is set");
                return sex;
            }
            else
            {
                trace("Sex is NOT set");
                return null;
            }
        }

        public static function isNew(user:User):Boolean
        {
            if (null == user || isNaN(user.id))
            {
                return true;
            }
            return false;
        }
    }

}