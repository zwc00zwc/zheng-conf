package conf.spring;

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
                    configManagerHelper=new ConfigManagerHelper();
                }
            } finally {
                lock.unlock();
            }
        }
        return configManagerHelper;
    }
}
