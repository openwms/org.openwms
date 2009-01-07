/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain.values;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * A Barcode.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
@Embeddable
public class Barcode implements Serializable {

    /**
     * The <tt>BARCODE_ALIGN</tt> defines whether the <tt>Barcode</tt> is applied <tt>LEFT</tt> or <tt>RIGHT</tt>.
     * Only be used when padding is active.
     */
    public static enum BARCODE_ALIGN {
	LEFT, RIGHT
    }

    private static final long serialVersionUID = 1L;

    /**
     * Define whether use character padding or not.
     */
    private static boolean padded = false;

    /**
     * Defines a character used for padding.<br>
     * If the actually length of the <tt>Barcode</tt> is less than the defined maximum <tt>length</tt> the rest will
     * be filled with <tt>padder</tt> characters.
     * 
     */
    private static char padder;

    /**
     * Defines the maximum character length of the <tt>Barcode</tt>.
     */
    private static int length;

    /**
     * The alignment of the <tt>Barcode</tt> can be set to {@link BARCODE_ALIGN}.
     */
    private static BARCODE_ALIGN alignment = BARCODE_ALIGN.RIGHT;

    @Column(name = "BARCODE")
    private String id;

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    @SuppressWarnings("unused")
    private Barcode() {}

    /**
     * 
     * Create a new Barcode with an Id.
     * 
     * @param id
     */
    public Barcode(String id) {
	if (id == null) {
	    throw new IllegalArgumentException("Cannot create a barcode without id");
	}
	this.id = id;
	if (isPadded()) {
	    this.id = (alignment == BARCODE_ALIGN.RIGHT) ? StringUtils.leftPad(id, length, padder) : StringUtils
		    .rightPad(id, length, padder);
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
     * @param a -
     *                The alignment to set.
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
     * @param p -
     *                The padded to set.
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
     * @param p -
     *                The padder to set.
     */
    public static void setPadder(char p) {
	padder = p;
	padded = true;
    }

    /**
     * Get the id.
     * 
     * @return the id.
     */
    public String getId() {
	return id;
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
     * @param l -
     *                The length to set.
     */
    public static void setLength(int l) {
	length = l;
    }

    @Override
    public String toString() {
	return id;
    }

}
