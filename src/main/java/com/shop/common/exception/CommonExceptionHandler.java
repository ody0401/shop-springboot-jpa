package com.shop.common.exception;

import com.shop.exception.CartProductNotFoundException;
import com.shop.exception.OverCountException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest req, HttpServletResponse res) {
        if(isAjax(req)) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        } else {
            log.info("error : {}", ex.getMessage());
            return "error/accessError";
        }
    }

    @ExceptionHandler(CartProductNotFoundException.class)
    public void handleCartProductNotFoundException(CartProductNotFoundException ex, HttpServletRequest req, HttpServletResponse res) {
        log.info("error : {}", ex.getMessage());
        if(isAjax(req)) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            throw ex;
        }
    }

    @ExceptionHandler(OverCountException.class)
    public void handleOverCountException(OverCountException ex, HttpServletRequest req, HttpServletResponse res) {
        log.info("error : {}", ex.getMessage());
        if(isAjax(req)) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            throw ex;
        }
    }

    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

}
