package life.majiang.community.dto;

import lombok.Data;

/**
 * @Description token参数的实体类
 * @auther Admin
 * @date 2020/6/21 17:21
 */
@Data
public class AccessTokenDTO {
    //github接口的id
    private String client_id;
    //github接口的秘钥
    private String  client_secret;
    //
    private String code;
    //统一资源名称
    private String redirect_uri;
    //状态
    private String state;
}
