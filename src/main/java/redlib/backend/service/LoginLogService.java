package redlib.backend.service;

import redlib.backend.dto.query.LoginLogQueryDTO;
import redlib.backend.model.Page;
import redlib.backend.vo.LoginLogVO;

public interface LoginLogService {
    Page<LoginLogVO> list(LoginLogQueryDTO queryDTO);
}
