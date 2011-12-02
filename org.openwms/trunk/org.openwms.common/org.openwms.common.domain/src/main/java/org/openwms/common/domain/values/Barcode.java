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
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

import org.apache.commons.lang.StringUtils;

/**
 * A Barcode is a printable item with an unique identifier to label
 * <code>TransportUnit</code>s. The identifier has a defined number of
 * characters whereas these characters are aligned either left or right. Non
 * filled positions of a Barcode are padded with a so called padding character.
 * 
 * @GlossaryTerm
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Embeddable
public class Barcode implements Serializable {

    private static final long serialVersionUID = 7743388968799876214L;

    /**
     * A BARCODE_ALIGN defines whether the <code>Barcode</code> is applied
     * <code>LEFT</code> or <code>RIGHT</code>.
     * <p>
     * Only be used when padding is activated.
     * </p>
     * 
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public static enum BARCODE_ALIGN {
        /**
         * Barcode is left aligned.
         */
        LEFT,
        /**
         * Barcode is right aligned.
         */
        RIGHT
    }

    /**
     * Define whether to use character padding or not.
     */
    private static boolean padded = true;

    /**
     * Defines a character used for padding.<br>
     * If the actually length of the <code>Barcode</code> is less than the
     * maximum defined <code>length</code> the rest will be filled with
     * <code>padder</code> characters.
     */
    private static char padder = '0';

    /**
     * Defines the maximum length of characters.
     */
    private static int length = 20;

    /**
     * The alignment of the <code>Barcode</code>. Could be something of
     * {@link BARCODE_ALIGN}.
     */
    private static BARCODE_ALIGN alignment = BARCODE_ALIGN.RIGHT;

    /**
     * 'Identifier' of the <code>Barcode</code>.
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
    private Barcode() {
        super();
    }

    /**
     * Create a new <code>Barcode</code> with a String.
     * 
     * @param value
     *            The value of the <code>Barcode</code> as String
     * @throws IllegalArgumentException
     *             when the value is set to <code>null</code>.
     */
    public Barcode(String value) {
        adjustBarcode(value);
    }

    @SuppressWarnings("unused")
    @PostPersist
    @PostUpdate
    private void init() {
        adjustBarcode(value);
    }

    /**
     * Force the Barcode to be aligned to the determined rules regarding
     * padding, alignment.
     * 
     * @param val
     *            The old Barcode as String
     * @return The new aligned Barcode
     */
    public String adjustBarcode(String val) {
        if (val == null) {
            throw new IllegalArgumentException("Cannot create a barcode without value");
        }
        if (isPadded()) {
            this.value = (alignment == BARCODE_ALIGN.RIGHT) ? StringUtils.leftPad(val, length, padder) : StringUtils
                    .rightPad(val, length, padder);
        } else {
            this.value = val;
        }
        return this.value;
    }

    /**
     * Returns the alignment.
     * 
     * @return The alignment
     */
    public static BARCODE_ALIGN getAlignment() {
        return alignment;
    }

    /**
     * Set the alignment.
     * 
     * @param align
     *            The alignment to set
     */
    public static void setAlignment(BARCODE_ALIGN align) {
        alignment = align;
    }

    /**
     * Check if <code>Barcode</code> is padded.
     * 
     * @return <code>true</code> if <code>Barcode</code> is padded, otherwise
     *         <code>false</code>.
     */
    public static boolean isPadded() {
        return padded;
    }

    /**
     * Set padded.
     * 
     * @param p
     *            <code>true</code> if <code>Barcode</code> should be padded,
     *            otherwise <code>false</code>.
     */
    static void setPadded(boolean p) {
        padded = p;
    }

    /**
     * Return the padding character.
     * 
     * @return The padding character.
     */
    public static char getPadder() {
        return padder;
    }

    /**
     * Set the padding character.
     * 
     * @param p
     *            The padding character to use
     */
    static void setPadder(char p) {
        padder = p;
        padded = true;
    }

    /**
     * Return the <code>Barcode</code> value.
     * 
     * @return The value of the <code>Barcode</code>
     */
    public String getValue() {
        return value;
    }

    /**
     * Set the <code>Barcode</code> value.
     * 
     * @param value
     *            The value to set
     */
    public void setValue(String value) {
        adjustBarcode(value);
    }

    /**
     * Return the length.
     * 
     * @return The length
     */
    public static int getLength() {
        return length;
    }

    /**
     * Set the length.
     * 
     * @param l
     *            The length to set
     */
    static void setLength(int l) {
        length = l;
    }

    /**
     * Return the value of the <code>Barcode</code> as String.
     * 
     * @see java.lang.Object#toString()
     * @return As String
     */
    @Override
    public String toString() {
        return value;
    }
}