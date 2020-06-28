package life.majiang.community.dto;

import lombok.Data;

/**
 * @Description 用户信息的实体类
 * @auther Admin
 * @date 2020/6/28 9:41
 */
@Data
public class User {
    //用户id
    private Integer id;
    //账号id
    private String accountId;
    //用户姓名
    private String name;
    //token
    private String token;
    //添加时间
    private Long gmtCreate;
    //修改人
    private Long gmtModified;
}
