package com.simonbassey.springtodo.core.domain.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "todos")
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int todoId;
	@Column(name="title")
	private String title;
	@Column(name = "detail")
	private String description;
	@Column(name="created")
	private LocalDate createdDate;
	@Column(name = "lastupdated")
	private LocalDate lastUpdated;
	@Column(name = "completed")
	private boolean complete;
	@Column(name = "userId")
	private String userId;
	
	@JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false, insertable = false, updatable = false)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private ApplicationUser user;
	
	public int getTodoId() {
		return todoId;
	}
	public void setTodoId(int todoId) {
		this.todoId = todoId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	public LocalDate getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(LocalDate lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	public void setUserId(String userId) { this.userId = userId;}
	public String getUserId() {return this.userId;}
	
	public void setUser(ApplicationUser user) {this.user = user;}
	public ApplicationUser getUser() {return this.user;}
	
	public Todo(String title_, String descriptionString) {
		title = title_;
		description = descriptionString;
		createdDate = LocalDate.now();
	}
	
	public Todo() {
	}
}