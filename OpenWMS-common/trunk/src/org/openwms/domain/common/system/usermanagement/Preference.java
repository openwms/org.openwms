package org.openwms.domain.common.system.usermanagement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "preference")
public class Preference implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private long id;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "VALUE")
	private String value;

	@Column(name = "KEY")
	private String key;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "FLOAT_VALUE")
	private BigDecimal floatValue;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "MINIMUM")
	private String minimum;

	@Column(name = "MAXIMUM")
	private String maximum;

	@ManyToMany(mappedBy = "preferences")
	private List<Role> roles = new ArrayList<Role>();

	public Preference() {
		super();
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.value = key;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BigDecimal getFloatValue() {
		return this.floatValue;
	}

	public void setFloatValue(BigDecimal floatValue) {
		this.floatValue = floatValue;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMinimum() {
		return this.minimum;
	}

	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}

	public String getMaximum() {
		return this.maximum;
	}

	public void setMaximum(String maximum) {
		this.maximum = maximum;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
