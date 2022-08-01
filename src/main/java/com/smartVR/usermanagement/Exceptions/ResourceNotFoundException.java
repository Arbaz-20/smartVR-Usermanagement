package com.smartVR.usermanagement.Exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException{

    String resourceName;

    String fieldName;

    int fieldValue;

    public ResourceNotFoundException(String resourceName , String fieldName,int fieldValue){
        super(String.format("%s Not Found with this %s : %s",resourceName,fieldName,fieldValue));
    }
}
