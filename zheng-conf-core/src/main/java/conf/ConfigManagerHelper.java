package conf;

import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Created by alan.zheng on 2017/3/17.
 */
public class ConfigManagerHelper {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ConfigManagerHelper.class);
    private static ConfigManagerHelper configManagerHelper;
    private static ReentrantLock lock=new ReentrantLock();
    private static ReentrantLock staticLock=new ReentrantLock();

    private ConfigManagerHelper(){

    }

    private static ConfigManagerHelper instance(){
        if (configManagerHelper==null){
            try {
                lock.lock();
                if (configManagerHelper==null){
                    List<Integer> nodes=new ArrayList<Integer>();
                    nodes.add(1);
                    AppConfigContext.setNodeIds(nodes);
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
        if (instance()==null){
            logger.error("当前配置异常");
        }
        Map map = AppConfigContext.getContext();
        if (map.containsKey(key)){
            return map.get(key).toString();
        }
        return null;
    }
}
