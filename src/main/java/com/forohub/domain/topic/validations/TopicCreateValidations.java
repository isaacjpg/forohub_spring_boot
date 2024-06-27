package com.forohub.domain.topic.validations;

import com.forohub.services.topic.TopicRequest;

public interface TopicCreateValidations {
    public void validate(TopicRequest data);
}
