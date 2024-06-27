package com.forohub.services.topic;

import com.forohub.services.course.CourseResponse;

import java.time.LocalDateTime;

public record TopicResponse(
        Long id,
        String title,
        String message,
        LocalDateTime createdAt,
        Boolean isClosed,
        Long idUser,
        CourseResponse course,
        String createByEmail
) {
}
