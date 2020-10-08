package ru.geekbrains.lesson6_spring_boot.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.lesson6_spring_boot.exceptions.NotFoundException;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView notFoundExceptionHandler(NotFoundException exception){
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.getModel().put("someAttr", exception.getField());
        return modelAndView;
    }
}
