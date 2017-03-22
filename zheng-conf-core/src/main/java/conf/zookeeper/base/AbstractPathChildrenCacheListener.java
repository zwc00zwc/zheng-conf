package conf.zookeeper.base;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

/**
 * Created by alan.zheng on 2017/3/22.
 */
public class AbstractPathChildrenCacheListener implements PathChildrenCacheListener {
    public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {

    }
}
