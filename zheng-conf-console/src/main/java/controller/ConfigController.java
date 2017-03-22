package controller;

import conf.db.model.Configuration;
import conf.db.model.PageModel;
import conf.db.model.query.ConfigurationQuery;
import conf.utility.PropertiesUtility;
import conf.zookeeper.ZookeeperConfig;
import conf.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import services.ConfigurationService;

import javax.servlet.http.HttpSession;

/**
 * Created by alan.zheng on 2017/3/21.
 */
@Controller
public class ConfigController extends BaseController {
    @Autowired
    private ConfigurationService configurationService;

    @RequestMapping(value = "/config/index")
    public String index(Model model, HttpSession httpSession, Integer currPage){
        ConfigurationQuery query=new ConfigurationQuery();
        if (currPage!=null){
            query.setCurrPage(currPage);
        }
        PageModel<Configuration> pageModel= configurationService.queryPageList(query);
        model.addAttribute("pageModel",pageModel);
        model.addAttribute("user",getAuthUser(httpSession));
        return "/config/index";
    }

    @RequestMapping(value = "/config/add")
    public String add(Model model, HttpSession httpSession, Integer currPage){
//        model.addAttribute("rolelist",list);
//        model.addAttribute("member",member);
        return "/config/add";
    }

    @ResponseBody
    @RequestMapping(value = "/config/update")
    public String update(String keyvalue){
        ZookeeperConfig zookeeperConfig=new ZookeeperConfig();
        zookeeperConfig.setServerLists("localhost:2181");
        zookeeperConfig.setNamespace("conf");
        zookeeperConfig.setAuth("auth");
        ZookeeperRegistryCenter zookeeperRegistryCenter=new ZookeeperRegistryCenter(zookeeperConfig);
        zookeeperRegistryCenter.init();
        zookeeperRegistryCenter.create("/conf/1",keyvalue);
        return "update";
    }

}
