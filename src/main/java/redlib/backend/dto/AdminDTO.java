package redlib.backend.dto;

import java.util.List;

import lombok.Data;

@Data
public class AdminDTO {
    private Integer id;

    private String userCode;

    private String name;

    private Integer sex;

    private Boolean enabled;

    private String password;

    private String phone;

    private String email;

    private List<AdminModDTO> modList;
}
