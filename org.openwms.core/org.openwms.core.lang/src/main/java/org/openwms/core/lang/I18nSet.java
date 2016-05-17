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
package org.openwms.core.lang;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * An I18nSet encapsulates different languages. Used as an embedded value type in {@link I18n}.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.2
 * @see org.openwms.core.lang.I18n
 * @since 0.1
 */
@Embeddable
public class I18nSet implements Serializable {

    /** Length of each column. */
    public static final int LENGTH = 1024;
    /** American English language. */
    @Column(name = "C_EN_US", length = LENGTH)
    private String enUs;
    /** German language. */
    @Column(name = "C_DE_DE", length = LENGTH)
    private String deDe;
    /** French language. */
    @Column(name = "C_FR_FR", length = LENGTH)
    private String frFr;

    /** Dear JPA... */
    protected I18nSet() {
        super();
    }

    /**
     * Create a new I18nSet.
     *
     * @param enUs American English language
     * @param deDe German language
     * @param frFr French language
     */
    public I18nSet(String enUs, String deDe, String frFr) {
        super();
        this.enUs = enUs;
        this.deDe = deDe;
        this.frFr = frFr;
    }

    /**
     * Get the German translation.
     *
     * @return the deDe property
     */
    public String getDeDe() {
        return deDe;
    }

    /**
     * Get the American English translation.
     *
     * @return the enUs property
     */
    public String getEnUs() {
        return enUs;
    }

    /**
     * Get the French translation.
     *
     * @return the frFr property
     */
    public String getFrFr() {
        return frFr;
    }
}