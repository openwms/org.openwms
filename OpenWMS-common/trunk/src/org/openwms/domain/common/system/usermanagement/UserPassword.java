package org.openwms.domain.common.system.usermanagement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER_PASSWORD")
public class UserPassword implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private long id;

	@ManyToOne
	private User user;

	@Column(name = "PASSWORD")
	private String password;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public String getPassword() {
		return this.password;
	}
}
