package redlib.backend.dto;

import lombok.Data;

@Data
public class DepartmentDTO {
    private Integer id;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 描述
     */
    private String description;
}
