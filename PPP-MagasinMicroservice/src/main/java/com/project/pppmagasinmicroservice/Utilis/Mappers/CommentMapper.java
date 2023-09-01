package com.project.pppmagasinmicroservice.Utilis.Mappers;


import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.project.pppmagasinmicroservice.Entity.Comment;
import com.project.pppmagasinmicroservice.Entity.Post;
import com.project.pppmagasinmicroservice.Repositories.CommentRepository;
import com.project.pppmagasinmicroservice.Utilis.DTO.CommentTO;
import com.project.pppmagasinmicroservice.Utilis.DTO.CommentToResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;


@Mapper(componentModel = "spring")
public abstract class CommentMapper {

    @Autowired
    private CommentRepository commentRepositry;
    @Mapping(target = "idComment", ignore = true)
    @Mapping(target = "text", source = "commentTO.text")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "createdBy" ,source = "username")
    public abstract Comment map(CommentTO commentTO, Post post, String username);

    @Mapping(target = "idComment", ignore = true)
    @Mapping(target = "text", source = "commentTO.text")
    @Mapping(target = "post", ignore = true)
    @Mapping(target = "lastModifiedBy" ,source = "username")
    public abstract Comment mapWithoutPost(CommentTO commentTO, String username);


    @Mapping(target = "postId", expression = "java(comment.getPost().getPubId())")
    @Mapping(target = "createdBy" ,source = "createdBy")
    @Mapping(target = "duration", expression = "java(getDuration(comment))")

    public abstract CommentToResponse mapToTO(Comment comment);
    String getDuration(Comment comment) {
        return TimeAgo.using(Timestamp.valueOf(comment.getCreatedDate()).getTime());
    }



}
