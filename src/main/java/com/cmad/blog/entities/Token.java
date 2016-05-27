package com.cmad.blog.entities;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;


@Entity
public class Token implements Serializable {

	@Id
	private ObjectId id;

	private String token;

	@Reference
	private User user;

	public Token() {
	}

	public ObjectId getTokenId() {
		System.out.println("Token.getTokenId()  id "+id);
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		System.out.println("Token.getToken()  token  "+token);
		return token;
	}

	public void setToken(String token) {
		System.out.println(" Set token  token  ::"+token);		
		this.token = token;
	}

	@Override
	public String toString() {
		return "Token [id=" + id + ",  token=" + token + "]";
	}

}