package com.forohub.services.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record ReplyRequest(
        @NotNull
        @NotBlank
        String message,
        @NotNull
        Long topicId,
        Boolean isSolution
){
}
