Topic topic = new Topic();
Post post1 = new Post();
Post post2 = new Post();

post1.setTopic(topic);
post2.setTopic(topic);

topic.getPosts().add(post1);
topic.getPosts().add(post2);

postRepository.save(post1);
postRepository.save(post2);

topicRepository.save(topic);
