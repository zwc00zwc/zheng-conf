package conf;

import java.util.Map;

/**
 * 配置上下文
 * Created by Administrator on 2017/3/20.
 */
public class AppConfigContext {
    private static Map context;

    public static Map getContext() {
        return context;
    }

    public static void setContext(Map context) {
        AppConfigContext.context = context;
    }
}
