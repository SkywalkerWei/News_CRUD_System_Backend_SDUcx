package redlib.backend.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redlib.backend.dto.AdminDTO;
import redlib.backend.dto.query.KeywordQueryDTO;
import redlib.backend.model.Page;
import redlib.backend.vo.AdminVO;
import redlib.backend.vo.ModuleVO;

public interface AdminService {
    List<ModuleVO> listModules();

    Map<Integer, String> getNameMap(Set<Integer> adminIds);

    Page<AdminVO> list(KeywordQueryDTO queryDTO);

    AdminDTO getDetail(Integer id);

    Integer update(AdminDTO adminDTO);

    Integer add(AdminDTO adminDTO);

    Integer delete(List<Integer> ids);

    void updatePassword(String oldPassword, String password);
}
