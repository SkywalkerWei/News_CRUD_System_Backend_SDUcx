package redlib.backend.model;

import java.util.Date;

import lombok.Data;

@Data
public class LoginLog {
    
    private Long id;

    private String userCode;

    private String ipAddress;

    private String name;

    private String os;

    private String browser;

    private Date createdAt;
}