package life.majiang.community.mapper;

import life.majiang.community.dto.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 数据持久层 把用户信息存到MySQL数据库中
 * @auther Admin
 * @date 2020/6/28 9:43
 */

@Mapper
public interface UserMapper {
    /**
     * @Description 插入数据
     * @param user
     */
    @Insert("insert into user(account_id, name, token, gmt_create, gmt_modified) values (#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified})")
     void insert(User user);
}
