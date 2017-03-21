package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by alan.zheng on 2017/3/21.
 */
@Controller
public class ConfigController {
    @RequestMapping(value = "/config/index")
    public String index(){
        return "/config/index";
    }
}
