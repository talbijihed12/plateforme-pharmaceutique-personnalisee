package com.project.pppmagasinmicroservice.Services;

import com.project.pppmagasinmicroservice.Entity.Post;
import com.project.pppmagasinmicroservice.Entity.Topic;
import com.project.pppmagasinmicroservice.Repositories.ITopicRepo;
import com.project.pppmagasinmicroservice.Repositories.PostRepository;
import com.project.pppmagasinmicroservice.Utilis.DTO.PostRequest;
import com.project.pppmagasinmicroservice.Utilis.DTO.PostResponse;
import com.project.pppmagasinmicroservice.Utilis.Exception.PostNotFoundException;
import com.project.pppmagasinmicroservice.Utilis.Exception.SpringPlateformeException;
import com.project.pppmagasinmicroservice.Utilis.Exception.TopicNotFoundException;
import com.project.pppmagasinmicroservice.Utilis.Mappers.PostMapper;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.spi.UnauthorizedException;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.NotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@Transactional
public class PostService {
    @Autowired
    private ITopicRepo iTopicRepo;

    @Autowired
    private PostMapper postMapper;
    @Autowired
    private PostRepository postRepository;


    @Transactional
    public void save(PostRequest postRequest, String username) {
        Topic topic = iTopicRepo.findByName(postRequest.getTopic())
                .orElseThrow(() -> new TopicNotFoundException(postRequest.getTopic()));
        postRepository.save(postMapper.map(postRequest, topic,username));

    }
    @Transactional
    public void update(PostRequest postRequest, String username,Long pubId) {
        Post existingPost = postRepository.findById(pubId)
                .orElseThrow(() -> new PostNotFoundException(pubId));
        if (!existingPost.getCreatedBy().equals(username)) {
            throw new UnauthorizedException("You are not authorized to update this post.");
        }
        Post updatedPost = postMapper.mapWithOutTopic(postRequest, username);
        existingPost.setPostName(updatedPost.getPostName());
        existingPost.setDescription(updatedPost.getDescription());
        existingPost.setHashTag(updatedPost.getHashTag());

        postRepository.save(existingPost);
    }


    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id.toString()));
        return postMapper.mapToTO(post);

    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToTO)
                .collect(toList());
    }
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPostsByUsername(String username) {
        return postRepository.findByCreatedBy(username)
                .stream()
                .map(postMapper::mapToTO)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubplateforme(Long subplateformeId) {
        Topic subplateforme = iTopicRepo.findById(subplateformeId)
                .orElseThrow(() -> new TopicNotFoundException(subplateformeId.toString()));
        List<Post> posts = postRepository.findAllByTopic(subplateforme);
        return posts
                .stream()
                .map(postMapper::mapToTO)
                .collect(toList());
    }



    public void deletePost(Long id){
        Post post = this.postRepository.findById(id).orElseThrow(() -> new SpringPlateformeException("post not found with id -" + id));
        this.postRepository.delete(post);

    }
}
