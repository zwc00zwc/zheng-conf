package services.impl;

import conf.db.dal.ConfigurationDal;
import conf.db.model.Configuration;
import conf.db.model.PageModel;
import conf.db.model.query.ConfigurationQuery;
import conf.utility.PropertiesUtility;
import conf.zookeeper.ZookeeperConfig;
import conf.zookeeper.ZookeeperRegistryCenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import services.ConfigurationService;

import javax.annotation.Resource;

/**
 * Created by alan.zheng on 2017/3/21.
 */
@Service
public class ConfigurationServiceImpl implements ConfigurationService {
    private static Logger logger = LoggerFactory.getLogger(NodeServiceImpl.class);
    @Resource
    private ConfigurationDal configurationDal;

    public Configuration queryById(Long configId) {
        try {
            return configurationDal.queryById(configId);
        } catch (Exception e) {
            logger.error("查询配置异常"+e.toString());
        }
        return null;
    }

    public PageModel<Configuration> queryPageList(ConfigurationQuery query) {
        PageModel<Configuration> pageModel= null;
        try {
            pageModel = configurationDal.queryPageList(query);
        } catch (Exception e) {
            logger.error("分页查询配置异常"+e.toString());
        }
        return pageModel;
    }

    public boolean insertConfiguration(Configuration configuration) {
        try {
            if (configurationDal.insertConfiguration(configuration)>0){
                notify(configuration);
                return true;
            }
        } catch (Exception e) {
            logger.error("添加配置异常"+e.toString());
        }
        return false;
    }

    public boolean updateConfiguration(Configuration configuration) {
        try {
            if (configurationDal.updateConfiguration(configuration)){
                notify(configuration);
                return true;
            }
        } catch (Exception e) {
            logger.error("更新配置异常"+e.toString());
        }
        return false;
    }

    /**
     * 配置更新通知
     * @param configuration
     */
    private void notify(Configuration configuration){
        ZookeeperConfig zookeeperConfig=new ZookeeperConfig();
        PropertiesUtility propertiesUtility=new PropertiesUtility("conf.properties");
        zookeeperConfig.setServerLists(propertiesUtility.getProperty("reg.serverList"));
        zookeeperConfig.setNamespace(propertiesUtility.getProperty("reg.namespace"));
        zookeeperConfig.setAuth(propertiesUtility.getProperty("reg.auth"));
        ZookeeperRegistryCenter zookeeperRegistryCenter=new ZookeeperRegistryCenter(zookeeperConfig);
        zookeeperRegistryCenter.init();
        if (zookeeperRegistryCenter.isExisted("/"+configuration.getConfKey())){
            zookeeperRegistryCenter.update("/"+configuration.getConfKey(),configuration.getConfValue());
        }else {
            zookeeperRegistryCenter.create("/"+configuration.getConfKey(),configuration.getConfValue());
        }
        zookeeperRegistryCenter.close();
    }
}
