package org.example.socialmediaapp.service;

import jakarta.transaction.Transactional;
import org.example.socialmediaapp.dao.PostDao;
import org.example.socialmediaapp.dto.PostCreate;
import org.example.socialmediaapp.dto.PostPreview;
import org.example.socialmediaapp.exception.ResourceNotFoundException;
import org.example.socialmediaapp.mapper.PostMapper;
import org.example.socialmediaapp.model.Post;
import org.example.socialmediaapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class PostService {
    @Autowired
    private PostDao postDao;
    @Autowired
    private UserService userService;

    public Page<PostPreview> getAllPostsPreview(Pageable pageable) {
        Page<Post> p = postDao.findAll(pageable);
        return p.map(PostMapper::toPostPreview);
    }

    public Page<PostPreview> getPostsByUser(String userId, Pageable pageable) {
        return postDao.findByUserId(userId, pageable).map(PostMapper::toPostPreview);
    }

    public Page<PostPreview> getPostsByTag(String tag, Pageable pageable) {
        return postDao.findByTag(tag, pageable).map(PostMapper::toPostPreview);
    }

    public Post getPostById(String id) {
        return postDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", id));
    }

    public Post createPost(PostCreate post) {
        User user = userService.getUserById(post.user());

        Post newPost = new Post();
        newPost.setText(post.text().trim());
        newPost.setImage(post.image());
        newPost.setTags(post.tags());
        newPost.setLikes(0);
        newPost.setUser(user);

        return postDao.save(newPost);

    }

    public Post updatePost(String id, Post postDetails) {
        Post post = getPostById(id);

        if (postDetails.getUser() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Updating the owner of a post is forbidden");
        }


        post.setText(postDetails.getText());
        post.setImage(postDetails.getImage());
        post.setTags(postDetails.getTags());

        return postDao.save(post);
    }

    public String deletePost(String id) {

        if (!postDao.existsById(id)) {
            throw new ResourceNotFoundException("Post", id);
        }

        postDao.deleteById(id);
        return id;
    }


}
