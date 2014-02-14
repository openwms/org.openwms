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
package org.openwms.web.flex.client.view.dialogs {

    import flash.events.KeyboardEvent;
    import mx.collections.ArrayCollection;
    import mx.managers.PopUpManager;
    import org.openwms.core.domain.system.usermanagement.Grant;
    import org.openwms.core.domain.system.usermanagement.Role;
    import org.openwms.core.domain.system.usermanagement.SecurityObject;
    import org.openwms.web.flex.client.util.I18nUtil;

    [Name]
    [Bindable]
    [ResourceBundle("appMain")]
    /**
     * An AssignGrantsDialog.
     *
     * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
     * @version $Revision$
     * @since 0.1
     */
    public class AssignGrantsDialog extends AssignUsersDialog {

        /**
         * Constructor.
         */
        public function AssignGrantsDialog() { }

        override protected function onCreationComplete() : void {
            notAssigned = new ArrayCollection(modelLocator.securityObjects.toArray());
            toAssign = assigned;
            for each (var grant : Grant in toAssign) {
                for each (var g : Grant in notAssigned) {
                    if (grant.name == g.name) {
                        notAssigned.removeItemAt(notAssigned.getItemIndex(g));
                    }
                }
            }
            this.addEventListener(KeyboardEvent.KEY_DOWN, keyEventHandler);
            this.setFocus();
            PopUpManager.centerPopUp(this);
        }

        override protected function formatFunction(item : *) : String { return (item == null ? " " : (item as SecurityObject).name + " - " + (item as SecurityObject).description); }

        override protected function getTitle():String { return I18nUtil.trans(I18nUtil.APP_MAIN, "txt_assignGrants_title", role.name); }

        /**
         * Sort Grants by name.
         *
         * @return The name of the Grant.
         */
        override protected function getSortField():String { return "name"; }

        override protected function getAssignedLabel():String { return I18nUtil.trans(I18nUtil.APP_MAIN, "lbl_assignGrants_assigned"); }

        override protected function getNotAssignedLabel():String { return I18nUtil.trans(I18nUtil.APP_MAIN, "lbl_assignGrants_not_assigned"); }

    }
}

