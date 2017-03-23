package controller;

import common.JsonResult;
import conf.db.model.Node;
import conf.db.model.PageModel;
import conf.db.model.query.NodeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import services.NodeService;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by alan.zheng on 2017/3/21.
 */
@Controller
public class NodeController extends BaseController {
    @Autowired
    private NodeService nodeService;

    @RequestMapping(value = "/node/index")
    public String index(Model model, HttpSession httpSession, Integer currPage){
        NodeQuery query=new NodeQuery();
        if (currPage!=null){
            query.setCurrPage(currPage);
        }
        PageModel<Node> pageModel= nodeService.queryPageList(query);
        model.addAttribute("pageModel",pageModel);
        model.addAttribute("user",getAuthUser(httpSession));
        return "/node/index";
    }

    @RequestMapping(value = "/node/add")
    public String add(Model model, HttpSession httpSession, Integer currPage){
        return "/node/add";
    }

    @ResponseBody
    @RequestMapping(value = "/node/adding")
    public JsonResult adding(Node node){
        node.setCreateTime(new Date());
        node.setUpdateTime(new Date());
        if (node.getId()>0){
            nodeService.updateNode(node);
            return jsonResult(1,"修改成功");
        }
        nodeService.insertNode(node);
        return jsonResult(1,"新增成功");
    }

    @RequestMapping(value = "/node/edit")
    public String update(Model model, HttpSession httpSession, Long nodeId){
        Node node= nodeService.queryById(nodeId);
        model.addAttribute("node",node);
        return "/config/add";
    }
}
