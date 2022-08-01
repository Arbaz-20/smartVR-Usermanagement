package com.smartVR.usermanagement.ServiceImplementation;

import com.smartVR.usermanagement.DTO.UserDTO;
import com.smartVR.usermanagement.Entity.User;
import com.smartVR.usermanagement.Exceptions.ResourceNotFoundException;
import com.smartVR.usermanagement.Repository.UserRepository;
import com.smartVR.usermanagement.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO CreateUser(UserDTO userDTO) {
        User user = this.modelMapper.map(userDTO, User.class);
        User SavedUser = this.userRepository.save(user);
        return this.modelMapper.map(SavedUser,UserDTO.class);
    }

    @Override
    public UserDTO UpdateUser(UserDTO userDTO, Integer user_id)
    {
        User user = this.userRepository.findById(user_id).orElseThrow(()-> new ResourceNotFoundException("User","Id",user_id));

        //Setting up User Details
        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return null;
    }

    @Override
    public UserDTO GetUserById(Integer user_Id) {
        User user = this.userRepository.findById(user_Id).orElseThrow(()-> new ResourceNotFoundException("User","Id",user_Id));
        return this.modelMapper.map(user,UserDTO.class);
    }

    @Override
    public UserDTO GetUserByName(String name) {
        User user = this.userRepository.findByUserName(name).orElseThrow(()-> new ResourceNotFoundException("User","Name",0));
        return this.modelMapper.map(user,UserDTO.class);
    }

    @Override
    public List<UserDTO> GetAllUsers() {
        List<User> users = this.userRepository.findAll();
        List<UserDTO> userDTOS = users.stream().map(user -> this.modelMapper.map(user,UserDTO.class)).collect(Collectors.toList());
        return userDTOS;
    }

    @Override
    public String DeleteUserById(Integer user_Id) {
        User user = this.userRepository.findById(user_Id).orElseThrow(()-> new ResourceNotFoundException("user","id",user_Id));
        this.userRepository.delete(user);
        return "User Deleted Successfully";
    }

    @Override
    public String DeleteAllUsers() {
        this.userRepository.deleteAll();
        return "All Users Deleted Successfully";
    }
}
