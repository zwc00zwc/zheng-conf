package services.impl;

import conf.db.dal.ConfigurationDal;
import conf.db.model.Configuration;
import conf.db.model.PageModel;
import conf.db.model.query.ConfigurationQuery;
import org.springframework.stereotype.Service;
import services.ConfigurationService;

import javax.annotation.Resource;

/**
 * Created by alan.zheng on 2017/3/21.
 */
@Service
public class ConfigurationServiceImpl implements ConfigurationService {
    @Resource
    private ConfigurationDal configurationDal;
    public PageModel<Configuration> queryPageList(ConfigurationQuery query) {
        PageModel<Configuration> pageModel= null;
        try {
            pageModel = configurationDal.queryPageList(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageModel;
    }
}
