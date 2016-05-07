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
package org.openwms.core.search;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the
 * org.openwms.core.domain.search package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation for XML content. The Java
 * representation of XML content can consist of schema derived interfaces and classes representing the binding of schema type definitions,
 * element declarations and model groups. Factory methods for each of these are provided in this class.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.2
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName ACTIONS_QNAME = new QName("http://www.openwms.org/schema/ui-actions-schema", "actions");

    /**
     * Create an instance of {@link Tag }.
     * 
     * @return A new Tag
     */
    public Tag createTag() {
        return new Tag();
    }

    /**
     * Create an instance of {@link Tags }.
     * 
     * @return A new Tags
     */
    public Tags createTags() {
        return new Tags();
    }

    /**
     * Create an instance of {@link Actions }.
     * 
     * @return A new Actions
     */
    public Actions createActions() {
        return new Actions();
    }

    /**
     * Create an instance of {@link Action }.
     * 
     * @return A new Action
     */
    public Action createAction() {
        return new Action();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Actions }{@code >} .
     * 
     * @param value
     *            An Actions object
     * @return A wrapped Actions as JAXBElement
     */
    @XmlElementDecl(namespace = "http://www.openwms.org/schema/ui-actions-schema", name = "actions")
    public JAXBElement<Actions> createActions(Actions value) {
        return new JAXBElement<Actions>(ACTIONS_QNAME, Actions.class, null, value);
    }
}