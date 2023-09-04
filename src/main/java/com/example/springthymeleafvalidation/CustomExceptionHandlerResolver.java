package com.example.springthymeleafvalidation;

import com.example.springthymeleafvalidation.dto.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class CustomExceptionHandlerResolver implements HandlerExceptionResolver {

    @Override
    @SneakyThrows
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        String requestURI = request.getRequestURI();
        log.info("발생한 예외 : {}, 요청경로 : {}",ex.getClass().getName(), requestURI);

        // api컨트롤러로 들어온 요청이 아닐 시 수동처리하지않고 디폴트설정을 사용하도록 넘김.
        if (!requestURI.startsWith("/api/")){
            return null;
        }
        // 아래와 같이 직접 페이지를 렌더링할수있음.
        if (false){
            ModelAndView mav = new ModelAndView("error/4xx");
            ModelMap modelMap = mav.getModelMap();
            modelMap.put("path", requestURI);
            modelMap.put("status", 405);
            modelMap.put("error", HttpStatus.METHOD_NOT_ALLOWED);
            return mav;
        }

        // 에러 코드와 메시지를 담을 ResponseDTO 생성
        ResponseDTO responseDTO = null;
        if(ex instanceof HttpRequestMethodNotSupportedException){
            responseDTO = new ResponseDTO(EnumStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        } else {
            responseDTO = new ResponseDTO(EnumStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
        // ResponseDTO를 JSON 문자열로 변환
        String jsonResponse = new ObjectMapper().writeValueAsString(responseDTO);

        // HttpServletResponse 객체를 이용해 JSON 응답 전송
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        response.getWriter().write(jsonResponse);

        // 빈 ModelAndView 객체를 반환하여
        // ResponseStatusExceptionResolver과 DefaultHandlerExceptionResolver에 할당될 예외상황을 정상처리
        return new ModelAndView();
    }
}
