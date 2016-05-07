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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * An Action represents a possible UI action an User can take. Each Action has a resulting URL to a webpage and a descriptive text that is
 * displayed in the UI. Additionally a field <code>weight</code> is used to count how many times the User has chosen this Action.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @GlossaryTerm
 * @since 0.2
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "action", propOrder = {"tags"})
public class Action implements Serializable {

    private static final long serialVersionUID = 760579768628332750L;

    /**
     * The unique name of this Action.
     */
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    private String id;

    @XmlAttribute(required = true)
    private String url;

    /**
     * Text full text of the Action that is displayed in the UI.
     */
    @XmlAttribute(required = true)
    private String text;

    /**
     *
     */
    @XmlTransient
    private int weight = 0;

    /**
     * An Action has one ore more Tags. With Tags an Action can be found.
     */
    @XmlElementWrapper(name = "tags")
    @XmlElement(name = "tag")
    // FIXME [scherrer] : This list is not resolved by JAXB
    private List<Tag> tags = new ArrayList<Tag>();

    /* ----------------------------- constructors ------------------- */

    /**
     * Create a new Action.
     */
    public Action() {
        super();
    }

    /* ----------------------------- methods ------------------- */

    /**
     * Calculate and return a rating for a list of words. Every match between a word and one of the tags, increases the rating. In case none
     * of the tags matches, 0 is returned.
     *
     * @param words The Array of words to calculate the rating
     * @return 0 when none of the words matches, or any other positive value in case of matches
     */
    public int rate(String... words) {
        int result = 0;
        for (int i = 0; i < words.length; i++) {
            for (Tag tag : tags) {
                if (tag.matches(words[i]) > 0) {
                    result++;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Get the name.
     *
     * @return the name.
     */
    public String getName() {
        return id;
    }

    /**
     * Set the name.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.id = name;
    }

    /**
     * Get the name.
     *
     * @return the name.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the name.
     *
     * @param id The name to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the url.
     *
     * @return the url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the url.
     *
     * @param url The url to set.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get the text.
     *
     * @return the text.
     */
    public String getText() {
        return text;
    }

    /**
     * Set the text.
     *
     * @param text The text to set.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Get the weight.
     *
     * @return the weight.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Set the weight.
     *
     * @param weight The weight to set.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Increase weight by 1 and return the new weight.
     *
     * @param The weight increased by 1
     */
    public int increaseWeight() {
        return weight++;
    }

    /**
     * Decrease weight by 1 and return the new weight.
     *
     * @param The weight decreased by 1
     */
    public int decreaseWeight() {
        return --weight;
    }

    /**
     * Get the tags.
     *
     * @return the tags.
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * Set the tags.
     *
     * @param tags The tags to set.
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new StringBuilder().append(getName()).append(getText()).append(getUrl()).toString();
    }

    /**
     * A Builder class for <code>Action</code>s.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.2
     */
    public static class Builder {

        private Action action;

        /**
         * Create a new Action.Builder.
         *
         * @param name An initial name
         */
        public Builder(String name) {
            action = new Action();
            action.setName(name);
        }

        /**
         * Add a text to the Action.
         *
         * @param text The text
         * @return The Builder
         */
        public Builder withText(String text) {
            action.setText(text);
            return this;
        }

        /**
         * Add a Tag to the Action.
         *
         * @param tag The Tag
         * @return The Builder
         */
        public Builder withTag(Tag tag) {
            action.getTags().add(tag);
            return this;
        }

        /**
         * Build and return the Action.
         *
         * @return The Action
         */
        public Action build() {
            return action;
        }
    }
}