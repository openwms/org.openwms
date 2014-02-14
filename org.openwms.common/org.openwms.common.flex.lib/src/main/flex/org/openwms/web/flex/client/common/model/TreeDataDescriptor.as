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
package org.openwms.web.flex.client.common.model {
    import mx.collections.ArrayCollection;
    import mx.collections.CursorBookmark;
    import mx.collections.ICollectionView;
    import mx.collections.IViewCursor;
    import mx.events.CollectionEvent;
    import mx.events.CollectionEventKind;
    import mx.controls.treeClasses.*;

    /**
     * A TreeDataDescriptor.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class TreeDataDescriptor implements mx.controls.treeClasses.ITreeDataDescriptor {
        
        /**
         * Constructor.
         */
        public function TreeDataDescriptor() { }

        public function getChildren(node:Object, model:Object = null):ICollectionView {
            try {
                if (node is Object) {
                    if (node.children is ArrayCollection) {
                        return node.children;
                    }
                    else {
                        return new ArrayCollection(node.children);
                    }
                }
            }
            catch (e:Error) {
                trace("[Descriptor] exception checking for getChildren");
            }
            return null;
        }

        public function isBranch(node:Object, model:Object = null):Boolean {
            try {
                if (node is Object) {
                    if (node.children != null) {
                        return true;
                    }
                }
            }
            catch (e:Error) {
                trace("[Descriptor] exception checking for isBranch");
            }
            return false;
        }

        public function hasChildren(node:Object, model:Object = null):Boolean {
            if (node == null)
                return false;
            var children:ICollectionView = getChildren(node, model);
            try {
                if (children.length > 0)
                    return true;
            }
            catch (e:Error) {
            }
            return false;
        }

        public function getData(node:Object, model:Object = null):Object {
            try {
                return node;
            }
            catch (e:Error) {
            }
            return null;
        }

        public function addChildAt(parent:Object, child:Object, index:int, model:Object = null):Boolean {
            var event:CollectionEvent = new CollectionEvent(CollectionEvent.COLLECTION_CHANGE);
            event.kind = CollectionEventKind.ADD;
            event.items = [child];
            event.location = index;
            if (!parent) {
                var iterator:IViewCursor = model.createCursor();
                iterator.seek(CursorBookmark.FIRST, index);
                iterator.insert(child);
            }
            else if (parent is Object) {
                if (parent.children != null) {
                    if (parent.children is ArrayCollection) {
                        parent.children.addItemAt(child, index);
                        if (model) {
                            model.dispatchEvent(event);
                            model.itemUpdated(parent);
                        }
                        return true;
                    }
                    else {
                        parent.children.splice(index, 0, child);
                        if (model)
                            model.dispatchEvent(event);
                        return true;
                    }
                }
            }
            return false;
        }

        public function removeChildAt(parent:Object, child:Object, index:int, model:Object = null):Boolean {
            var event:CollectionEvent = new CollectionEvent(CollectionEvent.COLLECTION_CHANGE);
            event.kind = CollectionEventKind.REMOVE;
            event.items = [child];
            event.location = index;

            if (!parent) {
                var iterator:IViewCursor = model.createCursor();
                iterator.seek(CursorBookmark.FIRST, index);
                iterator.remove();
                if (model)
                    model.dispatchEvent(event);
                return true;
            }
            else if (parent is Object) {
                if (parent.children != undefined) {
                    if (child == null) {
                        parent.children.splice(0, parent.children.length);
                    }
                    else {
                        parent.children.splice(index, 1);
                    }
                    if (model)
                        model.dispatchEvent(event);
                    return true;
                }
            }
            return false;
        }
    }
}