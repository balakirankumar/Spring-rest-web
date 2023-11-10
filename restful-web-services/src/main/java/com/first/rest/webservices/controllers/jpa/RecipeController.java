package com.first.rest.webservices.controllers.jpa;


import com.first.rest.webservices.config.AppLogger;
import com.first.rest.webservices.controllers.BaseController;
import com.first.rest.webservices.controllers.ControllerMappings;
import com.first.rest.webservices.domain.Ingredient;
import com.first.rest.webservices.exception.constants.StatusCode;
import com.first.rest.webservices.exception.exceptions.NotFoundException;
import com.first.rest.webservices.mediatype.Recipe;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.hateoas.server.LinkBuilder;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@ExposesResourceFor(Recipe.class)
@RequestMapping(value = ControllerMappings.RECIPES_JPA, produces = MediaTypes.HAL_JSON_VALUE)
public class RecipeController extends BaseController {

    public static final AppLogger LOGGER = AppLogger.getLogger(RecipeController.class);


    @RequestMapping(method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EntityModel<Recipe>>> getAllRecipes() {
        List<com.first.rest.webservices.domain.Recipe> recipes = recipeRepository.findAll();
        return new ResponseEntity<>(getRecipeEntityModel(recipes), HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{recipeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EntityModel<Recipe>>> deleteRecipe(@PathVariable String recipeId) {
        recipeRepository.deleteById(recipeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<EntityModel<Recipe>>> createRecipes(@RequestBody List<Recipe> recipes) {
        List<com.first.rest.webservices.domain.Recipe> recipeDomainList = new ArrayList<>();
        com.first.rest.webservices.domain.Recipe recipeDomain;
        Ingredient ingredient;
        for (Recipe rec : recipes) {
            rec.setId(UUID.randomUUID().toString());
            recipeDomain = new com.first.rest.webservices.domain.Recipe();
            BeanUtils.copyProperties(rec, recipeDomain);
            recipeDomain.setCreatedBy(userProfileRepository.findById("4bd8f699-b078-4573-8b9e-d4a5313d4e4d").get());
            recipeDomain.setCreatedDate(new Date());
            if (!CollectionUtils.isEmpty(rec.getIngredients())) {
                List<Ingredient> ingredients = new ArrayList<>();
                for (com.first.rest.webservices.mediatype.Ingredient recIngredient : rec.getIngredients()) {
                    ingredient = new Ingredient();
                    BeanUtils.copyProperties(recIngredient, ingredient);
                    ingredient.setId(UUID.randomUUID().toString());
                    ingredient.setCreatedBy(recipeDomain.getCreatedBy());
                    ingredient.setRecipe(recipeDomain);
                    ingredient.setCreatedDate(new Date());
                    ingredients.add(ingredient);
                }
                ingredientRepository.saveAll(ingredients);
            }
            recipeDomainList.add(recipeDomain);
        }
        recipeDomainList = recipeRepository.saveAll(recipeDomainList);
        return new ResponseEntity<>(getRecipeEntityModel(recipeDomainList), HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{recipeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EntityModel<Recipe>>> getRecipeById(@PathVariable String recipeId) {
        Optional<com.first.rest.webservices.domain.Recipe> recipeDomainOptional = recipeRepository.findById(recipeId);
        if (!recipeDomainOptional.isPresent()) {
            throw new NotFoundException(StatusCode._404.getDescription(),
                    String.format("Recipe with id [ %s ] is not found", recipeId));
        }
        com.first.rest.webservices.domain.Recipe recipe = recipeDomainOptional.get();
        return new ResponseEntity<>(getRecipeEntityModel(Collections.singletonList(recipe)), HttpStatus.OK);
    }


    private List<EntityModel<Recipe>> getRecipeEntityModel(List<com.first.rest.webservices.domain.Recipe> recipes) {
        EntityModel<Recipe> recipeEntityModel;
        List<EntityModel<Recipe>> recipeEntityModels = new ArrayList<>();
        Recipe recipeMediaType;
        for (com.first.rest.webservices.domain.Recipe recipe : recipes) {
            recipeMediaType = new Recipe();
            BeanUtils.copyProperties(recipe, recipeMediaType);
            recipeMediaType.setCreatedBy(recipe.getCreatedBy().getId());
            recipeMediaType.setIngredients(getIngredientMediaType(recipe.getIngredients()));
            recipeEntityModel = EntityModel.of(recipeMediaType);
            LinkBuilder lb = entityLinks.linkFor(Recipe.class);
            recipeEntityModel.add(lb.slash(recipeMediaType.getId()).withSelfRel());
            recipeEntityModel.add(lb.slash(recipeMediaType.getId()).slash("ingredients").withRel("ingredients"));
            recipeEntityModels.add(recipeEntityModel);
        }
        return recipeEntityModels;
    }


    private List<com.first.rest.webservices.mediatype.Ingredient> getIngredientMediaType(List<Ingredient> ingredientDomainsList) {
        List<com.first.rest.webservices.mediatype.Ingredient> ingredientMediaList = new ArrayList<>();
//        HttpEntity httpEntity = new HttpEntity("",)
//        restTemplate.exchange("url", HttpMethod.POST,)
        ingredientDomainsList.forEach(ingredient -> {
            com.first.rest.webservices.mediatype.Ingredient ingredientMedia = new com.first.rest.webservices.mediatype.Ingredient();
            BeanUtils.copyProperties(ingredient, ingredientMedia);
            ingredientMediaList.add(ingredientMedia);
        });
        return ingredientMediaList;
    }
}
