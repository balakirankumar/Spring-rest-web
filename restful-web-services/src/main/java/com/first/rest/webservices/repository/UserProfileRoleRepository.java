package com.first.rest.webservices.repository;

import com.first.rest.webservices.domain.UserProfileRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserProfileRoleRepository extends
        JpaRepository<UserProfileRole,String>,JpaSpecificationExecutor<UserProfileRole> {

    UserProfileRole findByUserProfileIdAndRoleId(String userProfileId, String roleId);

    UserProfileRole findByUserProfileIdAndId(String userProfileId, String id);

    List<UserProfileRole> findByUserProfileId(String userProfileId);

    List<UserProfileRole> findByRoleId(String roleId);


}
