package com.forohub.domain.reply;

import com.forohub.domain.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply>  findByTopic(Topic topic);
}
