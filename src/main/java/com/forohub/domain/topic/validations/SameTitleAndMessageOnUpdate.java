package com.forohub.domain.topic.validations;

import com.forohub.domain.topic.TopicRepository;
import com.forohub.services.topic.TopicRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SameTitleAndMessageOnUpdate {
    @Autowired
    private TopicRepository topicRepository;


    public void validate(TopicRequest topicRequest,Long id) {
        if (topicRequest.title().equals(topicRequest.message())) {
            throw new IllegalArgumentException("Title and message must be different");
        }

        Boolean exists = topicRepository.existsByTitleAndByMessageDiferentId(topicRequest.title(), topicRequest.message(),id);
        if (exists) {
            throw new RuntimeException("There is a topic with the same title and message");
        }
    }
}
