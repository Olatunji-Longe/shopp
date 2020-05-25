package com.shopp.services;

import com.shopp.domain.User;
import com.shopp.repositories.CredentialRepository;
import com.shopp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private CredentialRepository credentialRepository;

    public static final String CURRENT_USER_NAME = "olatunji.longe";
    public static final String CURRENT_USER_EMAIL = "olatunji@longe.com";

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CredentialRepository credentialRepository) {
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
    }

    @Override
    public User getCurrentUser(){
        return userRepository.findByEmail(CURRENT_USER_NAME);
    }
}
