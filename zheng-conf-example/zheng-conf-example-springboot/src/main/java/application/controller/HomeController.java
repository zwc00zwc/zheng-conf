package application.controller;

import conf.ConfigManagerHelper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by XR on 2016/12/9.
 */
@RestController
@SpringBootApplication
public class HomeController {
    @RequestMapping(value = "index")
    public String index(){
        String value= ConfigManagerHelper.get("1");
        return "index key:1,value:"+value+"";
    }

    @RequestMapping(value = "index2")
    public String index2(){
        String value= ConfigManagerHelper.get("2");
        return "index2 key:2,value:"+value+"";
    }
}
