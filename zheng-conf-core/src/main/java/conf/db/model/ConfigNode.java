package conf.db.model;

/**
 * Created by alan.zheng on 2017/3/14.
 */
public class ConfigNode {
    /**
     * 主键
     */
    private String id;
    /**
     * 配置键
     */
    private String key;
    /**
     * 配置值
     */
    private String value;
    /**
     * 描述
     */
    private String desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
