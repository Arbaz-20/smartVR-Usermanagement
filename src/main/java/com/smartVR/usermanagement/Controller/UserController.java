package com.smartVR.usermanagement.Controller;

import com.smartVR.usermanagement.Constant.UserConstant;
import com.smartVR.usermanagement.DTO.UserDTO;

import com.smartVR.usermanagement.Payload.APIResponse;
import com.smartVR.usermanagement.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.Principal;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {



    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/createUser")
    public ResponseEntity<UserDTO> CreateUser(@Valid @RequestBody UserDTO userDTO){
        userDTO.setRoles(UserConstant.DEFAULT_ROLE);
        String encryptedPassword =  bCryptPasswordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encryptedPassword);
        UserDTO userDTO1 = this.userService.CreateUser(userDTO);
        return new ResponseEntity<UserDTO>(userDTO1, HttpStatus.CREATED);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserDTO> UpdateUser(@Valid @RequestBody UserDTO userDTO , @PathVariable("id") int user_id){
        UserDTO userDTO1 = this.userService.UpdateUser(userDTO,user_id);
        return new ResponseEntity<UserDTO>(userDTO1,HttpStatus.OK);
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<UserDTO> GetSingleUser(@PathVariable("id") int user_id){
        UserDTO user = this.userService.GetUserById(user_id);
        return new ResponseEntity<UserDTO>(user,HttpStatus.ACCEPTED);
    }


    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/getUsers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> userDTOS = this.userService.GetAllUsers();
        return new ResponseEntity<List<UserDTO>>(userDTOS,HttpStatus.ACCEPTED);
    }

    @GetMapping("/getUsername/{name}")
    public ResponseEntity<UserDTO> GetUserByName(@PathVariable("name") String  name){
        UserDTO userDTO = this.userService.GetUserByName(name);
        return new ResponseEntity<UserDTO>(userDTO,HttpStatus.ACCEPTED);
    }

    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> DeleteUser(@PathVariable("id") int user_id){
        this.userService.DeleteUserById(user_id);
        String message = "Deleted User Successfully";
        return new ResponseEntity<>(message,HttpStatus.OK);
    }


    //If logged in User is Admin -> Admin and Moderator Access
    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/access/{user_Id}/{user_Role}")
    public ResponseEntity<APIResponse> GiveAccessToUser(@PathVariable("id") int user_Id, @PathVariable("user_Role") String user_Role, Principal principal){
        UserDTO userDTO = this.userService.GetUserById(user_Id);
        List<String> activeRoles = GetAllRolesFromLoggedInUser(principal);
        if(activeRoles.contains(user_Role)){
            String newRole = userDTO.getRoles() +","+user_Role;
            userDTO.setRoles(newRole);
        }
        userService.UpdateUser(userDTO,user_Id);
        return new ResponseEntity<>(new APIResponse("New Role Assigned to" + userDTO.getUserName() + "from" + principal.getName(),false),HttpStatus.OK);
    }

    private List<String> GetAllRolesFromLoggedInUser(Principal principal){
        String roles =  GetLoggedInUser(principal).getRoles();
        List<String> assignRoles = Arrays.stream(roles.split(",")).collect(Collectors.toList());
        if(assignRoles.contains("ROLE_ADMIN")){
            return Arrays.stream(UserConstant.ADMIN_ACCESS).collect(Collectors.toList());
        }else if(assignRoles.contains("ROLE_MODERATOR")){
            return Arrays.stream(UserConstant.MODERATOR_ACCESS).collect(Collectors.toList());
        }else{
            return Collections.emptyList();
        }
    }

    private UserDTO GetLoggedInUser(Principal principal){
        return this.userService.GetUserByName(principal.getName());
    }

}
