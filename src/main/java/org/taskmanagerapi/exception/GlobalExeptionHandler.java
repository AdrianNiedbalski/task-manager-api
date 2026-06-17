package org.taskmanagerapi.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.taskmanagerapi.task.exception.TaskNotFoundException;

import java.util.List;
import java.util.Map;

public class GlobalExeptionHandler {

    @ExceptionHandler
    public List<ErrorResponse> TaskNotFoundException (TaskNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        Map<String, String> errors = errorResponse.getErrors();

        return errors;
    }
}
