package com.forohub.controller;

import com.forohub.services.topic.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Tópicos", description = "Operaciones con tópicos y respuestas")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Operation(
            summary = "Crea un tópico",
            description = "",
            tags = { "Tópicos", "post" })
    @PostMapping
    ResponseEntity<TopicResponse> createTopic(@RequestBody TopicRequest topicRequest){
        TopicResponse response = topicService.createTopic(topicRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Lista de tópicos en con paginación",
            description = "",
            tags = { "Tópicos", "get" })
    @GetMapping
    ResponseEntity<Page<TopicResponse>> listTopics(@PageableDefault(size = 10,sort = "createdAt",direction = Sort.Direction.DESC) Pageable pagination){
        Page<TopicResponse> response = topicService.getAllTopicsPaginated(pagination);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtiene un tópico por su id",
            description = "",
            tags = { "Tópicos", "get" })
    @GetMapping("/{id}")
    ResponseEntity<TopicResponse> getTopic(@PathVariable Long id){
        TopicResponse response = topicService.getTopic(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Actualiza un tópico por su id",
            description = "",
            tags = { "Tópicos", "put" })
    @PutMapping("/{id}")
    ResponseEntity<TopicResponse> updateTopic(@PathVariable Long id, @RequestBody TopicRequest topicRequest){
        TopicResponse response = topicService.updateTopic(id, topicRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Cambia el estado de abierto o cerrado de un tópico por su id",
            description = "",
            tags = { "Tópicos", "patch" })
    @PatchMapping("/close/{id}")
    ResponseEntity<TopicResponse> closeTopic(@PathVariable Long id){
        TopicResponse response = topicService.closeTopic(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Elimina un tópico por su id",
            description = "",
            tags = { "Tópicos", "delete" })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTopic(@PathVariable Long id){
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Crea una respuesta a tu tópico por su id",
            description = "",
            tags = { "Tópicos", "Respuestas","post" })
    @PostMapping("/{id}/respuestas")
    ResponseEntity<ReplyResponse> createReply(@PathVariable Long id, @RequestBody @Valid ReplyRequest replyRequest){
        ReplyResponse response = topicService.createReply(id, replyRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Lista las respuestas por el id del tópico",
            description = "",
            tags = { "Tópicos", "Respuestas","get" })
    @GetMapping("/{id}/respuestas")
    ResponseEntity<List<ReplyResponse>> listReplies(@PathVariable Long id){
        List<ReplyResponse> response = topicService.listReplies(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Actualiza las respuestas por el id del tópico y el id de la respuesta",
            description = "",
            tags = { "Tópicos", "Respuestas","put" })
    @PutMapping("/{id}/respuestas/{idReply}")
    ResponseEntity<ReplyResponse> updateReply(@PathVariable Long id, @PathVariable Long idReply, @RequestBody @Valid ReplyRequest replyRequest){
        ReplyResponse response = topicService.updateReply(id, idReply, replyRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Elimina las respuestas por el id del tópico y el id de la respuesta",
            description = "",
            tags = { "Tópicos", "Respuestas","delete" })
    @DeleteMapping("/{id}/respuestas/{idReply}")
    ResponseEntity<Void> deleteReply(@PathVariable Long id, @PathVariable Long idReply){
        topicService.deleteReply(id, idReply);
        return ResponseEntity.noContent().build();
    }
}
