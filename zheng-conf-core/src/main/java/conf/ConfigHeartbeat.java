package conf;

import java.util.Timer;
import java.util.TimerTask;
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

    class HeartRun extends TimerTask {
        @Override
        public void run() {

        }
    }
}
