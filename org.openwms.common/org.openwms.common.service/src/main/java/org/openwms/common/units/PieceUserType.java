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
package org.openwms.common.units;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

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
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SessionImplementor sessionImplementor, Object o) throws HibernateException, SQLException {
        String unitType = resultSet.getString(strings[0]);
        if (resultSet.wasNull()) {
            return null;
        }
        int amount = resultSet.getInt(strings[1]);
        return new Piece(amount, PieceUnit.valueOf(unitType));
    }

    /**
     * {@inheritDoc}
     *
     * @throws SQLException
     *             in case of database errors
     */
    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        if (o == null) {
            preparedStatement.setNull(i, StandardBasicTypes.STRING.sqlType());
            preparedStatement.setNull(i + 1, StandardBasicTypes.BIG_DECIMAL.sqlType());
        } else {
            Piece piece = (Piece) o;
            String unitType = piece.getUnitType().toString();
            preparedStatement.setString(i, unitType);
            preparedStatement.setBigDecimal(i + 1, piece.getMagnitude());
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
    public Serializable disassemble(Object o, SessionImplementor sessionImplementor) throws HibernateException {
        return (Serializable) o;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object assemble(Serializable serializable, SessionImplementor sessionImplementor, Object o) throws HibernateException {
        return o;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object replace(Object o, Object o1, SessionImplementor sessionImplementor, Object o2) throws HibernateException {
        return o;
    }
}