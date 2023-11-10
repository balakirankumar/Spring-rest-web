package com.first.rest.webservices.service;

import com.first.rest.webservices.domain.Post;
import com.first.rest.webservices.domain.UserProfile;

import java.util.List;

public interface PostService {

    List<Post> getPostsByUserId(String userId);

    Post getPostsByIdAndUserId(String id,String userId);

    Post createPost(UserProfile userProfile, com.first.rest.webservices.mediatype.Post post);

    void deletePostsById(String id);
}
