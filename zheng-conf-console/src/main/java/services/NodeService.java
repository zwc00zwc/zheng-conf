package services;

import conf.db.model.Node;
import conf.db.model.PageModel;
import conf.db.model.query.NodeQuery;

import java.util.List;

/**
 * Created by alan.zheng on 2017/3/21.
 */
public interface NodeService {
    Node queryById(Long nodeid);

    List<Node> queryList();

    PageModel<Node> queryPageList(NodeQuery query);

    boolean insertNode(Node node);

    boolean updateNode(Node node);
}
