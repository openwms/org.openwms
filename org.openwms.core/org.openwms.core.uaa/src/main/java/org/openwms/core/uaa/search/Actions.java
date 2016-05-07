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
package org.openwms.core.uaa.search;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates a collection of {@link Action}s and is mapped to the XML type "actions" in the ui-actions-schema.xsd. <p> <a
 * href="http://www.openwms.org/schema/ui-actions-schema.xsd">http://www. openwms.org/schema/ui-actions-schema.xsd</a> </p>
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.2
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "actions", propOrder = {"action"})
public class Actions implements Serializable {

    private static final long serialVersionUID = -8134374894573948880L;
    /**
     * A List of all {@link Action}s.
     */
    protected List<Action> action;
    /**
     * The owning module of the set of {@link Action}s.
     */
    @XmlAttribute
    protected String owner;

    /**
     * Gets the value of the action property.
     * <p>
     * <p>
     * This acessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list
     * will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the action property.
     * <p>
     * <p>
     * For example, to add a new item, do as follows:
     * <p>
     * <pre>
     * getAction().add(newItem);
     * </pre>
     * <p>
     * <p>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Action }
     *
     * @return The list of {@link Action}s
     */
    public List<Action> getAction() {
        if (action == null) {
            action = new ArrayList<Action>();
        }
        return action;
    }

    /**
     * Gets the owner of this property.
     *
     * @return possible object is {@link String }
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the owner of this property.
     *
     * @param owner allowed object is {@link String }
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }
}