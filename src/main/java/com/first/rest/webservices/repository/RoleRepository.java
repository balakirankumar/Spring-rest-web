package com.first.rest.webservices.repository;

import com.first.rest.webservices.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleRepository extends JpaRepository<Role,String>, JpaSpecificationExecutor<Role> {

    Role findByName(String name);


}
