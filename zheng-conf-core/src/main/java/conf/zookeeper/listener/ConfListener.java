package conf.zookeeper.listener;

import conf.AppConfigContext;
import conf.zookeeper.base.AbstractPathChildrenCacheListener;
import org.apache.commons.lang3.StringUtils;
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
        try {
            lock.lock();
            Map map = AppConfigContext.getContext();
            String key=null;
            if (data.getPath().indexOf("/conf/")>=0){
                key=data.getPath().replace("/conf/","");
            }
            if (!StringUtils.isEmpty(key)){
                //新增节点
                if (PathChildrenCacheEvent.Type.CHILD_ADDED.equals(pathChildrenCacheEvent.getType())){
                    map.put(key,str);
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
                        map.put(key,str);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
