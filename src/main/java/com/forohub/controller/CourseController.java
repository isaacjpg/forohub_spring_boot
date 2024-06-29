package com.forohub.controller;

import com.forohub.services.course.CourseRequest;
import com.forohub.services.course.CourseResponse;
import com.forohub.services.course.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Cursos", description = "Operaciones con cursos")
public class CourseController {


    private CourseRequest courseRequest;


    private CourseResponse courseResponse;

    @Autowired
    CourseService courseService;

    @Operation(
            summary = "Crea un nuevo curso. Categorías serán FRONTEND o BACKEND",
            description = "",
            tags = { "Cursos", "post" })
    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@RequestBody CourseRequest courseRequest){
        CourseResponse response = courseService.createCourse(courseRequest);

        return ResponseEntity.ok(response);

    }

    @Operation(
            summary = "Listado de Cursos",
            description = "",
            tags = { "Cursos", "get" })
    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourses(){
        List<CourseResponse> response = courseService.getAllCourses();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtiene un curso por su id",
            description = "",
            tags = { "Cursos", "get" })
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourse(@PathVariable Long id){
        CourseResponse response = courseService.getCourse(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Actualiza un curso por su id",
            description = "",
            tags = { "Cursos", "put" })
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> updateCourse(@PathVariable Long id, @RequestBody CourseRequest courseRequest){
        CourseResponse response = courseService.updateCourse(id, courseRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Elimina un curso por su id",
            description = "",
            tags = { "Cursos", "delete" })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

}
