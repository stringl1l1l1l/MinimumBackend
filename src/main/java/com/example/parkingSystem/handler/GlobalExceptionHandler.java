package com.example.parkingSystem.handler;

import com.example.parkingSystem.entity.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.BindException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局异常拦截
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    // 打印log
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ResponseResult handleValidException(Exception ex){
        if(ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) ex;
            logger.error("数据校验错误:{}，异常类型:{}", e.getMessage(), e.getClass());
            BindingResult bindingResult = e.getBindingResult();
            Map<String, String> map = new HashMap<>();
            bindingResult.getFieldErrors().forEach(item -> {
                String defaultMessage = item.getDefaultMessage();
                String field = item.getField();
                map.put(field, defaultMessage);
            });
            return new ResponseResult(400, "数据格式错误", map);
        }
        logger.error("{}",ex.getMessage());
        return new ResponseResult(500, ex.getMessage());
    }
}

