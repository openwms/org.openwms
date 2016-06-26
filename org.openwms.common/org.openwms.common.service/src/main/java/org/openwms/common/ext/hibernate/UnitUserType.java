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
package org.openwms.common.ext.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.TypeMismatchException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import org.openwms.common.units.Piece;
import org.openwms.common.units.PieceUnit;
import org.openwms.common.units.Weight;
import org.openwms.common.units.WeightUnit;
import org.openwms.core.values.AbstractMeasure;
import org.openwms.core.values.Measurable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An UnitUserType is used by Hibernate as converter for custom <code>Unit</code> types. Only subclasses of {@link AbstractMeasure} are
 * supported by this type converter.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
public class UnitUserType implements CompositeUserType {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnitUserType.class);

    /**
     * {@inheritDoc}
     * <p>
     * We expect that every unit has two fields, named <code>unitType</code> and <code>amount</code>.
     */
    @Override
    public String[] getPropertyNames() {
        return new String[]{"unitType", "amount"};
    }

    /**
     * {@inheritDoc}
     * <p>
     * We're going to persist both fields as Strings.
     */
    @Override
    public Type[] getPropertyTypes() {
        return new Type[]{StandardBasicTypes.STRING, StandardBasicTypes.STRING};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getPropertyValue(Object component, int property) {
        if (component instanceof Piece) {
            Piece piece = (Piece) component;
            return property == 0 ? piece.getUnitType() : piece.getMagnitude();
        } else if (component instanceof Weight) {
            Weight weight = (Weight) component;
            return property == 0 ? weight.getUnitType() : weight.getMagnitude();
        }
        throw new TypeMismatchException("Incompatible type:" + component.getClass());
    }

    /**
     * {@inheritDoc}
     * <p>
     * We have immutable types, throw an UnsupportedOperationException here.
     */
    @Override
    public void setPropertyValue(Object component, int property, Object value) {
        throw new UnsupportedOperationException("Unit types are immutable");
    }

    /**
     * {@inheritDoc}
     * <p>
     * We do not know the concrete implementation here and return an Unit class type.
     */
    @Override
    public Class returnedClass() {
        return Measurable.class;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Delegate to Unit implementation.
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
     * <p>
     * Delegate to Unit implementation.
     */
    @Override
    public int hashCode(Object x) {
        return x.hashCode();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Try to re-assign the value read from the database to some type of Unit. Currently supported types: <ul> <li>Piece</li>
     * <li>Weight</li> </ul>
     *
     * @throws SQLException in case of database errors
     */
    @Override
    public Object nullSafeGet(ResultSet rs, String[] strings, SessionImplementor sessionImplementor, Object o) throws HibernateException, SQLException {
        String rs0 = rs.getString(strings[0]);
        if (rs.wasNull()) {
            return null;
        }
        String[] val = rs0.split("@");
        String unitType = val[0];
        String unitTypeClass = val[1];
        if (Piece.class.getCanonicalName().equals(unitTypeClass)) {
            int amount = rs.getInt(strings[1]);
            return new Piece(amount, PieceUnit.valueOf(unitType));
        } else if (Weight.class.getCanonicalName().equals(unitTypeClass)) {
            BigDecimal amount = rs.getBigDecimal(strings[1]);
            return new Weight(amount, WeightUnit.valueOf(unitType));
        }
        throw new TypeMismatchException("Incompatible type: " + unitTypeClass);
    }

    /**
     * {@inheritDoc}
     * <p>
     * We've to store the concrete classname as well.
     *
     * @throws SQLException in case of database errors
     */
    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, StandardBasicTypes.STRING.sqlType());
            st.setNull(index + 1, StandardBasicTypes.STRING.sqlType());
        } else {
            if (value instanceof Piece) {
                Piece piece = (Piece) value;
                st.setString(index, piece.getUnitType().toString() + "@" + Piece.class.getCanonicalName());
                st.setString(index + 1, piece.getMagnitude().toPlainString());
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("Binding '" + piece.getUnitType().toString() + "@" + Piece.class.getCanonicalName()
                            + "' to parameter: " + index);
                    LOGGER.trace("Binding '" + piece.getMagnitude().toPlainString() + "' to parameter: " + (index + 1));
                }
            } else if (value instanceof Weight) {
                Weight weight = (Weight) value;
                st.setString(index, weight.getUnitType().toString() + "@" + Weight.class.getCanonicalName());
                st.setString(index + 1, weight.getMagnitude().toPlainString());
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("Binding '" + weight.getUnitType().toString() + "@" + Weight.class.getCanonicalName()
                            + "' to parameter: " + index);
                    LOGGER.trace("Binding '" + weight.getMagnitude().toPlainString() + "' to parameter: " + index + 1);
                }
            } else {
                throw new TypeMismatchException("Incompatible type: " + value.getClass().getCanonicalName());
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * No deep copy -> Immutable types.
     */
    @Override
    public Object deepCopy(Object value) {
        return value;
    }

    /**
     * {@inheritDoc}
     * <p>
     * All Unit types aren't mutable.
     */
    @Override
    public boolean isMutable() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Serializable disassemble(Object value, SessionImplementor sessionImplementor) throws HibernateException {
        return (Serializable) value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object assemble(Serializable cached, SessionImplementor sessionImplementor, Object o) throws HibernateException {
        return cached;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object replace(Object original, Object o1, SessionImplementor sessionImplementor, Object o2) throws HibernateException {
        return original;
    }
}