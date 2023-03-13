package cn.edu.xmu.seckill.controller;

import cn.edu.xmu.seckill.exception.SeckillException;
import cn.edu.xmu.seckill.utils.ReturnNo;
import cn.edu.xmu.seckill.utils.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
        return new ResponseEntity<>(new ReturnObject(ReturnNo.ERROR, "", errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SeckillException.class)
    public ResponseEntity<Object> handleSeckillExceptions(
            SeckillException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getReturnNo().getMessage());
        return new ResponseEntity<>(new ReturnObject(ex.getReturnNo(), "", errors), HttpStatus.BAD_REQUEST);
    }

}