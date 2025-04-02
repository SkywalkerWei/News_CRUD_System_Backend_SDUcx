package redlib.backend.model;

import java.util.Date;

import lombok.Data;

@Data
public class Admin {
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

    private Date updatedAt;

    private Integer updatedBy;
}