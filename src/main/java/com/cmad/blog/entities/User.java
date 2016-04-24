package com.cmad.blog.entities;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class User implements Principal{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(nullable = false, length = 60)
	private String firstName;

	@NotNull
	@Column(nullable = false, length = 60)
	private String lastName;
	
	@NotNull
	@Pattern(regexp = ".+@.+\\.[a-z]+")
	@Column(unique = true)
	private String emailAddress;

	@NotNull
	@Column
	@Size(min = 6, max = 50, message = "password is required, min is 6 and max 50 characters.")
	private String password;

	@NotNull
	@Column
	private String salt;
	
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @org.hibernate.annotations.OnDelete(
        action = org.hibernate.annotations.OnDeleteAction.CASCADE
    )
    protected Set<BlogPost> posts= new HashSet<BlogPost>();

	public User() {
	}

	public User(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = email;
	}

	public Long getId() {
		return id;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getName() {
		return emailAddress;
	}

	
	@Override
	public String toString() {
		return "User [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", emailAddress="
				+ emailAddress + ", password=" + password + ", salt=" + salt + "]";
	}
	
}
