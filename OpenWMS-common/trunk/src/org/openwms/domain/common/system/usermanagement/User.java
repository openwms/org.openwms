package org.openwms.domain.common.system.usermanagement;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USERNAME")
	private String username;

	@Column(name = "PHONE_NO")
	private String phoneNo;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "EXTERN")
	private String extern;

	@Column(name = "COMMENT")
	private String comment;

	@Column(name = "LAST_PASSWORD_CHANGE")
	private Timestamp lastPasswordChange;

	@Column(name = "LOCKED")
	private String locked;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "OFFICE")
	private String office;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "ENABLED")
	private String enabled;

	@Column(name = "EXPIRATION_DATE")
	private Timestamp expirationDate;

	@Column(name = "SKYPE_NAME")
	private String skypeName;

	@ManyToMany(mappedBy = "users")
	private List<Role> roles = new ArrayList<Role>();

	@Column(name = "FULLNAME")
	private String fullname;

	@OneToMany(mappedBy = "user")
	private List<UserPassword> passwords;

	@Column(name = "DEPARTMENT")
	private String department;

	public User() {
		super();
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhoneNo() {
		return this.phoneNo;
	}

	public void setPhone(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExternalUser() {
		return this.extern;
	}

	public void setExternalUser(String externalUser) {
		this.extern = externalUser;
	}

	public String getUserComment() {
		return this.comment;
	}

	public void setUserComment(String userComment) {
		this.comment = userComment;
	}

	public Timestamp getLastPasswordChange() {
		return this.lastPasswordChange;
	}

	public void setLastPasswordChange(Timestamp lastPasswordChange) {
		this.lastPasswordChange = lastPasswordChange;
	}

	public String getLocked() {
		return this.locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOffice() {
		return this.office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEnabled() {
		return this.enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public Timestamp getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(Timestamp expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getSkypeName() {
		return this.skypeName;
	}

	public void setSkypeName(String skypeName) {
		this.skypeName = skypeName;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public List<UserPassword> getPasswords() {
		return this.passwords;
	}

	public void setPasswords(List<UserPassword> passwords) {
		this.passwords = passwords;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

}
