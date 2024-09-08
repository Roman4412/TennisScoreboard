package com.pustovalov.servlet.filter;

import com.pustovalov.exception.InvalidMatchPlayerException;
import com.pustovalov.exception.InvalidRequestParamException;
import com.pustovalov.exception.MatchAlreadyPersistException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter
public class ExceptionFilter implements Filter {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    try {
      chain.doFilter(request, response);
    } catch (InvalidRequestParamException e) {
      HttpServletResponse resp = (HttpServletResponse) response;
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      request.setAttribute("error message", e.getMessage());
    } catch (InvalidMatchPlayerException | MatchAlreadyPersistException e) {
      HttpServletResponse resp = (HttpServletResponse) response;
      resp.setStatus(HttpServletResponse.SC_CONFLICT);
      request.setAttribute("error message", e.getMessage());
    }
  }
}