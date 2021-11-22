package com.java.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author lingfeng
 * @date 2021-10-21
 */
public class CharacterEncodingFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.setContentType("text/css;charset=UTF-8");

    }

    public void destroy() {

    }
}
