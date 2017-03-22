package conf;

import java.util.List;
import java.util.Map;

/**
 * 配置上下文
 * Created by Administrator on 2017/3/20.
 */
public class AppConfigContext {
    /**
     * 节点
     */
    private static List<Integer> nodeIds;
    /**
     * 配置上下文
     */
    private static Map context;

    public static List<Integer> getNodeIds() {
        return nodeIds;
    }

    public static void setNodeIds(List<Integer> nodeIds) {
        AppConfigContext.nodeIds = nodeIds;
    }

    public static Map getContext() {
        return context;
    }

    public static void setContext(Map context) {
        AppConfigContext.context = context;
    }
}
