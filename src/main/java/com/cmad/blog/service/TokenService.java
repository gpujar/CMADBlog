package com.cmad.blog.service;

import java.util.UUID;

import javax.inject.Inject;

import org.jvnet.hk2.annotations.Contract;
import org.jvnet.hk2.annotations.Service;

import com.cmad.blog.dal.TokenDao;
import com.cmad.blog.dal.UserDao;
import com.cmad.blog.entities.Token;
import com.cmad.blog.entities.User;

/**
 * Service class that handles REST request about token, which is for user authentication check
 */
@Contract
@Service
public class TokenService {
    
	
    private @Inject TokenDao tokenDao ;

    
    private @Inject UserDao userDao;

    /**
     * Create token by userId
     * @param user
     * @return token
     *
     */
    public String createToken(User user) {
        Long userId = user.getId();
        Token token = new Token();
        token.setUserId(userId);
        token.setToken(UUID.randomUUID().toString());
        return (tokenDao.createToken(token))? null : token.getToken();
    }
    
    /**
     * Find user by token
     * @param token
     * @return token
     */
    public User getUserByToken(String token) {
        Long userId = tokenDao.getUserIdByToken(token);
        System.out.println("TokenService: Get userId by token" + userId);
        return userDao.getUser(userId);
    }
}
