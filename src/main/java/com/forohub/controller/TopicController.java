package com.forohub.controller;

import com.forohub.services.topic.TopicRequest;
import com.forohub.services.topic.TopicResponse;
import com.forohub.services.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@RestController
@RequestMapping("/topicos")
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping
    ResponseEntity<TopicResponse> createTopic(@RequestBody TopicRequest topicRequest){
        TopicResponse response = topicService.createTopic(topicRequest);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    ResponseEntity<Page<TopicResponse>> listTopics(@PageableDefault(size = 10,sort = "createdAt",direction = Sort.Direction.DESC) Pageable pagination){
        Page<TopicResponse> response = topicService.getAllTopicsPaginated(pagination);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<TopicResponse> getTopic(@PathVariable Long id){
        TopicResponse response = topicService.getTopic(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    ResponseEntity<TopicResponse> updateTopic(@PathVariable Long id, @RequestBody TopicRequest topicRequest){
        TopicResponse response = topicService.updateTopic(id, topicRequest);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/close/{id}")
    ResponseEntity<TopicResponse> closeTopic(@PathVariable Long id){
        TopicResponse response = topicService.closeTopic(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTopic(@PathVariable Long id){
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }
}
