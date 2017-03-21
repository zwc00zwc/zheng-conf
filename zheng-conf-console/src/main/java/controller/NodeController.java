package controller;

import conf.db.model.Node;
import conf.db.model.PageModel;
import conf.db.model.query.NodeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import services.NodeService;

import javax.servlet.http.HttpSession;

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
        return "node/index";
    }
}
