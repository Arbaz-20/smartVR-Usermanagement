package com.smartVR.usermanagement.Exceptions;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PropertyResponseException extends RuntimeException{

    public PropertyResponseException(String errors){
        super(String.format("%s Not Found !!!!!",errors));
}
}
