package controller;

import common.JsonResult;
import conf.db.model.Configuration;
import conf.db.model.Node;
import conf.db.model.PageModel;
import conf.db.model.query.ConfigurationQuery;
import conf.utility.PropertiesUtility;
import conf.zookeeper.ZookeeperConfig;
import conf.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import services.ConfigurationService;
import services.NodeService;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by alan.zheng on 2017/3/21.
 */
@Controller
public class ConfigController extends BaseController {
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private NodeService nodeService;

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
        List<Node> nodeList= nodeService.queryList();
        model.addAttribute("nodeList",nodeList);
        return "/config/add";
    }

    @RequestMapping(value = "/config/edit")
    public String update(Model model, HttpSession httpSession, Long configId){
        List<Node> nodeList= nodeService.queryList();
        Configuration configuration = configurationService.queryById(configId);
        model.addAttribute("nodeList",nodeList);
        model.addAttribute("configuration",configuration);
        return "/config/add";
    }

    @ResponseBody
    @RequestMapping(value = "/config/adding")
    public JsonResult adding(Configuration configuration){
        configuration.setCreateTime(new Date());
        configuration.setUpdateTime(new Date());
        if (configuration.getId()>0){
            configurationService.updateConfiguration(configuration);
            return jsonResult(1,"修改成功");
        }
        configurationService.insertConfiguration(configuration);
        return jsonResult(1,"新增成功");
    }

    @ResponseBody
    @RequestMapping(value = "/config/update")
    public JsonResult update(String keyvalue){
//        ZookeeperConfig zookeeperConfig=new ZookeeperConfig();
//        zookeeperConfig.setServerLists("localhost:2181");
//        zookeeperConfig.setNamespace("conf");
//        zookeeperConfig.setAuth("auth");
//        ZookeeperRegistryCenter zookeeperRegistryCenter=new ZookeeperRegistryCenter(zookeeperConfig);
//        zookeeperRegistryCenter.init();
//        zookeeperRegistryCenter.create("/conf/1",keyvalue);
//        return "update";
        return jsonResult(1,"update");
    }

}
