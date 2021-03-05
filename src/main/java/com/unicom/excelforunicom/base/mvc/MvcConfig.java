package com.unicom.excelforunicom.base.mvc;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unicom.excelforunicom.base.result.Result;
import com.unicom.excelforunicom.base.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author GaoLiwei
 * @date 2019/4/15
 */
@Configuration
@Slf4j
public class MvcConfig implements WebMvcConfigurer {

    /**
     * 当前激活的配置文件
     */
    @Value("${spring.profiles.active}")
    private String env;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/pic/**").addResourceLocations("file:E:/work/pic/");
    }

    /**
     * 统一异常处理
     *
     * @author GaoLiWei
     * @date 13:51 2019/4/15
     **/
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add((HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) -> {
                    Result result = new Result();
                    if (handler instanceof HandlerMethod) {
                        HandlerMethod handlerMethod = (HandlerMethod) handler;
                            result.setCode(ResultCode.INTERNAL_SERVER_ERROR.code);
                            result.setMsg("接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员");
                            String message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                                    request.getRequestURI(),
                                    handlerMethod.getBean().getClass().getName(),
                                    handlerMethod.getMethod().getName(),
                                    e.getMessage());
                            log.error(message, e);
                    } else {
                        if (e instanceof NoHandlerFoundException) {
                            result.setCode(ResultCode.NOT_FOUND.code);
                            result.setMsg("接口 [" + request.getRequestURI() + "] 不存在");
                        } else {
                            result.setCode(ResultCode.INTERNAL_SERVER_ERROR.code);
                            result.setMsg(e.getMessage());
                            log.error(e.getMessage(), e);
                        }
                  }
                    responseResult(response, result);
                    return new ModelAndView();
                }
        );
    }

    private void responseResult(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }


    /**
     * 配置跨域
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
//                .allowCredentials(true)
                .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE")
                .allowedHeaders("Authorization", "Origin", "X-Requested-With", "X-File-Name", "Content-Type", "Accept")
//                .maxAge(3600)
        ;
    }


    /**
     * 设置解析json的格式
     *
     * @return MappingJackson2HttpMessageConverter
     */
    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        converter.setObjectMapper(mapper);
        //设置中文编码格式
        List<MediaType> list = new ArrayList<>();
        list.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(list);
        return converter;
    }


    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }

}
