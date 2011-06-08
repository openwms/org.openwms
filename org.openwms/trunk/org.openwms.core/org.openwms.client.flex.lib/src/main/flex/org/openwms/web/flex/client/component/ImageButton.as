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
package org.openwms.web.flex.client.component {

    import flash.events.Event;
    import flash.events.MouseEvent;
    import flash.filters.BitmapFilter;
    import flash.filters.ColorMatrixFilter;
    import mx.controls.Image;

    [Bindable]
    /**
     * An ImageButton is an extension of the common Image enhanced by the
     * feature to enable and disable the command functionality.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision: 1415 $
     * @since 0.1
     */
    public class ImageButton extends Image {

        private var enabledChanged:Boolean = false;

        /**
         * Constructor.
         */
        public function ImageButton() {
            super();
            addEventListener(MouseEvent.CLICK, onClick);
            addEventListener("enabledChanged", onEnabled);
            buttonMode = true;
        }

        /**
         * Modify original image via a filter whenever the enabled property changes.
         *
         * @param event: unused
         */
        private function onEnabled(event : Event):void {
            var filter:BitmapFilter = new ColorMatrixFilter(matrix);
            var matrix:Array = new Array();

            if (enabled) {
                matrix = matrix.concat([1, 0, 0, 0, 0]);
                matrix = matrix.concat([0, 1, 0, 0, 0]);
                matrix = matrix.concat([0, 0, 1, 0, 0]);
                matrix = matrix.concat([0, 0, 0, 1, 0]);
            }
            else {
                matrix = matrix.concat([0.31, 0.61, 0.08, 0, 0]);
                matrix = matrix.concat([0.31, 0.61, 0.08, 0, 0]);
                matrix = matrix.concat([0.31, 0.61, 0.08, 0, 0]);
                matrix = matrix.concat([0, 0, 0, 1, 0]);
            }
            filters = new Array(filter) ;
            buttonMode = enabled ;
        }

        private function onClick(event : MouseEvent):void {
            if (!enabled) {
                event.stopImmediatePropagation();
                return;
            }
        }
    }
}


