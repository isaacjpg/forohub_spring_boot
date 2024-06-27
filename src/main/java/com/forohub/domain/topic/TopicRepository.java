package com.forohub.domain.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Page<Topic> findAll(Pageable pagination);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Topic t WHERE lower(t.title) = lower(?1) AND lower(t.message) = lower(?2)")
    Boolean existsByTitleAndByMessage(String title,String message);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Topic t WHERE lower(t.title) = lower(?1) AND lower(t.message) = lower(?2) AND t.id <> ?3")
    Boolean existsByTitleAndByMessageDiferentId(String title,String message, Long id);
}
