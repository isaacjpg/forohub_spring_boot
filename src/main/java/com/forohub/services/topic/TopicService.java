package com.forohub.services.topic;

import com.forohub.domain.courses.CourseRepository;
import com.forohub.domain.reply.Reply;
import com.forohub.domain.reply.ReplyRepository;
import com.forohub.domain.topic.Topic;
import com.forohub.domain.topic.TopicRepository;
import com.forohub.domain.topic.validations.SameTitleAndMessage;
import com.forohub.domain.topic.validations.SameTitleAndMessageOnUpdate;
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

    @Autowired
    private ReplyRepository replyRepository;

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

    public ReplyResponse createReply(Long id, ReplyRequest replyRequestResponse) {

        if(!topicRepository.existsById(id)){
            throw new RuntimeException("Topic not found");
        }

        User createdBy = authenticationService.getCurrentUser();
        Topic topic = topicRepository.findById(id).get();
        Reply reply = Reply.builder()
                .message(replyRequestResponse.message())
                .createdAt(LocalDateTime.now())
                .createdBy(createdBy)
                .isSolution(false)
                .topic(topic)
                .build();
        replyRepository.save(reply);

        return new ReplyResponse(
                reply.getId(),
                reply.getMessage(),
                reply.getIsSolution(),
                reply.getCreatedBy().getId(),
                reply.getTopic().getId(),
                reply.getCreatedAt(),
                reply.getCreatedBy().getEmail()
        );

    }

    public List<ReplyResponse> listReplies(Long id) {
        if(!topicRepository.existsById(id)){
            throw new RuntimeException("Topic not found");
        }

        Topic topic = topicRepository.findById(id).get();
        List<Reply> replies = replyRepository.findByTopic(topic);
        return replies.stream()
                .map(r -> new ReplyResponse(
                        r.getId(),
                        r.getMessage(),
                        r.getIsSolution(),
                        r.getCreatedBy().getId(),
                        r.getTopic().getId(),
                        r.getCreatedAt(),
                        r.getCreatedBy().getEmail()
                ))
                .collect(Collectors.toList());
    }


    public ReplyResponse updateReply(Long id, Long idReply, ReplyRequest replyRequest) {
        if(!topicRepository.existsById(id)){
            throw new RuntimeException("Topic not found");
        }

        if(!replyRepository.existsById(idReply)){
            throw new RuntimeException("Reply not found");
        }

        Reply reply = replyRepository.findById(idReply).get();
        reply.setMessage(replyRequest.message());
        reply.setIsSolution(replyRequest.isSolution());

        replyRepository.save(reply);

        return new ReplyResponse(
                reply.getId(),
                reply.getMessage(),
                reply.getIsSolution(),
                reply.getCreatedBy().getId(),
                reply.getTopic().getId(),
                reply.getCreatedAt(),
                reply.getCreatedBy().getEmail()
        );
    }

    public void deleteReply(Long id, Long idReply) {
        if(!topicRepository.existsById(id)){
            throw new RuntimeException("Topic not found");
        }

        if(!replyRepository.existsById(idReply)){
            throw new RuntimeException("Reply not found");
        }

        replyRepository.deleteById(idReply);
    }
}
