package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by alan.zheng on 2017/3/21.
 */
@Controller
public class NodeController extends BaseController {
    @RequestMapping(value = "/node/index")
    public String index(){
        return "node/index";
    }
}
