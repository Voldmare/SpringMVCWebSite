package com.yablon.volodymyr.controller;

import com.yablon.volodymyr.exceptions.NullEntityReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ApplicationExceptionController {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullEntityReferenceException.class)
    public ModelAndView handleNullEntityReferenceException(NullEntityReferenceException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("info", ex.getMessage());
        modelAndView.addObject("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.addObject("exClass", ex.getClass());
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(EntityNotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("info", ex.getMessage());
        modelAndView.addObject("statusCode", HttpStatus.NOT_FOUND);
        modelAndView.addObject("exClass", ex.getClass().getSimpleName());
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.addObject("info", ex.getMessage());
        modelAndView.addObject("exClass", ex.getClass());
        return modelAndView;
    }
}
