package com.smartVR.usermanagement.Payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class APIResponse {

    @NotNull
    @NotEmpty(message = "Message Can't be Empty")
    String message;
    boolean success;

}
