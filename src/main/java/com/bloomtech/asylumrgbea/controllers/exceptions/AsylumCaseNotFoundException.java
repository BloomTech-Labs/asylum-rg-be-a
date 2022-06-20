package com.bloomtech.asylumrgbea.controllers.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class AsylumCaseNotFoundException extends RuntimeException{

    private  final static long serialVersionUID = 146277653713708386L;

    @Setter
    private String message;
}
