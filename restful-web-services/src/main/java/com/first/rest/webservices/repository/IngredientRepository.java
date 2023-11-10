package com.first.rest.webservices.repository;

import com.first.rest.webservices.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IngredientRepository extends JpaRepository<Ingredient,String>, JpaSpecificationExecutor<Ingredient> {
}
