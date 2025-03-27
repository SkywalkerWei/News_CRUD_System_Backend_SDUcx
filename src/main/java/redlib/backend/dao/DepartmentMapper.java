package redlib.backend.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import redlib.backend.dto.query.DepartmentQueryDTO;
import redlib.backend.model.Department;

public interface DepartmentMapper {
    Department selectByPrimaryKey(Integer id);

    int insert(Department record);

    int updateByPrimaryKey(Department record);

    Department getByCode(
            @Param("departmentCode") String departmentCode,
            @Param("tenantCode") String tenantCode);

    Integer count(DepartmentQueryDTO queryDTO);

    List<Department> list(@Param("queryDTO") DepartmentQueryDTO queryDTO, @Param("offset") Integer offset, @Param("limit") Integer limit
    );

    void deleteByCodes(@Param("codeList") List<Integer> codeList);

    List<Department> listByCodes(
            @Param("codeList") List<String> codeList,
            @Param("tenantCode") String tenantCode);

    List<Department> listByName(
            @Param("departmentName") String departmentName,
            @Param("tenantCode") String tenantCode);
}