package com.jfelixy.encurtadorurl.model.exceptions;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    String errorMessage;
    String errorCode;
    String path;
}
