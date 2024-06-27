package com.forohub.services.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record TopicRequest(
        @NotNull
        @NotBlank
        String title,

        @NotNull
        @NotBlank
        String message,

        @NotNull(message = "idCourse is required")
        Long idCourse
) {
}
