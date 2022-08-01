package com.smartVR.usermanagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {

    private int user_id;

    @NotEmpty(message = "Please Enter UserName")
    @NotBlank(message = "Please Enter UserName")
    private String userName;

    @NotEmpty(message = "Please Enter Email")
    @NotBlank(message = "Please Enter Email")
    @Email(regexp = "^(.+)@(\\S+)$",message = "Invalid Email")
    private String email;

    @NotEmpty(message = "Please Enter Password")
    @NotBlank(message = "Please Enter Password")
    @Size(min = 8,message = "Password must contains 8 characters")
    private String password;

    private String roles;
}
