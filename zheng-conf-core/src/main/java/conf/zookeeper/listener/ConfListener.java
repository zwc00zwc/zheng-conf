package conf.zookeeper.listener;

import conf.AppConfigContext;
import conf.zookeeper.base.AbstractPathChildrenCacheListener;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by alan.zheng on 2017/3/22.
 */
public class ConfListener extends AbstractPathChildrenCacheListener {
    private Lock lock=new ReentrantLock();
    @Override
    public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
        ChildData data = pathChildrenCacheEvent.getData();
        String str=new String(data.getData());
        lock.lock();
        Map map = AppConfigContext.getContext();
        String key = str.substring(data.getPath().indexOf("/conf/"));
        //新增节点
        if (PathChildrenCacheEvent.Type.CHILD_ADDED.equals(pathChildrenCacheEvent.getType())){
            if (!map.containsKey(key)){
                map.put(key,str);
            }
        }
        //删除节点
        if (PathChildrenCacheEvent.Type.CHILD_REMOVED.equals(pathChildrenCacheEvent.getType())){
            if (map.containsKey(key)){
                map.remove(key);
            }
        }
        //修改节点
        if (PathChildrenCacheEvent.Type.CHILD_UPDATED.equals(pathChildrenCacheEvent.getType())){
            if (map.containsKey(key)){
                map.put(key,data.getData());
            }
        }
        lock.unlock();
    }
}
