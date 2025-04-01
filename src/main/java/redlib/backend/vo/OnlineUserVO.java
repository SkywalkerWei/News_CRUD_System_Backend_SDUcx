package redlib.backend.vo;

import java.util.Date;

import lombok.Data;

@Data
public class OnlineUserVO {
    private String accessToken;
    
    private boolean backend;

    private String userName;

    private String userCode;

    private Integer userId;

    private Integer roleId;

    private String roleName;

    private Date lastAction;

    private String sex;

    private String department;

    private String ipAddr;

    private String os;

    private String browser;

    private String browserVersion;

    private String device;

    private String country;

    private String location;

    private String isp;

    private Long totalNetFlow;

    private String referer;
}
