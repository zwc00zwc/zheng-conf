package conf;

import conf.db.dal.ConfigurationDal;
import conf.db.model.Configuration;
import conf.utility.PropertiesUtility;
import conf.zookeeper.ZookeeperConfig;
import conf.zookeeper.ZookeeperRegistryCenter;
import conf.zookeeper.listener.ConfListener;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 心跳守护
 * Created by alan.zheng on 2017/3/17.
 */
public class ConfigHeartbeat {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ConfigHeartbeat.class);
    private static ConfigHeartbeat configHeartbeat;
    private static ReentrantLock lock = new ReentrantLock();
    private static ZookeeperRegistryCenter zookeeperRegistryCenter;
    private ConfigHeartbeat(){
        //注册心跳
        Timer timer=new Timer();
        timer.schedule(new HeartRun(),0,5000);
    }

    public static ConfigHeartbeat instance(){
        if (configHeartbeat==null){
            try {
                lock.lock();
                if (configHeartbeat==null){
                    configHeartbeat=new ConfigHeartbeat();
                }
            } finally {
                lock.unlock();
            }
        }
        return configHeartbeat;
    }

    public static void loadConfig(){
        //加载配置
        ConfigurationDal configurationDal=new ConfigurationDal();
        List<Configuration> configurationList= configurationDal.queryByNodeIds(AppConfigContext.getNodeIds());
        Map map=new HashMap();
        if (configurationList != null && configurationList.size() > 0){
            for (Configuration config:configurationList) {
                map.put(config.getConfKey(),config.getConfValue());
            }
        }
        AppConfigContext.setContext(map);
        //加载配置进行配置更新监听
        if (zookeeperRegistryCenter==null){
            ZookeeperConfig zookeeperConfig=new ZookeeperConfig();
            PropertiesUtility propertiesUtility=new PropertiesUtility("conf.properties");
            zookeeperConfig.setServerLists(propertiesUtility.getProperty("reg.serverList"));
            zookeeperConfig.setNamespace(propertiesUtility.getProperty("reg.namespace"));
            zookeeperConfig.setAuth(propertiesUtility.getProperty("reg.auth"));
            zookeeperRegistryCenter=new ZookeeperRegistryCenter(zookeeperConfig);
            zookeeperRegistryCenter.init();
        }
        //清空
        zookeeperRegistryCenter.remove("/");
        try {
            CuratorFramework curatorFramework=(CuratorFramework) zookeeperRegistryCenter.getRawClient();
            PathChildrenCache childrenCache=new PathChildrenCache(curatorFramework,"/",true);
            childrenCache.getListenable().addListener(new ConfListener());
            childrenCache.start();
        } catch (Exception e) {
            logger.error("zookeeper配置监听异常"+e.toString());
        }
    }

    class HeartRun extends TimerTask {
        @Override
        public void run() {

        }
    }
}
