/*
 * OpenWMS, the Open Warehouse Management System
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.client.helper {

	import org.openwms.common.domain.system.usermanagement.User;
	import org.openwms.common.domain.system.usermanagement.UserDetails$SEX;

	/**
	 * A UserHelper.
	 *
	 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
	 * @version $Revision: 235 $
	 */
	public final class UserHelper {
		public function UserHelper() {

		}

		public static function haveDetails(userData:User):Boolean {
			if (userData != null && userData.userDetails != null) {
				return true;
			}
			return false;
		}

		public static function traceUser(user:User):void {
			if (user == null) {
				return;
			}
			trace("Username:" + user.username);
			trace("Fullname:" + user.fullname);

			if (haveDetails(user)) {
				// trace also the details
				//trace("Sex:"+user.userDetails.sex);
			}
		}

		public static function bindUserDetailsSEXtoRBG(sex:UserDetails$SEX):UserDetails$SEX {
			if (sex != null) {
				trace("Sex is set");
				return sex;
			} else {
				trace("Sex is NOT set");
				return null;
			}
		}

		public static function isNew(user:User):Boolean {
			if (null == user || isNaN(user.id)) {
				return true;
			}
			return false;
		}
	}

}