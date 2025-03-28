package redlib.backend.service;

import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import redlib.backend.dto.DepartmentDTO;
import redlib.backend.dto.query.DepartmentQueryDTO;
import redlib.backend.model.Page;
import redlib.backend.vo.DepartmentVO;

public interface DepartmentService {
    Page<DepartmentVO> listByPage(DepartmentQueryDTO queryDTO);

    /**
     * 新建部门
     *
     * @param departmentDTO 部门输入对象
     * @return 部门编码
     */
    Integer addDepartment(DepartmentDTO departmentDTO);

    DepartmentDTO getById(Integer id);

    /**
     * 更新部门数据
     *
     * @param departmentDTO 部门输入对象
     * @return 部门编码
     */
    Integer updateDepartment(DepartmentDTO departmentDTO);

    /**
     * 根据编码列表，批量删除部门
     *
     * @param ids 编码列表
     */
    void deleteByCodes(List<Integer> ids);

    Workbook export(DepartmentQueryDTO queryDTO);

    int importDepartment(
            InputStream inputStream,
            String fileName) throws Exception;
}
