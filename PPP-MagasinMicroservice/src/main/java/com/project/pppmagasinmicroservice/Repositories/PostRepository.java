package com.project.pppmagasinmicroservice.Repositories;

import com.project.pppmagasinmicroservice.Entity.Post;
import com.project.pppmagasinmicroservice.Entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByTopic(Topic topic);




    List<Post> findByPostName(String postName);

    List<Post> findByCreatedBy(String username);
}
