package com.tenfar.yiyi.advice;

import com.tenfar.yiyi.common.Result;
import com.tenfar.yiyi.common.ResultGenerator;
import com.tenfar.yiyi.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Arrays;
import java.util.List;

/**
 * @author tenfar
 */
@ControllerAdvice(basePackages = {"com.tenfar.yiyi.controller"})
public class ResponseMessageAdvice implements ResponseBodyAdvice<Object> {
    final Logger logger = LoggerFactory.getLogger(ResponseMessageAdvice.class);
    List<MediaType> mediaTypes =
            Arrays.asList(MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_JSON_UTF8,
                    MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_OCTET_STREAM, MediaType.IMAGE_GIF,
                    MediaType.MULTIPART_MIXED
            );

    /**
     * 格式化输出为统一JSON格式
     *
     * @param object
     * @param methodParameter
     * @param mediaType
     * @param converter
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object object, MethodParameter methodParameter,
                                  MediaType mediaType, Class<? extends HttpMessageConverter<?>> converter,
                                  ServerHttpRequest request, ServerHttpResponse response) {
//         如果请求的ContentType不是application/json，则原样返回
        if (!mediaTypes.contains(mediaType)) {
            return object;
        }

        if (object instanceof Result) {
            return object;
        }

        // 当范围实体非ResponseMsg实，则格式化为ResponseMsg
        object = ResultGenerator.genSuccessResult(object);


        // 记录日志
        try {
            logger.info("HTTP_RSP: {}", JsonUtils.obj2String(object));
        } catch (Exception e) {
            logger.error("parse return object error: {}", e.getMessage());
        }

        return object;
    }

    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> httpMessageConverter) {
        return true;
    }

}
