package life.majiang.community.controller;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import life.majiang.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/callback")
    public String  callbcck(@RequestParam(name = "code") String code,
                            @RequestParam(name = "state") String state){


        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();//当光标在对象accessTokenDTO后面时可以 按shelf + enter快捷键到下一行
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(clientUri);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);//new AccessTokenDTO() 按ctrl + alt +v快捷键
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println("name:"+user.getName());

        return "index";

    }


}
