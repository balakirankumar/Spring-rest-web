package com.first.rest.webservices.service.impl;

import com.first.rest.webservices.domain.Post;
import com.first.rest.webservices.domain.UserProfile;
import com.first.rest.webservices.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl extends  BeanInjectionService implements PostService {

    public List<Post> getPostsByUserId(String userId){
        return postsRepository.findByUserProfileId(userId);
    }

    public Post getPostsByIdAndUserId(String postId, String userId){
        return  postsRepository.findByIdAndUserProfileId(postId,userId);
    }

    public Post createPost(UserProfile userProfile, com.first.rest.webservices.mediatype.Post post){
        Post postDomain = populatePostEntity(userProfile,post);
        return postsRepository.save(postDomain);
    }

    public void deletePostsById(String postId){
        postsRepository.deleteById(postId);
    }


    Post populatePostEntity(UserProfile userProfile, com.first.rest.webservices.mediatype.Post post){
    Post post1 = new Post();
    BeanUtils.copyProperties(post,post1);
    return post1;

    }
}
