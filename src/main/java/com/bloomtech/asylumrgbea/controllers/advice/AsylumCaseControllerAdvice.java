package com.bloomtech.asylumrgbea.controllers.advice;

import com.bloomtech.asylumrgbea.controllers.exceptions.AsylumCaseNotFoundException;
import com.bloomtech.asylumrgbea.controllers.exceptions.BadRequestException;
import com.bloomtech.asylumrgbea.models.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@ResponseBody
public class AsylumCaseControllerAdvice {

    @ExceptionHandler(AsylumCaseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleAsylumCaseNotFoundException(AsylumCaseNotFoundException asylumCaseNotFoundException,
                                                      HttpServletRequest httpServletRequest) {

        return new ErrorDto(asylumCaseNotFoundException.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleBadRequestException(BadRequestException badRequestException,
                                              HttpServletRequest httpServletRequest) {

        return new ErrorDto(badRequestException.getMessage());
    }
}
