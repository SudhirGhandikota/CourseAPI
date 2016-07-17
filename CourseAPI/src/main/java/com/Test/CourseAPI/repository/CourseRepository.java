package com.Test.CourseAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Test.CourseAPI.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

}
