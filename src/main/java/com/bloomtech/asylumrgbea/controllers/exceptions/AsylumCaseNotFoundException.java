package com.bloomtech.asylumrgbea.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AsylumCaseNotFoundException extends RuntimeException{

    private  final static long serialVersionUID = 146277653713708386L;

    private String message;


}
