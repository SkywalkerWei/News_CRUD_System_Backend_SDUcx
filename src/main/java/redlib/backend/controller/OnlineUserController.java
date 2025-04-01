package redlib.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import redlib.backend.annotation.BackendModule;
import redlib.backend.annotation.Privilege;
import redlib.backend.service.TokenService;
import redlib.backend.vo.OnlineUserVO;

@RestController
@RequestMapping("/api/onlineUser")
@BackendModule({"page:页面", "kick:踢人"})
public class OnlineUserController {
    @Autowired
    private TokenService tokenService;

    @GetMapping("list")
    @Privilege("page")
    public List<OnlineUserVO> listOnlineUser() {
        return tokenService.list();
    }

    @GetMapping("kick")
    @Privilege("kick")
    public void kick(String readerToken) {
        tokenService.kick(readerToken);
    }
}
