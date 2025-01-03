package com.bitesync.api.security.filter;

import com.bitesync.api.exception.EntityNotFoundException;
import com.bitesync.api.exception.ErrorResponse;
import com.bitesync.api.exception.InsufficientInventoryException;
import com.bitesync.api.exception.MenuItemNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

  private final ObjectMapper objectMapper = new ObjectMapper();
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (EntityNotFoundException e) {
      sendErrorResponse(response,
                        HttpServletResponse.SC_NOT_FOUND,
                        "Username does not exist");
    } catch (RuntimeException e) {
      sendErrorResponse(response,
                        HttpServletResponse.SC_BAD_REQUEST,
                        "BAD REQUEST: " + e.getMessage());
    }
  }

  private void sendErrorResponse(HttpServletResponse response, int status, String messages) throws IOException {
    ErrorResponse errorResponse = new ErrorResponse(messages);
    response.setStatus(status);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    response.getWriter().flush();
  }
}
