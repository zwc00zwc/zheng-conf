package conf.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by alan.zheng on 2017/2/8.
 */
public class PropertiesUtility {

    private static Properties props = new Properties();
    public PropertiesUtility(String file){
        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String property){
        return props.getProperty(property);
    }

    public void setProperty(String key,String value){
        props.setProperty(key,value);
    }

    public void storeProperty(String file){
        try {
            FileOutputStream oFile=null;
            File outFile=null;
            try {
                URL url= Thread.currentThread().getContextClassLoader().getResource(file);
                outFile=new File(url.getFile());
            } catch (Exception e) {
                e.printStackTrace();
            }
            oFile = new FileOutputStream(outFile);
            props.store(oFile,null);
            oFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
