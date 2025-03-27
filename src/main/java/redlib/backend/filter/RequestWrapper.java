package redlib.backend.filter;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import redlib.backend.utils.FormatUtils;

public class RequestWrapper extends HttpServletRequestWrapper {
    private String accessToken;

    @SuppressWarnings("all")
    public RequestWrapper(HttpServletRequest request) {
        super(request);
        
        // 1. 先从 Authorization 头获取令牌
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            accessToken = authHeader.substring(7);
        }
        
        // 2. 如果没有，从 Cookie 获取
        if (FormatUtils.isEmpty(accessToken)) {
            accessToken = FormatUtils.trimToEmpty(getCookie("accessToken"));
        }
        
        // 3. 如果还没有，从请求参数获取
        if (FormatUtils.isEmpty(accessToken)) {
            accessToken = FormatUtils.trimToEmpty(request.getParameter("accessToken"));
        }
    }

    private String getCookie(String cookieName) {
        Cookie[] cookies = getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public HttpServletRequest getRequest() {
        return (HttpServletRequest) super.getRequest();
    }
}