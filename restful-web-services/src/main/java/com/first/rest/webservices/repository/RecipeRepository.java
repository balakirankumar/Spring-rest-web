package com.first.rest.webservices.repository;

import com.first.rest.webservices.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RecipeRepository extends JpaRepository<Recipe,String>, JpaSpecificationExecutor<Recipe> {

}
