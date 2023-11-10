package com.first.rest.webservices.controllers;


public final class ControllerMappings {

    public static final String CONTEXT_PATH = "/rest";

    public static final String USERS = CONTEXT_PATH + "/users";

    public static final String ROLES = CONTEXT_PATH + "/roles";

    public static final String USER_PROFILE_ROLES = USERS + "/{userProfileId}/roles";

    public static final String HELLO_WORLD = CONTEXT_PATH + "/hello-world";

    public static final String USERS_JPA = CONTEXT_PATH + "/jpa/users";

    public static final String POSTS_JPA = USERS_JPA + "/{userId}/posts";

    public static final String RECIPES_JPA = CONTEXT_PATH + "/recipes";

    public static final String REFRESH_TOKEN = CONTEXT_PATH + "/refresh-token";

    public static final String TOKEN = CONTEXT_PATH + "/token";

}
