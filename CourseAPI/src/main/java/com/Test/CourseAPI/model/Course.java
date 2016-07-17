package com.Test.CourseAPI.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Course {
	
	@Id
	@GeneratedValue
	private Long id;
	private String courseName;
	private String courseDept;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseDept() {
		return courseDept;
	}
	public void setCourseDept(String courseDept) {
		this.courseDept = courseDept;
	}

}
