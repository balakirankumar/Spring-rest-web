package com.first.rest.webservices.repository;

import com.first.rest.webservices.domain.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String>, JpaSpecificationExecutor<UserProfile> {

    Page<UserProfile> findByEmailContains(@Param("email") String email, Pageable pageable);


    UserProfile findByEmail(String email);


}
