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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A Tag represents a search keyword in the OpenWMS.org ActionSearch concept. Each {@link Action} consists of multiple Tags.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.2
 * @GlossaryTerm
 * @since 0.2
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tag")
public class Tag implements Serializable {

    /**
     * The unique name of this Tag.
     */
    @XmlAttribute(required = true)
    private String name;

    /**
     * Other possible names for this tag. Used for internationalization.
     */
    private List<String> aliases = new ArrayList<>();

    /* ----------------------------- constructors ------------------- */

    /**
     * Accessed by JAXB.
     */
    public Tag() {
        super();
    }

    /**
     * Create a new Tag.
     *
     * @param name The initial name
     */
    protected Tag(String name) {
        super();
        this.name = name;
    }

    /* ----------------------------- methods ------------------- */

    /**
     * Get the aliases.
     *
     * @return the aliases.
     */
    public List<String> getAliases() {
        return aliases;
    }

    /**
     * Set the aliases.
     *
     * @param aliases The aliases to set.
     */
    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    /**
     * Get the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Find the given String in the name of the Tag.
     *
     * @param word The String to search for
     * @return 0 if {@code name} is not part of the tag name, otherwise some positive value
     */
    public int matches(String word) {
        return name.toLowerCase().indexOf(word.toLowerCase()) + 1;
    }
}