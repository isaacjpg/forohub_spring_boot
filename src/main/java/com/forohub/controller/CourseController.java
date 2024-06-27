package com.forohub.controller;

import com.forohub.services.course.CourseRequest;
import com.forohub.services.course.CourseResponse;
import com.forohub.services.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {


    private CourseRequest courseRequest;


    private CourseResponse courseResponse;

    @Autowired
    CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@RequestBody CourseRequest courseRequest){
        CourseResponse response = courseService.createCourse(courseRequest);

        return ResponseEntity.ok(response);

    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourses(){
        List<CourseResponse> response = courseService.getAllCourses();

        return ResponseEntity.ok(response);
    }

}
