package com.project.pppmagasinmicroservice.Repositories;

import com.project.pppmagasinmicroservice.Entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITopicRepo extends JpaRepository<Topic, Long> {
    Optional<Topic> findByName(String name);
}
