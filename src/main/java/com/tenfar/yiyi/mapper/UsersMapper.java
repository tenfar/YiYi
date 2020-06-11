package com.tenfar.yiyi.mapper;

import com.tenfar.yiyi.model.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author tenfar
 */
@Mapper
public interface UsersMapper {
    /**
     * 按主键ID删除对应行
     *
     * @param id 主键ID
     * @return 被影响的行数
     */
    int deleteByPrimaryKey(String id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    Users findOneByName(@Param("name") String name);


}