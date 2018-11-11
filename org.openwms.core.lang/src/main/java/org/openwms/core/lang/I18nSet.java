/*
 * Copyright 2018 Heiko Scherrer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openwms.core.lang;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * An I18nSet encapsulates different languages. Used as an embedded value type in {@link I18n}.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
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