package com.bloomtech.asylumrgbea.controllers.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class BadRequestException extends RuntimeException{

    private final static long serialVersionUID = 4529555431977284783L;

    @Setter
    private String message;

}
