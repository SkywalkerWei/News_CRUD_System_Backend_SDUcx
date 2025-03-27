package redlib.backend.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import redlib.backend.dao.AdminMapper;
import redlib.backend.dao.AdminPrivMapper;
import redlib.backend.dao.LoginLogMapper;
import redlib.backend.model.Admin;
import redlib.backend.model.AdminPriv;
import redlib.backend.model.LoginLog;
import redlib.backend.model.Token;
import redlib.backend.service.TokenService;
import redlib.backend.service.utils.TokenUtils;
import redlib.backend.utils.FormatUtils;
import redlib.backend.vo.OnlineUserVO;

@Service
public class TokenServiceImpl implements TokenService {
    private static final long TOKEN_EXPIRE_TIME = 30 * 60 * 1000; // 30分钟过期

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Autowired
    private AdminPrivMapper adminPrivMapper;

    private Map<String, Token> tokenMap = new ConcurrentHashMap<>(1 << 8);

    /**
     * 用户登录，返回令牌信息
     */
    @Override
    public Token login(String userId, String password, String ipAddress, String userAgent) {
        Admin admin = adminMapper.login(userId, FormatUtils.password(password));
        Assert.notNull(admin, "用户名或者密码错误");
        Assert.isTrue(Boolean.TRUE.equals(admin.getEnabled()), "此账户已经禁用，不能登录");
        
        // 清理该用户的旧令牌
        cleanUserTokens(userId);
        
        Token token = new Token();
        token.setAccessToken(makeToken());
        token.setUserId(admin.getId());
        token.setLastAction(new Date());
        token.setDepartment(admin.getDepartment());
        token.setSex(admin.getSex());
        token.setIpAddress(ipAddress);
        token.setUserCode(userId);
        token.setUserName(admin.getName());
        token.setPrivSet(new HashSet<>());
        
        List<AdminPriv> privList = adminPrivMapper.list(admin.getId());
        for (AdminPriv priv : privList) {
            token.getPrivSet().add(priv.getModId() + '.' + priv.getPriv());
        }

        try {
            UserAgent ua = UserAgent.parseUserAgentString(userAgent);
            Browser browser = ua.getBrowser();
            OperatingSystem os = ua.getOperatingSystem();
            Version version = ua.getBrowserVersion();
            
            if (browser != null) {
                token.setBrowser(browser.getName());
                if (version != null) {
                    token.setBrowser(token.getBrowser() + " V" + version.getVersion());
                }
            }

            if (os != null) {
                token.setOs(os.getName());
                if (os.getDeviceType() != null) {
                    token.setDevice(os.getDeviceType().getName());
                }
            }

            // 记录登录日志
            LoginLog loginLog = new LoginLog();
            loginLog.setName(token.getUserName());
            loginLog.setUserCode(token.getUserCode());
            loginLog.setIpAddress(token.getIpAddress());
            loginLog.setBrowser(token.getBrowser());
            loginLog.setOs(token.getOs());
            loginLogMapper.insert(loginLog);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        tokenMap.put(token.getAccessToken(), token);
        return token;
    }

    /**
     * 清理指定用户的所有令牌
     */
    private void cleanUserTokens(String userId) {
        tokenMap.values().removeIf(token -> token.getUserCode().equals(userId));
    }

    /**
     * 根据token获取令牌信息
     */
    @Override
    public Token getToken(String accessToken) {
        if (FormatUtils.isEmpty(accessToken)) {
            return null;
        }

        Token token = tokenMap.get(accessToken);
        if (token != null) {
            // 检查令牌是否过期
            Date lastAction = token.getLastAction();
            if (lastAction != null && System.currentTimeMillis() - lastAction.getTime() > TOKEN_EXPIRE_TIME) {
                tokenMap.remove(accessToken);
                return null;
            }
        }
        return token;
    }

    /**
     * 更新令牌信息
     */
    @Override
    public void updateToken(Token token) {
        if (token != null && token.getAccessToken() != null) {
            tokenMap.put(token.getAccessToken(), token);
        }
    }

    /**
     * 登出系统
     */
    @Override
    public void logout(String accessToken) {
        if (accessToken != null) {
            tokenMap.remove(accessToken);
        }
    }

    /**
     * 获取在线用户列表
     */
    @Override
    public List<OnlineUserVO> list() {
        // 清理过期令牌
        cleanExpiredTokens();
        Collection<Token> tokens = tokenMap.values();
        return tokens.stream()
            .map(TokenUtils::convertToVO)
            .collect(Collectors.toList());
    }

    /**
     * 清理过期的令牌
     */
    private void cleanExpiredTokens() {
        long now = System.currentTimeMillis();
        tokenMap.values().removeIf(token -> 
            token.getLastAction() != null && 
            now - token.getLastAction().getTime() > TOKEN_EXPIRE_TIME
        );
    }

    /**
     * 将在线用户踢出系统
     */
    @Override
    public void kick(String accessToken) {
        Assert.hasText(accessToken, "访问令牌不能为空");
        Token token = tokenMap.get(accessToken);
        Assert.notNull(token, "用户未登录或会话已过期");
        tokenMap.remove(accessToken);
    }

    private String makeToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}