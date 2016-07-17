package com.Test.CourseAPI.web;


import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Test.CourseAPI.model.Course;
import com.Test.CourseAPI.service.CourseApiService;

@RestController
public class CourseController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	 //The CourseAPI business Service
	@Autowired
	CourseApiService service;
	
	/*
	 * Web Service end point get list of all available courses.
	 * This Service returns the collections courses as a JSON
	 * Output: A ResponseEntity containing collection of courses
	 */
	@RequestMapping(value="/courses-api/courses",method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Course>> getCourses()
	{
		logger.info(" -> get Courses");
		Collection<Course> courses = service.findAll();
		return new ResponseEntity<Collection<Course>>(courses,HttpStatus.OK);
	}
	
	/*
	 * Web Service end point to get a specific course based on course Identifier
	 * If found, returns Course as a JSON with HTTP 200
	 * If not found returns HTTP 404
	 * The course sent as path variable in the URL eg: /api/courses/1
	 */
	@RequestMapping(value="/courses-api/getCourse/{id}",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Course>getCourse(@PathVariable("id")Long id)
	{
		logger.info("-> get Course. ID:",id);
		Course course = service.findOne(id);
		//if the course is not available
		if(course==null)
			return new ResponseEntity<Course>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Course>(course,HttpStatus.OK);
	}
	
	/*
	 * Web Service end point to create a course.
	 * Course ID will be auto generated.
	 * HTTP Request body is expected to contain a Course Object in JSON format.
	 * The created Course is persisted in data repository and also added to the local cache.
	 * If created successfully the created Course is returned as JSON with HTTP 201
	 * If not created the method returns an empty response with HTTP 500
	 */
	@RequestMapping(value="/courses-api/createCourse",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Course>createCourse(@RequestBody Course course)
	{
		logger.info("-> create course");
		Course createdCourse = service.create(course);
		logger.info("***Course Created ***");
		return new ResponseEntity<Course>(createdCourse,HttpStatus.CREATED);
	}
	
	/*
	 * Web service end point method to update an already existing course.
	 * Either Course Name or Course Department can be updated.
	 * Course Identifier expected as part of the Course Object in HTTP Request Body in JSON format.
	 * Greeting is updated in the data repository and also added to the cache
	 * If successful, this method returns the updated Course as JSON with HTTP 200
	 * If not, the service returns an HTTP 500 status
	 */
	@RequestMapping(value="/courses-api/updateCourse/{id}",
			method=RequestMethod.PUT,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Course>updateCourse(@RequestBody Course course)
	{
		logger.info("-> update course. ID:",course.getId());
		Course updatedCourse = service.update(course);
		if(updatedCourse==null)
			return new ResponseEntity<Course>(HttpStatus.INTERNAL_SERVER_ERROR);
		logger.info("***Course Updated***");
		return new ResponseEntity<Course>(updatedCourse,HttpStatus.OK);
	}
	
	/*
	 * Web service end point method to delete an existing course.
	 * Course Identifier is supplied in the URL as a path variable
	 * If deleted successfully an empty response is returned along with HTTP 204
	 * If not HTTP 500 status is returned
	 */
	@RequestMapping(value="/courses-api/deleteCourse/{id}",
			method=RequestMethod.DELETE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Course>deleteCourse(@PathVariable("id")Long id)
	{
		logger.info("-> delete Course. ID:",id);
		service.delete(id);
		logger.info("***Course Deleted***");
		return new ResponseEntity<Course>(HttpStatus.NO_CONTENT);
	}

}
