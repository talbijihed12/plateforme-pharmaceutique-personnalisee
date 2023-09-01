package com.project.pppmagasinmicroservice.Repositories;


import com.project.pppmagasinmicroservice.Entity.Comment;
import com.project.pppmagasinmicroservice.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

}
