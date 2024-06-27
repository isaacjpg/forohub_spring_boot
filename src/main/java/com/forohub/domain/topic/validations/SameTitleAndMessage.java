package com.forohub.domain.topic.validations;

import com.forohub.domain.topic.TopicRepository;
import com.forohub.services.topic.TopicRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SameTitleAndMessage implements TopicCreateValidations{

    @Autowired
    private TopicRepository topicRepository;


    @Override
    public void validate(TopicRequest topicRequest) {
        if (topicRequest.title().equals(topicRequest.message())) {
            throw new IllegalArgumentException("Title and message must be different");
        }

        Boolean exists = topicRepository.existsByTitleAndByMessage(topicRequest.title(), topicRequest.message());
        if (exists) {
            throw new RuntimeException("There is a topic with the same title and message");
        }
    }
}
