package example.controller;

import conf.utility.PropertiesUtility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by alan.zheng on 2017/3/16.
 */
@Controller
public class HomeController {
    @ResponseBody
    @RequestMapping(value = "/index")
    public String index(){
        PropertiesUtility propertiesUtility=new PropertiesUtility("local.properties");
        String old= propertiesUtility.getProperty("test");
        propertiesUtility.setProperty("test","newtest");

        String newr= propertiesUtility.getProperty("test");
        propertiesUtility.storeProperty("local.properties");
        return "index【"+old+"】【"+newr+"】";
    }
}
