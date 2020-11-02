package com.apinizer.example.config.gzip;

public class GzipRequestFilter {
}
/*
@Component
@Order(1)
public class GzipRequestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        if ((request instanceof HttpServletRequest)) {
            HttpServletRequest req = (HttpServletRequest) request;
            String contentEncoding = req.getHeader("Content-Encoding");
            if ((contentEncoding != null)
                    && (contentEncoding.toLowerCase().indexOf("gzip") > -1)) {
                request = new GzipRequestWrapper(
                        (HttpServletRequest) request);
            }
        }
        ((HttpServletRequest)request).getParts();
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private boolean supportsGzip(ServletRequest request) {
        String acceptEncoding = ((HttpServletRequest) request).getHeader("Accept-Encoding");
        return acceptEncoding != null && acceptEncoding.contains("gzip");
    }
}
*/