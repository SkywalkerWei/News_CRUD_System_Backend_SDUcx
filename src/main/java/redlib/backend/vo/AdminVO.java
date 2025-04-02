package redlib.backend.vo;

import java.util.Date;

import lombok.Data;

@Data
public class AdminVO {
    private Integer id;

    private String userCode;

    private String name;

    private Integer sex;

    private Boolean enabled;

    private String password;

    private String phone;

    private String email;

    private Date createdAt;

    private Integer createdBy;

    private String createdByDesc;

    private Date updatedAt;

    private Integer updatedBy;

    private String updatedByDesc;
}
