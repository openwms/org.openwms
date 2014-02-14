/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
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
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.persistence.ext.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import org.openwms.common.domain.units.Piece;
import org.openwms.common.domain.units.PieceUnit;

/**
 * A PieceUserType.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 * @deprecated Use UnitUserType instead
 */
@Deprecated
public class PieceUserType implements CompositeUserType {

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getPropertyNames() {
        return new String[] { "unitType", "amount" };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type[] getPropertyTypes() {
        return new Type[] { StandardBasicTypes.STRING, StandardBasicTypes.BIG_DECIMAL };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getPropertyValue(Object component, int property) {
        Piece piece = (Piece) component;
        return property == 0 ? piece.getUnitType() : piece.getMagnitude();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPropertyValue(Object component, int property, Object value) {
        throw new UnsupportedOperationException("Unit types are immutable");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class returnedClass() {
        return Piece.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object x, Object y) {
        if (x == y) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }
        return x.equals(y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode(Object x) {
        return x.hashCode();
    }

    /**
     * {@inheritDoc}
     * 
     * @throws SQLException
     *             in case of database errors
     */
    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
            throws SQLException {
        String unitType = rs.getString(names[0]);
        if (rs.wasNull()) {
            return null;
        }
        int amount = rs.getInt(names[1]);
        return new Piece(amount, PieceUnit.valueOf(unitType));
    }

    /**
     * {@inheritDoc}
     * 
     * @throws SQLException
     *             in case of database errors
     */
    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
            throws SQLException {
        if (value == null) {
            st.setNull(index, StandardBasicTypes.STRING.sqlType());
            st.setNull(index + 1, StandardBasicTypes.BIG_DECIMAL.sqlType());
        } else {
            Piece piece = (Piece) value;
            String unitType = piece.getUnitType().toString();
            st.setString(index, unitType);
            st.setBigDecimal(index + 1, piece.getMagnitude());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object deepCopy(Object value) {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMutable() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Serializable disassemble(Object value, SessionImplementor session) {
        return (Serializable) value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object assemble(Serializable cached, SessionImplementor session, Object owner) {
        return cached;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object replace(Object original, Object target, SessionImplementor session, Object owner) {
        return original;
    }
}