package com.forohub.services.topic;

import com.forohub.domain.courses.CourseRepository;
import com.forohub.domain.topic.Course;
import com.forohub.domain.topic.Topic;
import com.forohub.domain.topic.TopicRepository;
import com.forohub.domain.topic.validations.SameTitleAndMessage;
import com.forohub.domain.topic.validations.SameTitleAndMessageOnUpdate;
import com.forohub.domain.topic.validations.TopicCreateValidations;
import com.forohub.domain.users.User;
import com.forohub.infra.security.AuthenticationService;
import com.forohub.services.course.CourseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TopicRepository topicRepository;

    private TopicRequest topicRequest;
    private TopicResponse topicResponse;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SameTitleAndMessage sameTitleAndMessage;

    @Autowired
    private SameTitleAndMessageOnUpdate sameTitleAndMessageOnUpdate;

    public TopicResponse createTopic(TopicRequest topicRequest){

        //Validaciones
        sameTitleAndMessage.validate(topicRequest);

        User createdBy = authenticationService.getCurrentUser();

        if(!courseRepository.existsById(topicRequest.idCourse())){
            throw new RuntimeException("Course not found");
        }

        var course = courseRepository.findById(topicRequest.idCourse()).get();


        Topic topic = Topic.builder()
                .title(topicRequest.title())
                .message(topicRequest.message())
                .createdAt(LocalDateTime.now())
                .isClosed(false)
                .course(course)
                .createBy(createdBy)
                .build();

        Topic saveTopic = topicRepository.save(topic);

        return new TopicResponse(
                saveTopic.getId(),
                saveTopic.getTitle(),
                saveTopic.getMessage(),
                saveTopic.getCreatedAt(),
                saveTopic.getIsClosed(),
                saveTopic.getCreateBy().getId(),
                new CourseResponse(
                        saveTopic.getCourse().getId(),
                        saveTopic.getCourse().getName(),
                        saveTopic.getCourse().getCategory()
                ),
                saveTopic.getCreateBy().getEmail());
    }

    public TopicResponse closeTopic(Long id){
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        topic.setIsClosed(true);
        topicRepository.save(topic);

        return new TopicResponse(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreatedAt(),
                topic.getIsClosed(),
                topic.getCreateBy().getId(),
                new CourseResponse(
                        topic.getCourse().getId(),
                        topic.getCourse().getName(),
                        topic.getCourse().getCategory()
                ),
                topic.getCreateBy().getEmail());
    }




    public Page<TopicResponse> getAllTopicsPaginated(Pageable pagination){
        Page<Topic> topics = topicRepository.findAll(pagination);
        return topics.map(t -> new TopicResponse(
                t.getId(),
                t.getTitle(),
                t.getMessage(),
                t.getCreatedAt(),
                t.getIsClosed(),
                t.getCreateBy().getId(),
                new CourseResponse(
                        t.getCourse().getId(),
                        t.getCourse().getName(),
                        t.getCourse().getCategory()
                ),
                t.getCreateBy().getEmail()
        ));
    }


    public TopicResponse getTopic(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        return new TopicResponse(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreatedAt(),
                topic.getIsClosed(),
                topic.getCreateBy().getId(),
                new CourseResponse(
                        topic.getCourse().getId(),
                        topic.getCourse().getName(),
                        topic.getCourse().getCategory()
                ),
                topic.getCreateBy().getEmail());
    }

    public TopicResponse updateTopic(Long id, TopicRequest topicRequest) {

        if(!topicRepository.existsById(id)){
            throw new RuntimeException("Topic not found");
        }

        if(!courseRepository.existsById(topicRequest.idCourse())){
            throw new RuntimeException("Course not found");
        }

        //Validaciones
        sameTitleAndMessageOnUpdate.validate(topicRequest,id);

        Topic topic = topicRepository.findById(id).get();
        topic.setCourse(courseRepository.findById(topicRequest.idCourse()).get());
        topic.setTitle(topicRequest.title());
        topic.setMessage(topicRequest.message());



        topicRepository.save(topic);

        return new TopicResponse(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreatedAt(),
                topic.getIsClosed(),
                topic.getCreateBy().getId(),
                new CourseResponse(
                        topic.getCourse().getId(),
                        topic.getCourse().getName(),
                        topic.getCourse().getCategory()
                ),
                topic.getCreateBy().getEmail());


    }

    public void deleteTopic(Long id) {
        if(!topicRepository.existsById(id)){
            throw new RuntimeException("Topic not found");
        }

        topicRepository.deleteById(id);
    }
}
