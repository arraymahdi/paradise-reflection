package com.tech.altoubli.museum.art.kafka;

import com.tech.altoubli.museum.art.exception.PostNotFoundExcdeption;
import com.tech.altoubli.museum.art.exception.UserFeedNotFoundException;
import com.tech.altoubli.museum.art.feed.Feed;
import com.tech.altoubli.museum.art.post.Post;
import com.tech.altoubli.museum.art.post.PostRepository;
import com.tech.altoubli.museum.art.user.User;
import com.tech.altoubli.museum.art.feed.FeedRepository;
import com.tech.altoubli.museum.art.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FeedUpdateConsumer {

    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @KafkaListener(topics = "feed.posts", groupId = "feed.group.dev")
    public void consumePostEvent(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new PostNotFoundExcdeption("Post Not Found"));
        User creator = post.getCreator();

        List<User> followers = creator.getFollowers();

        List<User> subscribers = creator.getSubscribers();

        for (User user : subscribers) {
            updateFeedForUser(user, post);
            log.info("Hey I worked Hard111");
        }

        if (!post.getRequireSubscription()) {
            for (User user : followers) {
                if (!subscribers.contains(user)) {
                    updateFeedForUser(user, post);
                    log.info("Hey I worked Hard");
                }
            }
        }
    }

    private void updateFeedForUser(User user, Post post) {
        Feed feed = feedRepository.findByUser(user).orElseThrow(
                ()-> new UserFeedNotFoundException("User Feed Not Found"));
        if (feed != null) {
            feed.getPosts().add(post);
            feedRepository.save(feed);
        }
    }
}
