package com.Test.CourseAPI.service;

import java.util.Collection;

import com.Test.CourseAPI.model.Course;

/*
 * Interface represents the services offered through the API
 */
public interface CourseApiService {
	//Service Method GET to get collection of courses
	Collection<Course> findAll();
	
	//Service Method GET to get individual course based in Course ID
	Course findOne(Long id);
	
	//Service Method POST to create a course entity
	Course create(Course course);
	
	//Service Method PUT to update an existing course
	Course update(Course course);
	
	//Service Method DELETE to delete a course
	void delete(Long id);
	
	//Service method to clear all entries in the cache
	void evictCache();
	

}
