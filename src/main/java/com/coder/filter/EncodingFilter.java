package com.coder.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(EncodingFilter.class);

    String charset = null;

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        if (charset != null && !"".equals(charset)){
            req.setCharacterEncoding(charset);
        }
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        String charset = config.getInitParameter("charset");
        this.charset = charset;
        logger.info("编码过滤器初始参数:"+charset);
    }

}

