package org.taskmanagerapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.taskmanagerapi.task.exception.TaskNotFoundException;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExeptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public List<ErrorResponse> TaskNotFoundException (TaskNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        Map<String, String> errors = errorResponse.getErrors();

        return errors;
    }
}
