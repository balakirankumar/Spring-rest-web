package com.first.rest.webservices.repository;

import com.first.rest.webservices.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, String>, JpaSpecificationExecutor<UserProfile> {

}
