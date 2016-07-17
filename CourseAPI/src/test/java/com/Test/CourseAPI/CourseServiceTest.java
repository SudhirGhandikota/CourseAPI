package com.Test.CourseAPI;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.Test.CourseAPI.model.Course;
import com.Test.CourseAPI.service.CourseApiService;

import junit.framework.Assert;

public class CourseServiceTest extends AbstractTest {
	
	@Autowired
	private CourseApiService service;
	
	/*
	 * Method called before starting the test methods
	 * Used to clear cache as we don't have control of order of test methods
	 */
	@Before
	public void setUp()
	{
		service.evictCache();
	}
	
	/*
	 * Test method to test findAll() webservice method
	 */
	@Test
	public void testFindAll()
	{
		Collection<Course> courses = service.findAll();
		//To Test non-null output
		Assert.assertNotNull("Error-Expected Not Null",courses);
		//To Test initial size of list. 2 courses which are added through data.sql script have to be found
		Assert.assertEquals("Error-expected size",2,courses.size());
	}
	
	/*
	 * Test method to test findOne() webservice method
	 */
	@Test
	public void findOneTest()
	{
		Long id = new Long(1);
		Course course = service.findOne(id);
		//To Test non-null result
		Assert.assertNotNull("Error-expected Not Null",course);
		//To match ID of retrieved Course with search ID
		Assert.assertEquals("error-Expected ID match",id,course.getId());
	}
	
	/*
	 * Test method to test negative condition of findOne() where given ID does not exist
	 */
	@Test
	public void findOneNotFoundTest()
	{
		Long id = new Long(Long.MAX_VALUE);
		Course course = service.findOne(id);
		//To Test NULL Response
		Assert.assertNull("Error-Expected NUull",course);
	}
	/*
	 * Test method to test "create" service
	 */
	@Test
	public void createTest()
	{
		Course course = new Course();
		course.setCourseName("Test Course Name");
		course.setCourseDept("Test Course Department");
		Course createdCourse = service.create(course);
		//To Test Non-null responses
		Assert.assertNotNull("Error-Expected Not Null Course",createdCourse);
		Assert.assertNotNull("Error-expected Not Null ID",createdCourse.getId());
		//To Test text matches
		Assert.assertEquals("Error-Expected test match","Test Course Name", createdCourse.getCourseName());
		Assert.assertEquals("Error-Expected test match","Test Course Department", createdCourse.getCourseDept());
		
		Collection<Course> courses = service.findAll();
		Assert.assertEquals("Error-Expected size is 3",3,courses.size());
	}
	/*
	 * Test Method to test create service validation
	 * Should get exception when creating the course with user generated ID
	 */
	@Test
	public void createWithIdTest()
	{
		Course course = new Course();
		course.setId(Long.MAX_VALUE);
		course.setCourseName("Test Course Name");
		course.setCourseDept("Test Course Department");
		Exception ex = null;
		try
		{
			service.create(course);
		}catch(EntityExistsException eee)
		{
			ex = eee;
		}
		Assert.assertNotNull("error-expected exception",ex);
		//To Test Exception type
		Assert.assertTrue("Error-Expected EntityExistsException", ex instanceof EntityExistsException);
	}
	
	/*
	 * To Test "update" webservice method
	 */
	@Test
	public void updateTest()
	{
		Long id = new Long(1);
		Course course = service.findOne(id);
		//To test existence of Course with ID "1"
		Assert.assertNotNull("Error-Expected Not Null",course);
		String new_courseName = course.getCourseName()+"test";
		String new_courseDept = course.getCourseDept()+"test";
		course.setCourseName(new_courseName);
		course.setCourseDept(new_courseDept);
		Course updatedCourse = service.update(course);
		//To Test non null response
		Assert.assertNotNull("Error-Expected Not Null",updatedCourse);
		//To Test text matches of course parameters
		Assert.assertEquals("Error-Expected Course Name match",new_courseName,updatedCourse.getCourseName());
		Assert.assertEquals("Error-Expected Course Department match",new_courseDept,updatedCourse.getCourseDept());
	}
	/*
	 * To Test "update" webservice method with an Invalid course ID
	 */
	@Test
	public void updateNotFoundTest()
	{
		Course course = new Course();
		course.setId(Long.MAX_VALUE);
		course.setCourseName("Test Course Name");
		course.setCourseDept("Test Course Department");
		Exception ex = null;
		try
		{
			service.update(course);
		}catch(NoResultException nre)
		{
			ex = nre;
		}
		Assert.assertNotNull("error-Expected Exception",ex);
		Assert.assertTrue("Error-Expected NoResultException",ex instanceof NoResultException);
	}
	/*
	 * To Test "delete" service Operation
	 */
	@Test
	public void deleteTest()
	{
		Long id = new Long(1);
		service.delete(id);
		Collection<Course> courses = service.findAll();
		//To Test remaining courses count
		//Assert.assertEquals("Error-Expected size of 1",1,courses.size());
		Course deletedCourse = service.findOne(id);
		//To test deletion
		Assert.assertNull("Error-Expected NULL",deletedCourse);
	}

}
