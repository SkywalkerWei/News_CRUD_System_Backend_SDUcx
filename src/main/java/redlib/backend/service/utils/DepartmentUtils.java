package redlib.backend.service.utils;

import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import redlib.backend.dto.DepartmentDTO;
import redlib.backend.model.Department;
import redlib.backend.utils.FormatUtils;
import redlib.backend.vo.DepartmentVO;

public class DepartmentUtils {
    /**
     * 规范并校验departmentDTO
     *
     * @param departmentDTO
     */
    public static void validateDepartment(DepartmentDTO departmentDTO) {
        FormatUtils.trimFieldToNull(departmentDTO);
        Assert.notNull(departmentDTO, "部门输入数据不能为空");
        Assert.hasText(departmentDTO.getDepartmentName(), "部门名称不能为空");
    }

    /**
     * 将实体对象转换为VO对象
     *
     * @param department 实体对象
     * @param nameMap
     * @return VO对象
     */
    public static DepartmentVO convertToVO(Department department, Map<Integer, String> nameMap) {
        DepartmentVO departmentVO = new DepartmentVO();
        BeanUtils.copyProperties(department, departmentVO);

        departmentVO.setCreatedByDesc(nameMap.get(department.getCreatedBy()));
        return departmentVO;
    }
}
