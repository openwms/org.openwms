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
package org.openwms.web.flex.client.component {

    import flash.events.KeyboardEvent;
    import flash.ui.Keyboard;
    import mx.collections.ArrayCollection;
    import mx.controls.ComboBox;
    import mx.core.UITextField;

    /**
     * A SuggestComboBox.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class SuggestComboBox extends ComboBox {

        public var searchFromBeginning : Boolean = false;
        private var searchRegEx : RegExp;
        private var dropdownCreated : Boolean;

        /**
         * Constructor.
         */
        public function SuggestComboBox() {
            super();
            dropdownCreated = false;
            this.editable = true;
            this.text = "";
            this.addEventListener(KeyboardEvent.KEY_UP, onKeyUp);
        }

        private function onKeyUp(event : KeyboardEvent) : void {
            if (isAlphaChar(event.keyCode)) {
                var textBox : UITextField = UITextField(event.target);
                var searchText : String = event.target.text;
                searchRegEx = new RegExp(textBox.text, 'i');
                ArrayCollection(this.dataProvider).filterFunction = filter;
                ArrayCollection(this.dataProvider).refresh();
                this.open();
                textBox.text = searchText;
                textBox.setSelection(searchText.length, searchText.length);
            }

            if (event.keyCode == Keyboard.ESCAPE) {
                this.text = "";
                this.setFocus();
            }
        }

        private function filter(item : Object) : Boolean {
            var found : Boolean = false;
            if (searchFromBeginning) {
                if (item is String) {
                    found = (item.search(searchRegEx) == 0);
                } else {
                    found = (String(item[this["labelField"]]).search(searchRegEx) == 0);
                }
            } else {
                if (item is String) {
                    found = (item.search(searchRegEx) >= 0);
                } else {
                    found = (String(item[this["labelField"]]).search(searchRegEx) >= 0);
                }
            }
            return found;
        }

        private function isAlphaChar(keyCode : int) : Boolean {
            var isAlpha : Boolean = false;
            if ((keyCode > 47 && keyCode < 58) || (keyCode > 64 && keyCode < 91) || (keyCode > 96 && keyCode < 123) || (keyCode == Keyboard.BACKSPACE) || (keyCode == Keyboard.DELETE)) {
                isAlpha = true;
            }
            return isAlpha;
        }
    }
}