package com.tpe.security.service;

import com.tpe.domain.User;
import com.tpe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    //tablodan username ile user getirip geriye UserDetails dondurur
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username).
                orElseThrow(()-> new UsernameNotFoundException("UserName is not Found !!!"));
        return UserDetailsImpl.build(user); //geriye userDetailsImlp donderir
    }
}
