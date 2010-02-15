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
package org.openwms.common.domain.values;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.StringUtils;

/**
 * A Barcode.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Embeddable
public class Barcode implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A BARCODE_ALIGN.
     * <p>
     * The {@link BARCODE_ALIGN} defines whether the {@link Barcode} is applied
     * <code>LEFT</code> or <code>RIGHT</code>. Only be used when padding is
     * activated.
     * </p>
     * 
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public static enum BARCODE_ALIGN {
        LEFT, RIGHT
    }

    /**
     * Define whether to use character padding or not.
     */
    private static boolean padded = false;

    /**
     * Defines a character used for padding.<br>
     * If the actually length of the {@link Barcode} is less than the maximum
     * defined <code>length</code> the rest will be filled with
     * <code>padder</code> characters.
     */
    private static char padder;

    /**
     * Defines the maximum length of characters.
     */
    private static int length;

    /**
     * The alignment of the {@link Barcode}. Could be set to
     * {@link BARCODE_ALIGN}.
     */
    private static BARCODE_ALIGN alignment = BARCODE_ALIGN.RIGHT;

    /**
     * "Identifier" of the {@link Barcode}.
     * <p>
     * <i>Note:</i>It is not guaranteed that this field must be unique.
     * </p>
     */
    @Column(name = "BARCODE")
    private String value;

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    @SuppressWarnings("unused")
    private Barcode() {}

    /**
     * Create a new Barcode with a String.
     * <p>
     * <i>Note:</i>An {@link IllegalArgumentException} is thrown when the value
     * is set to <code>null</code>.
     * </p>
     * 
     * @param value
     *            - Value of the {@link Barcode} as String.
     */
    public Barcode(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot create a barcode without value");
        }
        if (isPadded()) {
            this.value = (alignment == BARCODE_ALIGN.RIGHT) ? StringUtils.leftPad(value, length, padder) : StringUtils
                    .rightPad(value, length, padder);
        } else {
            this.value = value;
        }
    }

    /**
     * Get the alignment.
     * 
     * @return the alignment.
     */
    public static BARCODE_ALIGN getAlignment() {
        return alignment;
    }

    /**
     * Set the alignment.
     * 
     * @param a
     *            - The alignment to set.
     */
    public static void setAlignment(BARCODE_ALIGN a) {
        alignment = a;
    }

    /**
     * Get the padded.
     * 
     * @return the padded.
     */
    public static boolean isPadded() {
        return padded;
    }

    /**
     * Set the padded.
     * 
     * @param p
     *            - The padded to set.
     */
    public static void setPadded(boolean p) {
        padded = p;
    }

    /**
     * Get the padder.
     * 
     * @return the padder.
     */
    public static char getPadder() {
        return padder;
    }

    /**
     * Set the padder.
     * 
     * @param p
     *            - The padder to set.
     */
    public static void setPadder(char p) {
        padder = p;
        padded = true;
    }

    /**
     * Get the {@link Barcode} value.
     * 
     * @return - The value of the {@link Barcode}..
     */
    public String getValue() {
        return value;
    }

    /**
     * Get the length.
     * 
     * @return the length.
     */
    public static int getLength() {
        return length;
    }

    /**
     * Set the length.
     * 
     * @param l
     *            - The length to set.
     */
    public static void setLength(int l) {
        length = l;
    }

    /**
     * Return the value of the {@link Barcode} as String.
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return value;
    }
}