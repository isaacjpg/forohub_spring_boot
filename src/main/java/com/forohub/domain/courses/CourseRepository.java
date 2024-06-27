package com.forohub.domain.courses;

import com.forohub.domain.topic.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{
    Course findByName(String name);
    List<Course> findAll();
}
