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

import java.io.Serializable;

/**
 * A VerbalTag represents a verb.
 * 
 * @GlossaryTerm
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.2
 */
public class VerbTag extends Tag implements Serializable {

    private static final long serialVersionUID = -3010950207559198785L;

    /* ----------------------------- constructors ------------------- */
    /**
     * Create a new VerbTag.
     */
    public VerbTag() {
        super();
    }

    /**
     * Create a new VerbTag.
     * 
     * @param name
     *            The initial name
     */
    public VerbTag(String name) {
        super(name);
    }

    /* ----------------------------- methods ------------------- */
    /**
     * A Builder class for <code>VerbTags</code>.
     * 
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.2
     */
    public static class Builder {

        private VerbTag tag;

        /**
         * Constructor.
         * 
         * @param name
         *            Tags name
         */
        public Builder(String name) {
            tag = new VerbTag(name);
        }

        /**
         * Add an alias to the <code>VerbTags</code>.
         * 
         * @param alias
         *            The alias to add
         * @return The builder
         */
        public Builder withAlias(String alias) {
            tag.getAliases().add(alias);
            return this;
        }

        /**
         * Build and return the <code>VerbTags</code>.
         * 
         * @return The <code>VerbTags</code>
         */
        public VerbTag build() {
            return tag;
        }
    }
}