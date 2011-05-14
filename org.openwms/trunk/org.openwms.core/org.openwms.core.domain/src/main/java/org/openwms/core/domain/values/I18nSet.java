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

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * An I18nSet. Encapsulates different languages. Used as an embedded value type
 * in {@link org.openwms.core.domain.system.I18n}
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 * @see org.openwms.core.domain.system.I18n
 */
@Embeddable
public class I18nSet {

    /**
     * Length of each column.
     */
    public static final int LENGTH = 1024;
    @Column(name = "EN_US", length = LENGTH)
    private String en_US;
    @Column(name = "DE_DE", length = LENGTH)
    private String de_DE;
    @Column(name = "FR_FR", length = LENGTH)
    private String fr_FR;

    /**
     * Create a new I18nSet.
     */
    protected I18nSet() {}

    /**
     * Create a new I18nSet.
     * 
     * @param en_US
     *            US English language
     * @param de_DE
     *            German Germany language
     * @param fr_FR
     *            French language
     */
    public I18nSet(String en_US, String de_DE, String fr_FR) {
        super();
        this.en_US = en_US;
        this.de_DE = de_DE;
        this.fr_FR = fr_FR;
    }

    /**
     * Get the de_DE.
     * 
     * @return the de_DE.
     */
    public String getDe_DE() {
        return de_DE;
    }

    /**
     * Get the en_US.
     * 
     * @return the en_US.
     */
    public String getEn_US() {
        return en_US;
    }

    /**
     * Get the fr_FR.
     * 
     * @return the fr_FR.
     */
    public String getFr_FR() {
        return fr_FR;
    }
}
