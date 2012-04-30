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
package org.openwms.common.domain.units;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

/**
 * A PieceUserType.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
public class PieceUserType implements CompositeUserType {

    /**
     * @see org.hibernate.usertype.CompositeUserType#getPropertyNames()
     */
    @Override
    public String[] getPropertyNames() {
        return new String[] { "unitType", "amount" };
    }

    /**
     * @see org.hibernate.usertype.CompositeUserType#getPropertyTypes()
     */
    @Override
    public Type[] getPropertyTypes() {
        return new Type[] { Hibernate.STRING, Hibernate.BIG_DECIMAL };
    }

    /**
     * @see org.hibernate.usertype.CompositeUserType#getPropertyValue(java.lang.Object,
     *      int)
     */
    @Override
    public Object getPropertyValue(Object component, int property) throws HibernateException {
        Piece piece = (Piece) component;
        return property == 0 ? piece.getUnitType() : piece.getAmount();
    }

    /**
     * @see org.hibernate.usertype.CompositeUserType#setPropertyValue(java.lang.Object,
     *      int, java.lang.Object)
     */
    @Override
    public void setPropertyValue(Object component, int property, Object value) throws HibernateException {
        throw new UnsupportedOperationException("Unit types are immutable");
    }

    /**
     * @see org.hibernate.usertype.CompositeUserType#returnedClass()
     */
    @Override
    public Class returnedClass() {
        return Piece.class;
    }

    /**
     * @see org.hibernate.usertype.CompositeUserType#equals(java.lang.Object,
     *      java.lang.Object)
     */
    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }
        return x.equals(y);
    }

    /**
     * @see org.hibernate.usertype.CompositeUserType#hashCode(java.lang.Object)
     */
    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    /**
     * @see org.hibernate.usertype.CompositeUserType#nullSafeGet(java.sql.ResultSet,
     *      java.lang.String[], org.hibernate.engine.SessionImplementor,
     *      java.lang.Object)
     */
    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
            throws HibernateException, SQLException {
        String unitType = rs.getString(names[0]);
        if (rs.wasNull()) {
            return null;
        }
        int amount = rs.getInt(names[1]);
        return new Piece(amount, PieceUnit.valueOf(unitType));
    }

    /**
     * @see org.hibernate.usertype.CompositeUserType#nullSafeSet(java.sql.PreparedStatement,
     *      java.lang.Object, int, org.hibernate.engine.SessionImplementor)
     */
    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Hibernate.STRING.sqlType());
            st.setNull(index + 1, Hibernate.BIG_DECIMAL.sqlType());
        } else {
            Piece piece = (Piece) value;
            String unitType = piece.getUnitType().toString();
            st.setString(index, unitType);
            st.setBigDecimal(index + 1, piece.getAmount());
        }

    }

    /**
     * @see org.hibernate.usertype.CompositeUserType#deepCopy(java.lang.Object)
     */
    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    /**
     * @see org.hibernate.usertype.CompositeUserType#isMutable()
     */
    @Override
    public boolean isMutable() {
        return false;
    }

    /**
     * @see org.hibernate.usertype.CompositeUserType#disassemble(java.lang.Object,
     *      org.hibernate.engine.SessionImplementor)
     */
    @Override
    public Serializable disassemble(Object value, SessionImplementor session) throws HibernateException {
        return (Serializable) value;
    }

    /**
     * @see org.hibernate.usertype.CompositeUserType#assemble(java.io.Serializable,
     *      org.hibernate.engine.SessionImplementor, java.lang.Object)
     */
    @Override
    public Object assemble(Serializable cached, SessionImplementor session, Object owner) throws HibernateException {
        return cached;
    }

    /**
     * @see org.hibernate.usertype.CompositeUserType#replace(java.lang.Object,
     *      java.lang.Object, org.hibernate.engine.SessionImplementor,
     *      java.lang.Object)
     */
    @Override
    public Object replace(Object original, Object target, SessionImplementor session, Object owner)
            throws HibernateException {
        return original;
    }

}
