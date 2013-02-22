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
package org.openwms.web.flex.client.util {

    import flash.events.KeyboardEvent;

    import mx.binding.utils.BindingUtils;
    import mx.binding.utils.ChangeWatcher;
    import mx.collections.ArrayCollection;
    import mx.containers.ViewStack;
    import mx.core.UIComponent;
    import mx.managers.PopUpManager;

    /**
     * A DisplayUtility class used by UIComponents for switch screen handling or property bindings.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public final class DisplayUtility {

        /**
         * Constructor.
         */
        public function DisplayUtility() {
        }

        /**
         * Resolve the view with a given <code>viewId</code> from a <code>viewStack</code>.
         *
         * @param viewId The id of the the view to resolve
         * @param viewStack The ViewStack where the view should be resolved from
         * @return ViewStack index
         */
        public static function getView(viewId : String, viewStack : ViewStack) : int {
            if (viewStack.getChildByName(viewId) == null) {
                return -1;
            }
            return viewStack.getChildIndex(viewStack.getChildByName(viewId));
        }

        /**
         * Common used funtion to close a PopUp dialogue.
         *
         * @param dialogue The PopUp dialogue to close
         * @param keyEventHandler The listener function to remove from the list of listeners
         */
        public static function closeDialog(dialogue : UIComponent, keyEventHandler : Function) : void {
            dialogue.removeEventListener(KeyboardEvent.KEY_DOWN, keyEventHandler);
            PopUpManager.removePopUp(dialogue);
        }

        /**
         * Encapsulates the boring binding and un-binding stuff.
         *
         * @param bindings A list of bindings to bind
         * @param command Optional provide an anonymous function to be executed between binding and unbinding
         */
        public static function bindProperties(bindings : ArrayCollection, command : Function=null) : void {
            var watchers : ArrayCollection = new ArrayCollection();
            for each (var binding : BindingProperty in bindings) {
                if (binding == null) {
                    continue;
                }
                if (binding.clazz == null) {
                    watchers.addItem(BindingUtils.bindProperty(binding.site, binding.sitePropertyName, binding.host, binding.hostPropertyName));
                } else {
                    watchers.addItem(BindingUtils.bindProperty(binding.site, binding.sitePropertyName, binding.host as binding.clazz, binding.hostPropertyName));
                }
            }
            if (command != null) {
                try {
                    command();
                } catch (e : Error) {
                    trace("Error during executing the command:" + e.message);
                    throw e;
                } finally {
                    for each (var watcher : ChangeWatcher in watchers) {
                        watcher.unwatch();
                    }
                    return;
                }
            }
            for each (var watcher : ChangeWatcher in watchers) {
                watcher.unwatch();
            }
        }
    }
}