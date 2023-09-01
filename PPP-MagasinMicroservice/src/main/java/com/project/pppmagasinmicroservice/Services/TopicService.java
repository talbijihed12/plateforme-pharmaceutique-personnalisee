package com.project.pppmagasinmicroservice.Services;

import com.project.pppmagasinmicroservice.Entity.Post;
import com.project.pppmagasinmicroservice.Entity.Topic;
import com.project.pppmagasinmicroservice.Repositories.ITopicRepo;
import com.project.pppmagasinmicroservice.Utilis.DTO.TopicDto;
import com.project.pppmagasinmicroservice.Utilis.Exception.SpringPlateformeException;
import com.project.pppmagasinmicroservice.Utilis.Mappers.TopicMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.toList;
@AllArgsConstructor
@Service
@Slf4j
public class TopicService {
    private final ITopicRepo iTopicRepo;
    @Autowired
     TopicMapper topicMapper;

    @Transactional
    public TopicDto save(TopicDto topicDto,String username) {
        Topic save = iTopicRepo.save(topicMapper.mapToTopic(topicDto,username));
        topicDto.setId(save.getId());
        return topicDto;
    }

    @Transactional(readOnly = true)
    public List<TopicDto> getAll() {
        return iTopicRepo.findAll().stream().map(topicMapper::mapTotopicDto).collect(toList());
    }


    @Transactional
    public TopicDto getTopic(Long id) {
        Topic topic = iTopicRepo.findById(id)
                .orElseThrow(() -> new SpringPlateformeException("No subplateforme found with id " + id));

        return topicMapper.mapTotopicDto(topic);
    }
}
