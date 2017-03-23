package services.impl;

import conf.db.dal.NodeDal;
import conf.db.model.Node;
import conf.db.model.PageModel;
import conf.db.model.query.NodeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.NodeService;

import java.util.List;

/**
 * Created by alan.zheng on 2017/3/21.
 */
@Service
public class NodeServiceImpl implements NodeService {
    private static Logger logger = LoggerFactory.getLogger(NodeServiceImpl.class);
    @Autowired
    private NodeDal nodeDal;

    public List<Node> queryList() {
        try {
            return nodeDal.queryList();
        } catch (Exception e) {
            logger.error("查询节点列表异常"+e.toString());
        }
        return null;
    }

    public PageModel<Node> queryPageList(NodeQuery query) {
        PageModel<Node> pageModel= null;
        try {
            pageModel = nodeDal.queryPageList(query);
        } catch (Exception e) {
            logger.error("分页查询节点列表异常"+e.toString());
        }
        return pageModel;
    }
}
