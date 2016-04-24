package com.cmad.blog.app;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.cmad.blog.dal.TokenDao;
import com.cmad.blog.dal.UserDao;
import com.cmad.blog.service.TokenService;

public class BlogApp extends ResourceConfig {
    public BlogApp() {
        register(new AbstractBinder(){
		    @Override
		    protected void configure() {
		        bind(TokenDao.class).to(TokenDao.class);
		        bind(UserDao.class).to(UserDao.class);
		        bind(TokenService.class).to(TokenService.class);
		    }
        });
        packages(true, "com.cmad.blog.dal, com.cmad.blog.service, com.cmad.blog.security");
        register(JacksonFeature.class);
    }
}