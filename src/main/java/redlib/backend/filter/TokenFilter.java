package redlib.backend.filter;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import redlib.backend.model.Token;
import redlib.backend.service.TokenService;
import redlib.backend.utils.ThreadContextHolder;

@Component
@Slf4j
public class TokenFilter implements Filter {

    @Autowired
    private TokenService tokenService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        RequestWrapper request = new RequestWrapper((HttpServletRequest) servletRequest);
        
        // 从多个位置获取令牌
        String accessToken = request.getAccessToken();
        
        Token token = null;
        if (accessToken != null && !accessToken.isEmpty()) {
            token = tokenService.getToken(accessToken);
            if (token != null) {
                // 更新最后活动时间
                token.setLastAction(new Date());
                tokenService.updateToken(token);
            }
        }

        // 设置令牌到上下文
        ThreadContextHolder.setToken(token);
        
        // 继续过滤器链
        filterChain.doFilter(request.getRequest(), servletResponse);
    }
}