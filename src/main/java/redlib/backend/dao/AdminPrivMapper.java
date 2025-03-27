package redlib.backend.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import redlib.backend.model.AdminPriv;

public interface AdminPrivMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminPriv record);

    AdminPriv selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(AdminPriv record);

    List<AdminPriv> list(Integer id);

    void deleteByAdminIds(@Param("ids")List<Integer> ids);
}