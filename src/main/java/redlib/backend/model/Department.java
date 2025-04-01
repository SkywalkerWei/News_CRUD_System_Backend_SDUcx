package redlib.backend.model;

import java.util.Date;

import lombok.Data;

@Data
public class Department {
    private Integer id;
    private String departmentName;
    private String contact;
    private String contactPhone;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private Integer createdBy;
    private Integer updatedBy;
    private Boolean deleted;
}