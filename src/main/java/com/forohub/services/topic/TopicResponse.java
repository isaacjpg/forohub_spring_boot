package com.forohub.services.topic;

import com.forohub.services.course.CourseResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Builder
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
