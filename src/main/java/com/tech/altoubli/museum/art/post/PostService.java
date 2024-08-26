package com.tech.altoubli.museum.art.post;

import com.tech.altoubli.museum.art.exception.PostNotFoundExcdeption;
import com.tech.altoubli.museum.art.user.User;
import com.tech.altoubli.museum.art.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final KafkaTemplate<String, Long> kafkaTemplate;

    private static final String TOPIC = "feed.posts";

    public PostDto createPost(PostDto postDto) {
        User user = userRepository.findById(postDto.getUserId())
                .orElseThrow(()-> new RuntimeException("User Not Found"));

        Post post = Post.builder()
                .creator(user)
                .imageUrl(postDto.getImageUrl())
                .description(postDto.getDescription())
                .createdAt(LocalDate.now())
                .requireSubscription(postDto.getRequireSubscription())
                .build();

        post = postRepository.save(post);
        kafkaTemplate.send(TOPIC, post.getId());

        return PostDto.builder()
                .userId(post.getCreator().getId())
                .imageUrl(post.getImageUrl())
                .description(post.getDescription())
                .requireSubscription(post.getRequireSubscription())
                .createdAt(post.getCreatedAt())
                .build();
    }

    public PostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new PostNotFoundExcdeption("Post Not Found"));
        return PostDto.builder()
                .userId(post.getCreator().getId())
                .imageUrl(post.getImageUrl())
                .description(post.getDescription())
                .requireSubscription(post.getRequireSubscription())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
