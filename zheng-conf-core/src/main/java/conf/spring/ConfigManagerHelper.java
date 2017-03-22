package conf.spring;

import conf.AppConfigContext;
import conf.ConfigHeartbeat;
import conf.db.dal.ConfigurationDal;
import conf.db.model.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by alan.zheng on 2017/3/17.
 */
public class ConfigManagerHelper {
    private static ConfigManagerHelper configManagerHelper;
    private static ReentrantLock lock=new ReentrantLock();
    private static ReentrantLock staticLock=new ReentrantLock();

    static {
        if (configManagerHelper==null){
            staticLock.lock();
            if (configManagerHelper==null){

            }
        }
    }

    private static ConfigManagerHelper instance(){
        if (configManagerHelper==null){
            try {
                lock.lock();
                if (configManagerHelper==null){
                    ConfigHeartbeat.instance().loadConfig();
                    configManagerHelper=new ConfigManagerHelper();
                }
            } finally {
                lock.unlock();
            }
        }
        return configManagerHelper;
    }

    public static String get(String key){
        if (instance()!=null){

        }
        Map map = AppConfigContext.getContext();
        if (map.containsKey(key)){
            return map.get(key).toString();
        }
        return null;
    }
}
