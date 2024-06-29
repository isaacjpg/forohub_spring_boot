package com.forohub.services.topic;

import com.forohub.domain.users.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


public record ReplyResponse(
        Long id,
        String message,
        Boolean isSolution,
        Long createdById,
        Long topicId,
        LocalDateTime createdAt,
        String createdByEmail
) {
}
