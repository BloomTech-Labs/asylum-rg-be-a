package com.bloomtech.asylumrgbea.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class BadRequestException extends RuntimeException{

    private  final static long serialVersionUID = 4529555431977284783L;

    private String message;

}
