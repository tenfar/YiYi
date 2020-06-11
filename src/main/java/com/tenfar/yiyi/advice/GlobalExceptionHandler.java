package com.tenfar.yiyi.advice;

import com.tenfar.yiyi.common.Result;
import com.tenfar.yiyi.common.ResultGenerator;
import com.tenfar.yiyi.common.enums.ResultCode;
import com.tenfar.yiyi.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tenfar
 */
@ControllerAdvice(basePackages = {"com.tenfar.yiyi.controller"})
public class GlobalExceptionHandler {
    final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 参数错误异常处理
     *
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result argumentErrorHandler(HttpServletRequest request,
                                       MethodArgumentNotValidException exception) {

        Map<String, String> mapErrFields = new HashMap<String, String>();
        for (FieldError err : exception.getBindingResult().getFieldErrors()) {
            mapErrFields.put(err.getField(), err.getDefaultMessage());
        }
        Result responseMsg = ResultGenerator.genFailResultWithData(ResultCode.ARGUMENT_ERROR, "参数错误", mapErrFields);
        return responseMsg;
    }

    /**
     * 默认异常处理
     *
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result defaultErrorHandler(HttpServletRequest request, Exception exception) {
        Result responseMsg = new Result();
        if (exception instanceof ApiException) {
            responseMsg.setCode(((ApiException) exception).getErrorCode());
            responseMsg.setMessage(exception.getMessage());
        } else if (exception instanceof ConstraintViolationException) {
            responseMsg.setCode(0);
            for (ConstraintViolation<?> e : ((ConstraintViolationException) exception)
                    .getConstraintViolations()) {
                responseMsg.setMessage(e.getMessage());
                break;
            }
        } else if (exception instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            responseMsg.setCode(404);
            responseMsg.setMessage("HTTP 404 NOT FOUND");
        } else {
            responseMsg.setCode(500);
            responseMsg.setMessage("Internal Server Error");
        }
        logger.error("API_ERR: {}", exception.getMessage());
        return responseMsg;
    }
}
