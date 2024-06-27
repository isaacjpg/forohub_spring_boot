package com.forohub.services.course;

import com.forohub.domain.courses.Categoria;

public record CourseResponse(Long id, String name, Categoria category) {
}
