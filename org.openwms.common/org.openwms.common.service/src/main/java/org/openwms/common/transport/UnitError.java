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
package org.openwms.common.transport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.openwms.core.AbstractEntity;
import org.openwms.core.DomainObject;

/**
 * An UnitError represents an error occurring on <code>TransportUnit</code>s, on <code>LoadUnit</code>s or others.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Entity
@Table(name = "COM_UNIT_ERROR")
public class UnitError extends AbstractEntity<Long> implements DomainObject<Long> {

    private static final long serialVersionUID = -716902051194734598L;

    /**
     * Unique technical key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    /**
     * Error number.
     */
    @Column(name = "ERROR_NO")
    private String errorNo;

    /**
     * Error message text.
     */
    @Column(name = "ERROR_TEXT")
    private String errorText;

    /**
     * Version field.
     */
    @Version
    @Column(name = "C_VERSION")
    private long version;

    /* ----------------------------- methods ------------------- */
    /**
     * Create a new <code>UnitError</code>.
     */
    public UnitError() {
        super();
    }

    /**
     * Create a new <code>UnitError</code> with an error number.
     * 
     * @param errorNo
     *            The error number
     */
    public UnitError(String errorNo) {
        this.errorNo = errorNo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNew() {
        return this.id == null;
    }

    /**
     * Return the error number.
     * 
     * @return The error number
     */
    public String getErrorNo() {
        return errorNo;
    }

    /**
     * Set the error number.
     * 
     * @param errorNo
     *            The errorNo to set.
     */
    public void setErrorNo(String errorNo) {
        this.errorNo = errorNo;
    }

    /**
     * Return the error text.
     * 
     * @return The error text
     */
    public String getErrorText() {
        return errorText;
    }

    /**
     * Set the error text.
     * 
     * @param errorText
     *            The errorText to set.
     */
    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getVersion() {
        return version;
    }
}