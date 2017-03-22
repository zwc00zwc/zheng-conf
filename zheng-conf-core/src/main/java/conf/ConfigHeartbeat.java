package conf;

import conf.db.dal.ConfigurationDal;
import conf.db.model.Configuration;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 心跳守护
 * Created by alan.zheng on 2017/3/17.
 */
public class ConfigHeartbeat {
    private static ConfigHeartbeat configHeartbeat;
    private static ReentrantLock lock;
    private ConfigHeartbeat(){
        lock=new ReentrantLock();
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
    }

    class HeartRun extends TimerTask {
        @Override
        public void run() {

        }
    }
}
