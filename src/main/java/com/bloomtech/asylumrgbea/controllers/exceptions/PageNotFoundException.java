package com.bloomtech.asylumrgbea.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageNotFoundException extends RuntimeException {

    private final static long serialVersionUID = 9103134389086900045L;

    private String message;
}
