package conf.spring;

import conf.AppConfigContext;
import conf.ConfigHeartbeat;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by alan.zheng on 2017/3/17.
 */
public class ConfigManagerHelper {
    private static Map<String,Object> context;
    private static ConfigManagerHelper configManagerHelper;
    private static ReentrantLock lock=new ReentrantLock();
    private static ConfigManagerHelper instance(){
        if (configManagerHelper==null){
            try {
                lock.lock();
                if (configManagerHelper==null){
                    ConfigHeartbeat.instance();
                    configManagerHelper=new ConfigManagerHelper();
                    Map map=new HashMap();
                    map.put("aaa","bbb");
                    AppConfigContext.setContext(map);
                }
            } finally {
                lock.unlock();
            }
        }
        return configManagerHelper;
    }
}
