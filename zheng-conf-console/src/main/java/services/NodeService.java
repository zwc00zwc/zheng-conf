package services;

import conf.db.model.Node;
import conf.db.model.PageModel;
import conf.db.model.query.NodeQuery;

/**
 * Created by alan.zheng on 2017/3/21.
 */
public interface NodeService {
    PageModel<Node> queryPageList(NodeQuery query);
}
