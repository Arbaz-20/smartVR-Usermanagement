package com.smartVR.usermanagement.Service;

import com.smartVR.usermanagement.DTO.UserDTO;

import java.util.List;

public interface UserService {

    //Create the User
    public UserDTO CreateUser(UserDTO userDTO);

    //Update the user
    public UserDTO UpdateUser(UserDTO userDTO,Integer user_id);

    //Get User By Id
    public UserDTO GetUserById(Integer user_Id);

    //Get User by Name
    public UserDTO GetUserByName(String name);

    //Get All the User's
    public List<UserDTO> GetAllUsers();

    //Delete User by Id
    public String DeleteUserById(Integer user_Id);

    //Delete All Users
    public String DeleteAllUsers();

}
