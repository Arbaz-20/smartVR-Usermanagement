package com.smartVR.usermanagement.ServiceImplementation;

import com.smartVR.usermanagement.Entity.User;
import com.smartVR.usermanagement.Repository.UserRepository;
import com.smartVR.usermanagement.Service.GroupUserDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findByUserName(username);
        return user.map(GroupUserDetails::new).orElseThrow(()-> new UsernameNotFoundException("Username Doesn't Exists"));
    }
}
