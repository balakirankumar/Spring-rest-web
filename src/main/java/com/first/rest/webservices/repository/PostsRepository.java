package com.first.rest.webservices.repository;

import com.first.rest.webservices.domain.Post;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepository extends CrudRepository<Post,String>, JpaSpecificationExecutor<Post> {

    List<Post> findByUserProfileId(@Param("userProfileId") String userId);


    Post findByIdAndUserProfileId(@Param("postId") String postId,@Param("userProfileId") String userId);

}
