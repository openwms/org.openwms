/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain.system.usermanagement;

import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Password history. All passwords used by the <code>User</code>. When the <code>User</code> changes her password,
 * the current password is moving to the beginning of the password history list.
 * 
 * TODO: + Maybe possible to implement as an ring list with a given number of entries.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
@Entity
@Table(name = "USER_PASSWORD")
public class UserPassword implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Internal identifier.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = AUTO)
    private Long id;

    /**
     * User assigned to this password.
     */
    @ManyToOne
    private User user;

    /**
     * Password.
     */
    @Column(name = "PASSWORD")
    private String password;

    /* ----------------------------- methods ------------------- */
    public UserPassword() {}

    public Long getId() {
	return this.id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public User getUser() {
	return this.user;
    }

    public String getPassword() {
	return this.password;
    }
}
