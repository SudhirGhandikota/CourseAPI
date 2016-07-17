package com.Test.CourseAPI.service;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.Test.CourseAPI.model.Course;
import com.Test.CourseAPI.repository.CourseRepository;

/*
 * Bean class which implements the API Service
 * Default transactional support is provided
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
public class CourseServiceBean implements CourseApiService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CourseRepository courseRepository;
	
	/*
	 * Used to get a collection of courses stored in repository
	 * uses the findAll() method of JpaRepository
	 */
	public Collection<Course> findAll() {
		Collection<Course> courses = courseRepository.findAll();
		return courses;
	}

	/*
	 * Method used to get a particular course based on course ID
	 * Uses findOne() method of JpaRepositorys
	 * It is Cacheable and stored in "cached_courses" cache
	 */
	@Cacheable(value="cached_courses",key="#id")
	public Course findOne(Long id) {
		Course course = courseRepository.findOne(id);
		return course;
	}

	/*
	 * Method used to create a course through web-service client
	 * It has "required" transactional support
	 * It is also Cacheable
	 */
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	@CachePut(value="cached_courses",key="#result.id")
	public Course create(Course course) {
		if(course.getId()!=null)
		{
			//cannot create course as course ID provided in the input request
			logger.error("Attempted to create a Course but ID was provided");
			throw new EntityExistsException("ID attribute must be null to persist and entity");
		}
		Course createdCourse = courseRepository.save(course);
		return createdCourse;
	}

	/*
	 * Method used to update an existing course.
	 * Course ID is required as a parameter
	 * It has "required" transactional support as well also has caching enabled
	 */
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	@CachePut(value="cached_courses",key="#course.id")
	public Course update(Course course) {
		Course persistedCourse = findOne(course.getId());
		if(persistedCourse==null)
		{
			//cannot update a course as ID is not valid
			logger.error("Attempted to update a course which does not exist");
			throw new NoResultException("Requested Entity Not Found");
		}
		Course updatedCourse = courseRepository.save(course);
		return updatedCourse;
	}

	/*
	 * Method used to delete an existing course based on course ID
	 * It has transactional support.
	 * The deleted course will also be removed from the cache through CacheEvict
	 */
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@CacheEvict(value="cached_courses",key="#id")
	public void delete(Long id) {
		Course deletedCourse = findOne(id);
		if(deletedCourse==null)
		{
			//cannot update a course as ID is not valid
			logger.error("Attempted to delete a course which does not exist");
			throw new NoResultException("Requested Entity Not Found");
		}
		courseRepository.delete(id);
	}

	/*
	 * This method clears the entire "cached_courses" cache
	 */
	@CacheEvict(value="cached_courses",allEntries=true)
	public void evictCache() {
		// TODO Auto-generated method stub
		
	}

}
