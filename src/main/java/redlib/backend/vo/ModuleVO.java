package redlib.backend.vo;

import java.util.List;

import lombok.Data;

@Data
public class ModuleVO {
    private String id;
    private List<PrivilegeVO> privilegeList;
}
