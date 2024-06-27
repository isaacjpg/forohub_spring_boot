package com.forohub.services.course;

import com.forohub.domain.courses.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseRequest(
        @NotNull
        @NotBlank
        String name,

        @NotNull
        @NotBlank
        Categoria category) {
}
