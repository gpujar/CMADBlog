package com.cmad.blog.service;

import java.util.UUID;

import javax.inject.Inject;

import org.jvnet.hk2.annotations.Contract;
import org.jvnet.hk2.annotations.Service;

import com.cmad.blog.dal.TokenDao;
import com.cmad.blog.entities.Token;
import com.cmad.blog.entities.User;

/**
 * Service class that handles REST request about token, which is for user authentication check
 */
@Contract
@Service
public class TokenService {
    private @Inject TokenDao tokenDao ;	

    /**
     * Create token by userId
     * @param user
     * @return token
     *
     */
    public Token createToken(User user) {
        Token token = new Token();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        return token;
    }
}
