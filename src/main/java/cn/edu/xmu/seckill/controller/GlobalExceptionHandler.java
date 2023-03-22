package cn.edu.xmu.seckill.controller;

import cn.edu.xmu.seckill.exception.SeckillException;
import cn.edu.xmu.seckill.utils.ReturnNo;
import cn.edu.xmu.seckill.utils.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;
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
        return ResponseEntity.badRequest().header("Content-Type", "application/json; charset=utf-8").body(new ReturnObject(ReturnNo.ERROR, "", errors));
    }

    @ExceptionHandler(SeckillException.class)
    public ResponseEntity<Object> handleSeckillExceptions(
            SeckillException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getReturnNo().getMessage());
        return ResponseEntity.badRequest().header("Content-Type", "application/json; charset=utf-8").body(new ReturnObject(ex.getReturnNo(), "", errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleExceptions(
            Exception ex) {
        return ResponseEntity.badRequest().header("Content-Type", "application/json; charset=utf-8").body(new ReturnObject(ReturnNo.ERROR, "", "出现了些错误"));
    }

}