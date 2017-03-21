package services.impl;

import conf.db.dal.NodeDal;
import conf.db.model.Node;
import conf.db.model.PageModel;
import conf.db.model.query.NodeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.NodeService;

/**
 * Created by alan.zheng on 2017/3/21.
 */
@Service
public class NodeServiceImpl implements NodeService {
    @Autowired
    private NodeDal nodeDal;

    public PageModel<Node> queryPageList(NodeQuery query) {
        PageModel<Node> pageModel= null;
        try {
            pageModel = nodeDal.queryPageList(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageModel;
    }
}
