package life.majiang.community.controller;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import life.majiang.community.dto.User;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Description
 * @auther Admin
 * @date 2020/6/21 17:03
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.client.uri}")
    private String clientUri;

    /**
     * 自动装配UserMapper
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * @Description 调用Github接口登录验证token
     * @param code
     * @param state
     * @param request
     * @return
     */
    @GetMapping("/callback")
    public String  callbcck(@RequestParam(name = "code") String code,
                            @RequestParam(name = "state") String state,
                            HttpServletRequest request,
                            HttpServletResponse response){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();//当光标在对象accessTokenDTO后面时可以 按shelf + enter快捷键到下一行
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(clientUri);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);//new AccessTokenDTO() 按ctrl + alt +v快捷键
        GithubUser GithubUser = githubProvider.getUser(accessToken);
        if(GithubUser !=null){
            //登录成功 添加用户信息到数据库
            User user = new User();
            user.setAccountId(String.valueOf(GithubUser.getId()));
            user.setName(GithubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            //把用户信息存储到cookie中
            response.addCookie(new Cookie("token", token));
            //把用户信息存储到session中
//            request.getSession().setAttribute("user",GithubUser);
            return "redirect:/";
        }else {
            //登录失败
            return "redirect:/";
        }

    }


}
