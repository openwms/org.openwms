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
package org.openwms.core.domain.values;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * An I18nSet encapsulates different languages. Used as an embedded value type
 * in {@link org.openwms.core.domain.system.I18n}.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.domain.system.I18n
 */
@Embeddable
public class I18nSet implements Serializable {

    private static final long serialVersionUID = 2259073810167027049L;
    /**
     * Length of each column.
     */
    public static final int LENGTH = 1024;
    /**
     * American English language.
     */
    @Column(name = "EN_US", length = LENGTH)
    private String enUs;
    /**
     * German language.
     */
    @Column(name = "DE_DE", length = LENGTH)
    private String deDe;
    /**
     * French language.
     */
    @Column(name = "FR_FR", length = LENGTH)
    private String frFr;

    /**
     * Create a new I18nSet.
     */
    protected I18nSet() {
        super();
    }

    /**
     * Create a new I18nSet.
     * 
     * @param enUs
     *            American English language
     * @param deDe
     *            German language
     * @param frFr
     *            French language
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