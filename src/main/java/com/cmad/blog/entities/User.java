package com.cmad.blog.entities;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class User implements Principal{

	@Id
	private ObjectId id;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;
	
	@NotNull
	@Pattern(regexp = ".+@.+\\.[a-z]+")
	private String emailAddress;

	@NotNull
	@Size(min = 6, max = 50, message = "password is required, min is 6 and max 50 characters.")
	private String password;

	@NotNull
	private String salt;
	
	private Token token;
	
	@Embedded
	protected Set<Post> posts= new HashSet<Post>();

	public User() {
	}

	public User(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = email;
	}

	public ObjectId getId() {
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

	public Token getToken() {
		return token;
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

	
	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public String getLastName() {
		return lastName;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", emailAddress="
				+ emailAddress + ", password=" + password + ", salt=" + salt + "]";
	}
	
}
