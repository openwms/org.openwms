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
package org.openwms.core.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * An instance of ResponseVO is a transfer object that is used to encapsulate server responses to the calling client application. It
 * contains an arrays of {@link ResponseItem}s that encapsulate the response actually. This class this therefore only a {@link Serializable}
 * wrapper to a collection of {@link ResponseItem}s.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
public class ResponseVO implements Serializable {

    private static final long serialVersionUID = -73613607195853087L;

    /** Direct access to the encapsulated server responses. */
    public List<ResponseItem> items = new ArrayList<>(0);

    /**
     * Create a new ResponseVO.
     */
    public ResponseVO() {}

    /**
     * Create a new ResponseVO.
     * 
     * @param pMessage
     *            The message text
     * @param httpStatus
     *            The status of the response
     */
    public ResponseVO(String pMessage, HttpStatus httpStatus) {
        items.add(new ItemBuilder().wMessage(pMessage).wStatus(httpStatus).build());
    }

    /**
     * Create a new ResponseVO.
     * 
     * @param pItems
     *            An initial array of items to wrap
     */
    public ResponseVO(ResponseItem... pItems) {
        items.addAll(Arrays.asList(pItems));
    }

    /**
     * Add a {@link ResponseItem} to the array of items.
     * 
     * @param pItem
     *            The item to add
     * @return This
     */
    public ResponseVO add(ResponseItem pItem) {
        items.add(pItem);
        return this;
    }

    public static class ItemBuilder {
        private ResponseItem item;

        /**
         * Create a new ResponseVO.ItemBuilder.
         * 
         */
        public ItemBuilder() {
            item = new ResponseItem();
        }

        public ItemBuilder wMessage(String pMessage) {
            item.message = pMessage;
            return this;
        }

        public ItemBuilder wMessageKey(String pMessageKey) {
            item.messageKey = pMessageKey;
            return this;
        }

        public ItemBuilder wStatus(HttpStatus pHttpStatus) {
            item.httpStatus = pHttpStatus;
            return this;
        }

        public ItemBuilder wParams(Serializable... param) {
            item.obj = param;
            return this;
        }

        public ResponseItem build() {
            return item;
        }
    }

    /**
     * A ResponseItem is the direct wrapper to encapsulate server response data that is transfered to the client.
     * 
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision: $
     * @since 0.1
     */
    public static class ResponseItem {
        /** A text message to transfer as server response. */
        public String message = "";
        /** A unique key to identify a particular message. Note that this key can relate to the wrapped <tt>message</tt>, but it might not. */
        public String messageKey = "";
        /** An array ob objects that can be passed to the client to identify failure items. */
        public Serializable obj[];
        /** A http status code for this item. */
        public HttpStatus httpStatus;

        /**
         * Create a new ResponseVO.ResponseItem.
         * 
         * @param pMessage
         *            A text message to transfer as server response
         * @param pMessageKey
         *            A unique key to identify a particular message
         */
        public ResponseItem(String pMessage, String pMessageKey, HttpStatus pHttpStatus, Serializable... pObj) {
            message = pMessage;
            messageKey = pMessageKey;
            httpStatus = pHttpStatus;
            obj = Arrays.copyOf(pObj, pObj.length);
        }

        /**
         * Create a new ResponseItem.
         */
        public ResponseItem() {}
    }
}
