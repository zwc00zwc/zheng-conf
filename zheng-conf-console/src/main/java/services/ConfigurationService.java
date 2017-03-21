package services;

import conf.db.model.Configuration;
import conf.db.model.PageModel;
import conf.db.model.query.ConfigurationQuery;

/**
 * Created by alan.zheng on 2017/3/21.
 */
public interface ConfigurationService {
    PageModel<Configuration> queryPageList(ConfigurationQuery query);
}
