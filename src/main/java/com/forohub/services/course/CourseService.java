package com.forohub.services.course;

import com.forohub.domain.courses.CourseRepository;
import com.forohub.domain.topic.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;


    private CourseRequest courseRequest;

    private CourseResponse courseResponse;

    public CourseResponse createCourse(CourseRequest courseRequest) {

       Course courseFinded = courseRepository.findByName(courseRequest.name());
         if (courseFinded != null) {
              throw new RuntimeException("Course already exists");
         }

        Course course = Course.builder()
            .name(courseRequest.name())
            .category(courseRequest.category())
            .build();
        courseRepository.save(course);

        return new CourseResponse(course.getId(), course.getName(),course.getCategory());

    }

    public List<CourseResponse> getAllCourses() {
        List<Course> course = courseRepository.findAll();
        return course.stream()
                .map(c -> new CourseResponse(c.getId(), c.getName(),c.getCategory()))
                .collect(Collectors.toList());
    }


}