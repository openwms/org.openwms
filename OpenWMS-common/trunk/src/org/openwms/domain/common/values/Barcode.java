/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common.values;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.StringUtils;

@Embeddable
public class Barcode implements Serializable{

	public static enum BARCODE_ALIGN {
		LEFT, RIGHT
	}
	
	private static final long serialVersionUID = 1L;

	private static boolean padded = false;
	private static char padder;
	private static int length;
	private static BARCODE_ALIGN alignment = BARCODE_ALIGN.RIGHT;

	@Column(name = "BARCODE")
	private String id;

	/* ----------------------------- methods ------------------- */
	/**
	 * Accessed by persistence provider.
	 */
	@SuppressWarnings("unused")
	private Barcode() {
	}

	public Barcode(String id) {
		assert (id != null);
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
	public BARCODE_ALIGN getAlignment() {
		return alignment;
	}

	/**
	 * Set the alignment.
	 * 
	 * @param a
	 *            The alignment to set.
	 */
	public static void setAlignment(BARCODE_ALIGN a) {
		alignment = a;
	}

	/**
	 * Get the padded.
	 * 
	 * @return the padded.
	 */
	public boolean isPadded() {
		return padded;
	}

	/**
	 * Set the padded.
	 * 
	 * @param p
	 *            The padded to set.
	 */
	public static void setPadded(boolean p) {
		padded = p;
	}

	/**
	 * Get the padder.
	 * 
	 * @return the padder.
	 */
	public char getPadder() {
		return padder;
	}

	/**
	 * Set the padder.
	 * 
	 * @param p
	 *            The padder to set.
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
	public int getLength() {
		return length;
	}

	/**
	 * Set the length.
	 * 
	 * @param l
	 *            The length to set.
	 */
	public static void setLength(int l) {
		length = l;
	}

	@Override
	public String toString() {
		return id;
	}

}
