package com.project.pppmagasinmicroservice.Utilis.Mappers;

import com.project.pppmagasinmicroservice.Entity.Post;
import com.project.pppmagasinmicroservice.Entity.Topic;
import com.project.pppmagasinmicroservice.Utilis.DTO.TopicDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")


public interface TopicMapper {
    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(topic.getPosts()))")
    TopicDto mapTotopicDto(Topic topic);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "createdBy" ,source ="username" )
    @Mapping(target = "lastModifiedBy" ,source ="username" )
    Topic mapToTopic(TopicDto topicDto, String username);
}
