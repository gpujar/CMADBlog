package com.cmad.blog.entities;

import java.util.Date;
import javax.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Post {

	private ObjectId Id;

	@NotNull
	private String title;

	@NotNull
	private String synopsis;	

	@NotNull
	private String content;

	@NotNull
	private String firstName;	

	@NotNull
	protected Date createdOn = new Date();

	// This side is really used for DDL generation, e.g. the NOT NULL option)
	//protected User user;
	public Post(){
	}
	
	public Post(final String title, final String synopsis, final String content, final String firstName) {
		this.title = title;
		this.synopsis = synopsis;
		this.content = content;
		this.firstName = firstName;
		this.createdOn = new Date();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}
	
	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Override
	public String toString() {
		return "BlogPost [Id=" + Id + ", synopsis=" + synopsis + ",title=" + title + ", createdOn=" + createdOn + "]";
	}
}
