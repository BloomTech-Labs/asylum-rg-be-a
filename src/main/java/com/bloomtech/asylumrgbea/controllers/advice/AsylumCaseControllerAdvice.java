package com.bloomtech.asylumrgbea.controllers.advice;

import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.bloomtech.asylumrgbea.controllers.exceptions.AsylumCaseNotFoundException;
import com.bloomtech.asylumrgbea.controllers.exceptions.BadRequestException;
import com.bloomtech.asylumrgbea.controllers.exceptions.PageNotFoundException;
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
     * @param exception The AsylumCaseNotFoundException that is thrown.
     * @param request HttpServletRequest passed as an argument to the servlet's service methods
     *                (doGet, doPost, etc.).
     * @return An ErrorDto containing the exception's method.
     */
    @ExceptionHandler(AsylumCaseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleException(AsylumCaseNotFoundException exception, HttpServletRequest request) {
        return new ErrorDto(exception.getMessage());
    }

    /**
     * This method is triggered when an BadRequestException is given to the controller to handle.
     * @param exception The BadRequestException that is thrown.
     * @param request HttpServletRequest that's passed as an argument to the servlet's service methods
     *                (doGet, doPost, etc.).
     * @return An ErrorDto containing the exception's method.
     */
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleException(BadRequestException exception, HttpServletRequest request) {
        return new ErrorDto(exception.getMessage());
    }

    /**
     * This method is triggered when a PageNotFoundException is given to the controller to handle.
     * @param exception PageNotFoundException that is thrown.
     * @param request HttpServletRequest that's passed as an argument to the servlet's service methods
     *                (doGet, doPost, etc.).
     * @return An ErrorDto containing the exception's message.
     */
    @ExceptionHandler(PageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleException(PageNotFoundException exception, HttpServletRequest request) {
        return new ErrorDto(exception.getMessage());
    }

    /**
     * This method is triggered when a AmazonDynamoDBException is given to the controller to handle.
     * @param exception AmazonDynamoDBException that is thrown.
     * @param request HttpServletRequest that's passed as an argument to the servlet's service methods
     *                (doGet, doPost, etc.).
     * @return An ErrorDto containing the exception's message.
     */
    @ExceptionHandler(AmazonDynamoDBException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleException(AmazonDynamoDBException exception, HttpServletRequest request) {
        return new ErrorDto("Error: Cannot retrieve cases from database...");
    }
}
