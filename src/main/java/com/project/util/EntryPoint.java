package com.project.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EntryPoint implements AuthenticationEntryPoint {

    public void commence (HttpServletRequest req, HttpServletResponse res, AuthenticationException exception) throws IOException {
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED,exception.getMessage());
    }
}
