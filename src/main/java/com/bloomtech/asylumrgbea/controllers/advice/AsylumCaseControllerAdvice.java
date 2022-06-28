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

    /**
     * This method is triggered when an AsylumCaseNotFoundException is given to the controller to handle.
     * @param asylumCaseNotFoundException The exception that is thrown.
     * @param httpServletRequest passed as an argument to the servlet's service methods (doGet, doPost, etc).
     * @return An ErrorDto containing the exception's method.
     */
    @ExceptionHandler(AsylumCaseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleAsylumCaseNotFoundException(AsylumCaseNotFoundException asylumCaseNotFoundException, HttpServletRequest httpServletRequest) {
        return new ErrorDto(asylumCaseNotFoundException.getMessage());
    }

    /**
     * This method is triggered when an BadRequestException is given to the controller to handle.
     * @param badRequestException The exception that is thrown.
     * @param httpServletRequest passed as an argument to the servlet's service methods (doGet, doPost, etc).
     * @return An ErrorDto containing the exception's method.
     */
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleBadRequestException(BadRequestException badRequestException, HttpServletRequest httpServletRequest) {
        return new ErrorDto(badRequestException.getMessage());
    }
}
